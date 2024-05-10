package com.spring.start.h2.Usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.jugador.Jugador;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/InformacionUsuario")
    public ModelAndView mostrarInfoUsuario() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        // Obtener el usuario
        Usuario usuario = usuarioDAO.findByUsuario(nombreUsuario);
        
        // Obtener el jugador asociado al usuario
        Jugador jugador = usuario.getJugador();
        
        // Agregar tanto el usuario como el jugador al modelo
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("jugador", jugador);
        
        modelAndView.setViewName("infoUsuario");
        
        return modelAndView;
    }
}