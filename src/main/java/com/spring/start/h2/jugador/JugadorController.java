package com.spring.start.h2.jugador;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.spring.start.h2.Enmarca.EnmarcaDAO;
import com.spring.start.h2.equipo.EquipoDAO;

import jakarta.validation.Valid;

@Controller
public class JugadorController {

    @Autowired
    JugadorDAO jugadorDAO;

    @Autowired
    EquipoDAO equipoDAO;
    
    @Autowired
    EnmarcaDAO enmarcaDAO;

    


    
    
    @GetMapping("/jugadores")
    public ModelAndView jugadores() {
        ModelAndView modelAndView = new ModelAndView();
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        modelAndView.setViewName("jugadores");

        modelAndView.addObject("jugadores", jugadorDAO.findAll());

        return modelAndView;
    }

    @GetMapping("/jugador/{id}")
    public ModelAndView jugador(@PathVariable long id) {
        Jugador jugador = jugadorDAO.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView();
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        modelAndView.setViewName("jugador");

        modelAndView.addObject("jugador", jugador);
        modelAndView.addObject("equipos", equipoDAO.findAll());

        return modelAndView;
    }

    @GetMapping("/jugador/add")
    public ModelAndView addJugador() {
        ModelAndView modelAndView = new ModelAndView();
        //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        modelAndView.setViewName("formjugador");
        modelAndView.addObject("jugador", new Jugador());
        modelAndView.addObject("equipos", equipoDAO.findAll());

        return modelAndView;
    }

    @GetMapping("/jugador/edit/{id}")
    public ModelAndView editJugador(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Jugador> jugadorOptional = jugadorDAO.findById(id);
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            modelAndView.addObject("jugador", jugador);
            modelAndView.addObject("equipos", equipoDAO.findAll());
            modelAndView.addObject("equipoActual", jugador.getEquipo());
            
            modelAndView.setViewName("formjugador");
        } else {
            modelAndView.setViewName("redirect:/jugadores");
        }
        
        return modelAndView;
    }


    
    
    
    @PostMapping("/jugador/save")
    public ModelAndView saveJugador(@ModelAttribute("jugador") @Valid Jugador jugador,BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        
        if (bindingResult.hasErrors()) {
   
            modelAndView.setViewName("formjugador");
            return modelAndView;
        }
        // Verificar si el jugador ya existe 
        Optional<Jugador> existingJugadorOptional = jugadorDAO.findById(jugador.getId());
        if (existingJugadorOptional.isPresent()) {
            Jugador existingJugador = existingJugadorOptional.get();
            // Actualizar los datos del jugador existente
            existingJugador.setNombre(jugador.getNombre());
            existingJugador.setEdad(jugador.getEdad());
            existingJugador.setNivel(jugador.getNivel());
            existingJugador.setEquipo(jugador.getEquipo());
            jugadorDAO.save(existingJugador);
        } else {
            // Guardar el nuevo jugador
            jugadorDAO.save(jugador);
        }

        modelAndView.setViewName("redirect:/jugadores");
        return modelAndView;
    }


    @GetMapping("/jugador/delete/{id}")
    public ModelAndView deleteJugador(@PathVariable long id) {

    				
    		jugadorDAO.deleteById(id);
    		
    		ModelAndView model = new ModelAndView();
    		model.setViewName("redirect:/jugadores");
    		
    		return model;
    	}



    @GetMapping("/jugador/remove-equipo/{jugadorId}")
    public ModelAndView removeEquipo(@PathVariable long jugadorId) {
        Optional<Jugador> jugadorOptional = jugadorDAO.findById(jugadorId);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
  
            jugador.setEquipo(null);

            jugadorDAO.save(jugador);
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/jugadores");
        return model;
    }
}
