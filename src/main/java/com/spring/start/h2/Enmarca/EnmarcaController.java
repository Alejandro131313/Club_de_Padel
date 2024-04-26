package com.spring.start.h2.Enmarca;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;

import com.spring.start.h2.clase.ClaseDAO;
import com.spring.start.h2.jugador.JugadorDAO;
import jakarta.validation.Valid;

@Controller
public class EnmarcaController {

    @Autowired
    EnmarcaDAO enmarcaDAO;

    @Autowired
    ClaseDAO claseDAO;

    @Autowired
    JugadorDAO jugadorDAO;

    @GetMapping("/enmarca")
    public ModelAndView enmarcas() {
        ModelAndView modelAndView = new ModelAndView();
      //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.addObject("enmarcaciones", enmarcaDAO.findAll());
        modelAndView.setViewName("enmarcas");
        return modelAndView;
    }

    
    
    
    
    
    
    @GetMapping("/enmarca/add")
    public ModelAndView enmarcaAdd() {
        ModelAndView modelAndView = new ModelAndView();
        //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.addObject("enmarca", new Enmarca());
        modelAndView.addObject("jugador", jugadorDAO.findAll());
        modelAndView.addObject("clase", claseDAO.findAll());
        modelAndView.setViewName("formEnmarca");
        return modelAndView;
    }



    
    
    
    
    @PostMapping("/enmarca/save")
    public ModelAndView saveEnmarca(@ModelAttribute @Valid Enmarca enmarca) {
        EnmarcaKey key = new EnmarcaKey();
        
 
        key.setClaseId(enmarca.getClase().getId());
        key.setJugadorId(enmarca.getJugador().getId());
        

        enmarca.setId(key);
        enmarcaDAO.save(enmarca);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/enmarca");
        
        return modelAndView;
    }


    @GetMapping("/enmarca/del/{idJugador}/{idClase}")
    public ModelAndView deleteEnmarca(@PathVariable long idJugador, @PathVariable long idClase) {
        EnmarcaKey key = new EnmarcaKey();
        key.setJugadorId(idJugador);
        key.setClaseId(idClase);
        enmarcaDAO.deleteById(key);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/enmarca");
        return modelAndView;
    }
    
    @GetMapping("/enmarca/{idJugador}/{idClase}/datosenmarca")
    public ModelAndView obtenerDatosEnmarca(@PathVariable long idJugador, @PathVariable long idClase) {
        ModelAndView modelAndView = new ModelAndView();
        //Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.addObject("jugador", jugadorDAO.findById(idJugador));
        modelAndView.addObject("clase", claseDAO.findById(idClase));
        modelAndView.setViewName("datosEnmarca"); 
        return modelAndView;
    }
    
    @GetMapping("/menu")
    public ModelAndView menu() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Menu");
//Agregar nombre usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);

        return modelAndView;
    }
    
}
