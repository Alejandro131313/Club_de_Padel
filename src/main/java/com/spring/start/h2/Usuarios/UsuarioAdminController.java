package com.spring.start.h2.Usuarios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
public class UsuarioAdminController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/usuarios")
    public ModelAndView usuarios() {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/usuarios");
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
        return modelAndView;
    }

    @GetMapping("/usuario/edit/{usuario}")
    public ModelAndView editUsuario(@PathVariable String usuario) {
        ModelAndView modelAndView = new ModelAndView("UsuariosAdmin/formusuario");
        Optional<Usuario> usuarioOptional = usuarioDAO.findById(usuario);
        if (usuarioOptional.isPresent()) {
            modelAndView.addObject("usuario", usuarioOptional.get());
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
            return modelAndView;
        }
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioDAO.save(usuario);
        modelAndView.setViewName("redirect:/usuarios");
        return modelAndView;
    }

    @GetMapping("/usuario/delete/{usuario}")
    public ModelAndView deleteUsuario(@PathVariable String usuario) {
        usuarioDAO.deleteById(usuario);
        ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
        return modelAndView;
    }
}
