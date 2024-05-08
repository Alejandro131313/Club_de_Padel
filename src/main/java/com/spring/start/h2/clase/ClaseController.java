package com.spring.start.h2.clase;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

import com.spring.start.h2.Enmarca.Enmarca;
import com.spring.start.h2.Enmarca.EnmarcaDAO;
import com.spring.start.h2.jugador.Jugador;

import jakarta.validation.Valid;

@Controller
public class ClaseController {

    @Autowired
    ClaseDAO claseDAO;

    @Autowired
    EnmarcaDAO enmarcaDAO;

    @GetMapping("/clases")
    public ModelAndView clases() {
        ModelAndView modelAndView = new ModelAndView();
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        modelAndView.setViewName("clases");

        modelAndView.addObject("clases", claseDAO.findAll());

        return modelAndView;
    }

    @GetMapping("/clase/{id}")
    public ModelAndView clase(@PathVariable long id) {
        Clase clase = claseDAO.findById(id).orElse(null);
        Set<Jugador> jugadores = new HashSet<>();
        for (Enmarca enmarca : clase.getEnmarca()) {
            jugadores.add(enmarca.getJugador());
        }
        ModelAndView modelAndView = new ModelAndView();
        
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        modelAndView.setViewName("clase");

        modelAndView.addObject("clase", clase);
        modelAndView.addObject("jugadores", jugadores);

        return modelAndView;
    }


    @GetMapping("/clase/add")
    public ModelAndView addClase() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formclase");
        modelAndView.addObject("clase", new Clase());

        return modelAndView;
    }

    @GetMapping("/clase/edit/{id}")
    public ModelAndView editClase(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Clase> claseOptional = claseDAO.findById(id);
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        
        if (claseOptional.isPresent()) {
            Clase clase = claseOptional.get();
            modelAndView.addObject("clase", clase);
            modelAndView.setViewName("formclase");
        } else {
            modelAndView.setViewName("redirect:/clases");
        }
        
        return modelAndView;
    }

    @PostMapping("/clase/save")
    public ModelAndView saveClase(@ModelAttribute("clase") @Valid Clase clase,BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

      
        if (bindingResult.hasErrors()) {
   
            modelAndView.setViewName("formclase");
            return modelAndView;
        }
        claseDAO.save(clase);
        modelAndView.setViewName("redirect:/clases");
        return modelAndView;
    }
    
    

    @GetMapping("/clase/delete/{id}")
    public ModelAndView deleteClase(@PathVariable long id) {
        claseDAO.deleteById(id);
        
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/clases");
        
        return model;
    }
}
