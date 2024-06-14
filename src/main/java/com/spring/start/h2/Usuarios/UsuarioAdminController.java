package com.spring.start.h2.Usuarios;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.jugador.Jugador;
import com.spring.start.h2.jugador.JugadorDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        modelAndView.addObject("jugadores", jugadorDAO.findJugadoresSinUsuario());
        return modelAndView;
    }

 

    @PostMapping("/usuario/save")
    public ModelAndView saveUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("UsuariosAdmin/formusuario");

            modelAndView.addObject("jugadores", jugadorDAO.findJugadoresSinUsuario());
            return modelAndView;
        }
        
        
           Usuario nombreUsuario = usuarioDAO.findByUsuario(usuario.getUsuario());
        
        if (nombreUsuario != null) {
        	  modelAndView.setViewName("UsuariosAdmin/formusuario");
            modelAndView.addObject("mensaje", "El usuario ya está en uso");
            modelAndView.addObject("jugadores", jugadorDAO.findJugadoresSinUsuario());
            return modelAndView;
        }
        
        
        
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioDAO.save(usuario);
        modelAndView.setViewName("redirect:/usuarios");
        return modelAndView;
    }
    
    @GetMapping("/usuario/edit/{usuario}")
    public ModelAndView editUsuario(@PathVariable String usuario) {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/editarUsuario");
        Optional<Usuario> usuarioOptional = usuarioDAO.findById(usuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioObj = usuarioOptional.get();
            modelAndView.addObject("usuario", usuarioObj);

            // Obtener jugadores sin usuario
            List<Jugador> jugadoresSinUsuario = jugadorDAO.findJugadoresSinUsuario();

            // Obtener jugadores con el usuario actual
            List<Jugador> jugadoresConUsuario = jugadorDAO.findJugadoresSinUsuario2(usuario);

            //Fusionar las listas
            Set<Jugador> jugadores = new HashSet<>(jugadoresSinUsuario);
            jugadores.addAll(jugadoresConUsuario);

            modelAndView.addObject("jugadores", jugadores);
        } else {
            modelAndView.setViewName("redirect:/usuarios");
        }

        return modelAndView;
    }

    
    @PostMapping("/usuario/saveEdit")
    public ModelAndView saveEditUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("UsuariosAdmin/formusuario");

         // Obtener jugadores sin usuario
            List<Jugador> jugadoresSinUsuario = jugadorDAO.findJugadoresSinUsuario();

            // Obtener jugadores con el usuario actual
            List<Jugador> jugadoresConUsuario = jugadorDAO.findJugadoresSinUsuario2(usuario.getUsuario());

            //Fusionar las listas
            Set<Jugador> jugadores = new HashSet<>(jugadoresSinUsuario);
            jugadores.addAll(jugadoresConUsuario);

            modelAndView.addObject("jugadores", jugadores);
            return modelAndView;
        }              
        
        
        
        
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioDAO.save(usuario);
        modelAndView.setViewName("redirect:/usuarios");
        return modelAndView;
    }

    
    

    @GetMapping("/usuario/delete/{usuarioId}")
    public ModelAndView deleteUsuario(@PathVariable String usuarioId, HttpServletRequest request, HttpServletResponse response) {
    	
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String nombreUsuario = auth.getName();
         
        Optional<Usuario> usuarioOptional = usuarioDAO.findById(usuarioId);
        

            Usuario usuario = usuarioOptional.get();
            Jugador jugador = usuario.getJugador();

            if(jugador !=null) {
            jugador.setUsuario(null);
            jugadorDAO.save(jugador);
            
            }
            
            usuario.setJugador(null);
            usuarioDAO.save(usuario);
            usuarioDAO.delete(usuario);
            
            // Si el usuario eliminado es el mismo que está autenticado, realizar logout
            if (nombreUsuario.equals(usuarioId)) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
   

        return new ModelAndView("redirect:/usuarios");
    }

}
