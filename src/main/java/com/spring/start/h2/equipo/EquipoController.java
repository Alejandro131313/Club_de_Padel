package com.spring.start.h2.equipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.jugador.Jugador;
import com.spring.start.h2.jugador.JugadorDAO;
import com.spring.start.h2.torneos.TorneoDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class EquipoController {

    @Autowired
    EquipoDAO equipoDAO;

    
    
    @Autowired
    JugadorDAO jugadorDAO;
    
    
    @Autowired
    TorneoDAO torneoDAO;

    
    @GetMapping("/equipos")
    public ModelAndView equipos() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.setViewName("Equipos/equipos");
        modelAndView.addObject("equipos", equipoDAO.findAll());
        return modelAndView;
    }

    @GetMapping("/equipo/{id}")
    public ModelAndView equipo(@PathVariable long id) {
        Equipo equipo = equipoDAO.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView();
       
        modelAndView.setViewName("Equipos/equipo");
        modelAndView.addObject("equipo", equipo);
        modelAndView.addObject("jugadores", equipo.getJugadores()); 
        return modelAndView;
    }

    @GetMapping("/equipo/delete/{id}")
    public ModelAndView deleteEquipo(@PathVariable long id) {
        Optional<Equipo> equipoOptional = equipoDAO.findById(id);
        if (equipoOptional.isPresent()) {
            Equipo equipo = equipoOptional.get();
            List<Jugador> jugadores = equipo.getJugadores();

            for (Jugador jugador : jugadores) {
                jugador.setEquipo(null);
                jugadorDAO.save(jugador);
            }

            equipoDAO.deleteById(id);
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/equipos");
        return model;
    }

    @GetMapping("/equipo/add")
    public ModelAndView addEquipoNuevo() {
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("Equipos/crearequipo");
        modelAndView.addObject("equipo", new Equipo());
        modelAndView.addObject("torneos", torneoDAO.findAll());
        
        return modelAndView;
    }


    @PostMapping("/equipo/save")
    public ModelAndView saveEquipoNuevo(@ModelAttribute("equipo") @Valid Equipo equipo, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Equipos/crearequipo");
            modelAndView.addObject("torneos", torneoDAO.findAll());
            return modelAndView;
        }
        equipoDAO.save(equipo);
        modelAndView.setViewName("redirect:/equipos");
        return modelAndView;
    }

    
    
    
    @GetMapping("/equipo/edit/{id}")
    public ModelAndView editEquipo(@PathVariable long id) {
    	   ModelAndView modelAndView = new ModelAndView();
           modelAndView.setViewName("Equipos/formequipo");
           modelAndView.addObject("equipo", equipoDAO.findById(id).orElse(null));
           Equipo equipo = equipoDAO.findById(id).orElse(null);
           modelAndView.addObject("torneos", torneoDAO.findAll());
           modelAndView.addObject("jugadores",jugadorDAO.findAll() ); 
           return modelAndView;
    }


    @PostMapping("/equipo/saveEdit")
    public ModelAndView saveEditEquipo(@ModelAttribute("equipo") @Valid Equipo equipo, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener la lista de jugadores seleccionados desde el formulario
        String[] jugadoresSeleccionados = request.getParameterValues("jugadoresSeleccionados");

        // Si hay errores regresa al formulario
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Equipos/formequipo");
            modelAndView.addObject("torneos", torneoDAO.findAll());
            modelAndView.addObject("jugadores", jugadorDAO.findAll());
            return modelAndView;
        }

        // Obtener el equipo de la base de datos
        Optional<Equipo> equipoOriginalOptional = equipoDAO.findById(equipo.getId_equipo());
        if (equipoOriginalOptional.isPresent()) {
            Equipo equipoOriginal = equipoOriginalOptional.get();

            // Crear una lista para los jugadores seleccionados
            List<Jugador> jugadoresSeleccionadosList = new ArrayList<>();

            // Si hay jugadores seleccionados, actualizarlos
            if (jugadoresSeleccionados != null) {
                for (String jugadorId : jugadoresSeleccionados) {
                    Long id = Long.valueOf(jugadorId);
                    Optional<Jugador> jugadorOptional = jugadorDAO.findById(id);
                    jugadorOptional.ifPresent(jugador -> {
                        jugador.setEquipo(equipo);
                        jugadoresSeleccionadosList.add(jugador);
                    });
                }
            }

            // Obtener la lista de jugadores del equipo original
            List<Jugador> jugadoresOriginales = equipoOriginal.getJugadores();

            // Eliminar los jugadores que ya no están seleccionados
            for (Jugador jugadorOriginal : jugadoresOriginales) {
                if (!jugadoresSeleccionadosList.contains(jugadorOriginal)) {
                    jugadorOriginal.setEquipo(null);
                    jugadorDAO.save(jugadorOriginal);
                }
            }

            // Actualizar la lista de jugadores del equipo
            equipo.setJugadores(jugadoresSeleccionadosList);
        }

        // Guardar el equipo actualizado
        equipoDAO.save(equipo);
        modelAndView.setViewName("redirect:/equipos");
        return modelAndView;
    }



    
    
   
    
    @GetMapping("/jugador/remove-equipo-jugador/{jugadorId}")
    public ModelAndView removeEquipoJugador(@PathVariable long jugadorId) {
        Optional<Jugador> jugadorOptional = jugadorDAO.findById(jugadorId);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            long equipoId = jugador.getEquipo().getId_equipo();
            jugador.setEquipo(null);
            jugadorDAO.save(jugador);

            return new ModelAndView("redirect:/equipo/" + equipoId);
        } else {
            return new ModelAndView("redirect:/equipos");
        }
    } 
    
    



}

