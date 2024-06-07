package com.spring.start.h2.torneos;

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

import com.spring.start.h2.equipo.Equipo;
import com.spring.start.h2.equipo.EquipoDAO;
import com.spring.start.h2.jugador.JugadorDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TorneoController {

    @Autowired
    private TorneoDAO torneoDAO;

    @Autowired
    private EquipoDAO equipoDAO;

    @Autowired
    private JugadorDAO jugadorDAO;

    @GetMapping("/torneos")
    public ModelAndView torneos() {
        ModelAndView modelAndView = new ModelAndView();
        // Agregar nombre de usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.setViewName("Torneos/torneos");
        modelAndView.addObject("torneos", torneoDAO.findAll());
        return modelAndView;
    }

    @GetMapping("/torneo/{id}")
    public ModelAndView torneo(@PathVariable long id) {
        Torneo torneo = torneoDAO.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView();
       
        modelAndView.setViewName("Torneos/torneo");
        modelAndView.addObject("torneo", torneo);
        modelAndView.addObject("equipos", torneo.getEquipos()); 
        return modelAndView;
    }
    
    

    @GetMapping("/torneo/delete/{id}")
    public ModelAndView deleteTorneo(@PathVariable long id) {
        Optional<Torneo> torneoOptional = torneoDAO.findById(id);
        if (torneoOptional.isPresent()) {
            Torneo torneo = torneoOptional.get();
            List<Equipo> equipos = torneo.getEquipos();

            for (Equipo equipo : equipos) {
                equipo.setTorneo(null);
                equipoDAO.save(equipo);
            }

            torneoDAO.deleteById(id);
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/torneos");
        return model;
    }


    
    @GetMapping("/torneo/add")
    public ModelAndView addTorneoNuevo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Torneos/creartorneo");
        modelAndView.addObject("torneo", new Torneo());
        return modelAndView;
    }


    @PostMapping("/torneo/save")
    public ModelAndView guardarTorneoNuevo(@ModelAttribute("torneo") Torneo torneo, BindingResult result) {
    	  ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("Torneos/formtorneo");
    	
    	if (result.hasErrors()) {
    		modelAndView.setViewName("Torneos/formtorneo");
    		   return modelAndView;
        }
        torneoDAO.save(torneo);
        modelAndView.setViewName("redirect:/torneos");
        return modelAndView;
    
    }
    
    
    @GetMapping("/torneo/edit/{id}")
    public ModelAndView editTorneo(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Torneos/formtorneos");
        modelAndView.addObject("torneo", torneoDAO.findById(id).orElse(null));
        Torneo torneo = torneoDAO.findById(id).orElse(null);
        modelAndView.addObject("equipos",equipoDAO.findAll() ); 
        return modelAndView;
    }


    @PostMapping("/torneo/saveEdit")
    public ModelAndView saveEditTorneo(@ModelAttribute("torneo") @Valid Torneo torneo, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener la lista de equipos seleccionados desde el formulario
        String[] equiposSeleccionados = request.getParameterValues("equiposSeleccionados");

        // Si hay errores regresa al formulario
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Torneos/formtorneos");
            modelAndView.addObject("equipos", equipoDAO.findAll());
            return modelAndView;
        }

        // Obtener el torneo de la base de datos
        Optional<Torneo> torneoOriginalOptional = torneoDAO.findById(torneo.getId_torneo());
        if (torneoOriginalOptional.isPresent()) {
            Torneo torneoOriginal = torneoOriginalOptional.get();

            // Crear una lista para los equipos seleccionados
            List<Equipo> equiposSeleccionadosList = new ArrayList<>();

            // Si hay equipos seleccionados, actualizarlos
            if (equiposSeleccionados != null) {
                for (String equipoId : equiposSeleccionados) {
                    Long id = Long.valueOf(equipoId);
                    Optional<Equipo> equipoOptional = equipoDAO.findById(id);
                    equipoOptional.ifPresent(equipo -> {
                        equipo.setTorneo(torneo);
                        equiposSeleccionadosList.add(equipo);
                    });
                }
            }

            // Obtener la lista de equipos del torneo original
            List<Equipo> equiposOriginales = torneoOriginal.getEquipos();

            // Eliminar los equipos que ya no están seleccionados
            for (Equipo equipoOriginal : equiposOriginales) {
                if (!equiposSeleccionadosList.contains(equipoOriginal)) {
                    equipoOriginal.setTorneo(null);
                    equipoDAO.save(equipoOriginal);
                }
            }

            // Actualizar la lista de equipos del torneo
            torneo.setEquipos(equiposSeleccionadosList);
        }

        // Guardar el torneo actualizado
        torneoDAO.save(torneo);
        modelAndView.setViewName("redirect:/torneos");
        return modelAndView;
    }


    
    
    
    
    @GetMapping("/torneo/remove-equipo/{torneoId}/{equipoId}")
    public ModelAndView removeEquipoTorneo(@PathVariable long torneoId, @PathVariable long equipoId) {
        Optional<Torneo> torneoOptional = torneoDAO.findById(torneoId);
        Optional<Equipo> equipoOptional = equipoDAO.findById(equipoId);

        if (torneoOptional.isPresent() && equipoOptional.isPresent()) {
            Torneo torneo = torneoOptional.get();
            Equipo equipo = equipoOptional.get();

            equipo.setTorneo(null); 
            equipoDAO.save(equipo);

            
            return new ModelAndView("redirect:/torneo/" + torneoId);
        } else {
            return new ModelAndView("redirect:/torneos");
        }
    }


}