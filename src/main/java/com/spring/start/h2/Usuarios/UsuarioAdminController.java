package com.spring.start.h2.Usuarios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.jugador.JugadorDAO;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class UsuarioAdminController {

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    JugadorDAO jugadorDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/usuarios")
    public ModelAndView usuarios() {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/usuarios");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        
        
        modelAndView.addObject("usuarios", usuarioDAO.findAll());
        return modelAndView;
    }

    @GetMapping("/usuario/{usuario}")
    public ModelAndView usuario(@PathVariable String usuario) {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/usuario");
        Usuario user = usuarioDAO.findByUsuario(usuario);
        modelAndView.addObject("usuario", user);
        return modelAndView;
    }

    @GetMapping("/usuario/add")
    public ModelAndView addUsuario() {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/formusuario");
        modelAndView.addObject("usuario", new Usuario());
        modelAndView.addObject("jugadores", jugadorDAO.findAll());
        return modelAndView;
    }

    @GetMapping("/usuario/edit/{usuario}")
    public ModelAndView editUsuario(@PathVariable String usuario) {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/editarUsuario");
        Optional<Usuario> usuarioOptional = usuarioDAO.findById(usuario);
        if (usuarioOptional.isPresent()) {
            modelAndView.addObject("usuario", usuarioOptional.get());
 
            modelAndView.addObject("jugadores", jugadorDAO.findAll());
        } else {
            modelAndView.setViewName("redirect:/usuarios");
        }
        return modelAndView;
    }


    @PostMapping("/usuario/save")
    public ModelAndView saveUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("UsuariosAdmin/formusuario");

            modelAndView.addObject("jugadores", jugadorDAO.findAll());
            return modelAndView;
        }
        
        
           Usuario nombreUsuario = usuarioDAO.findByUsuario(usuario.getUsuario());
        
        if (nombreUsuario != null) {
        	  modelAndView.setViewName("UsuariosAdmin/formusuario");
            modelAndView.addObject("mensaje", "El usuario ya está en uso");
            return modelAndView;
        }
        
        
        
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioDAO.save(usuario);
        modelAndView.setViewName("redirect:/usuarios");
        return modelAndView;
    }


    @GetMapping("/usuario/delete/{usuarioId}")
    @Transactional
    public ModelAndView deleteUsuario(@PathVariable String usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioDAO.findById(usuarioId);
        

            Usuario usuario = usuarioOptional.get();
            
            usuario.setJugador(null);

            usuarioDAO.delete(usuario);
   

        return new ModelAndView("redirect:/usuarios");
    }

}
