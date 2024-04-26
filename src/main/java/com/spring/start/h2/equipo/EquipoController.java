package com.spring.start.h2.equipo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.jugador.Jugador;
import com.spring.start.h2.jugador.JugadorDAO;

import jakarta.validation.Valid;

@Controller
public class EquipoController {

    @Autowired
    EquipoDAO equipoDAO;

    
    
    @Autowired
    JugadorDAO jugadorDAO;
    
    
    
    
    @GetMapping("/equipos")
    public ModelAndView equipos() {
        ModelAndView modelAndView = new ModelAndView();
        // Agregar nombre de usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.setViewName("equipos");
        modelAndView.addObject("equipos", equipoDAO.findAll());
        return modelAndView;
    }

    @GetMapping("/equipo/{id}")
    public ModelAndView equipo(@PathVariable long id) {
        Equipo equipo = equipoDAO.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.setViewName("equipo");
        modelAndView.addObject("equipo", equipo);
        modelAndView.addObject("jugadores", equipo.getJugadores()); // Agregar la lista de jugadores al modelo
        return modelAndView;
    }








    @GetMapping("/equipo/delete/{id}")
    public ModelAndView deleteEquipo(@PathVariable long id) {
        equipoDAO.deleteById(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/equipos");
        return model;
    }
    


    
    
    
    @GetMapping("/jugador/remove-equipo-jugador/{jugadorId}")
    public ModelAndView removeEquipoJugador(@PathVariable long jugadorId) {
        Optional<Jugador> jugadorOptional = jugadorDAO.findById(jugadorId);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            long equipoId = jugador.getEquipo().getId_equipo();
            jugador.setEquipo(null);
            jugadorDAO.save(jugador);
            // Redirigir al usuario de vuelta a la página del equipo con el ID del equipo
            return new ModelAndView("redirect:/equipo/" + equipoId);
        } else {
            return new ModelAndView("redirect:/equipos");
        }
    } 
    
    
    
    @GetMapping("/equipo/add")
    public ModelAndView addEquipo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formequipo");
        modelAndView.addObject("equipo", new Equipo());
        return modelAndView;
    }

    @GetMapping("/equipo/edit/{id}")
    public ModelAndView editEquipo(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formequipo");
        modelAndView.addObject("equipo", equipoDAO.findById(id).orElse(null));
        return modelAndView;
    }

    @PostMapping("/equipo/save")
    public ModelAndView saveEquipo(@ModelAttribute("equipo") @Valid Equipo equipo, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("formequipo");
            return modelAndView;
        }
        equipoDAO.save(equipo);
        modelAndView.setViewName("redirect:/equipos");
        return modelAndView;
    }


    
    
    
    



}

