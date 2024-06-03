package com.spring.start.h2.Usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.Enmarca.Enmarca;
import com.spring.start.h2.Enmarca.EnmarcaDAO;
import com.spring.start.h2.Enmarca.EnmarcaKey;
import com.spring.start.h2.clase.Clase;
import com.spring.start.h2.clase.ClaseDAO;
import com.spring.start.h2.jugador.Jugador;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ClaseDAO claseDAO;

    @Autowired
    private EnmarcaDAO enmarcaDAO;

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
        
  
        // Obtener el nivel del jugador
        String nivel = jugador.getNivel();

        // Obtener las clases disponibles para el jugador según su nivel
        List<Clase> clasesDisponibles = claseDAO.findClasesDispoJugador(jugador.getId(), nivel);

        // Agregar las clases disponibles al modelo
        modelAndView.addObject("clasesDisponibles", clasesDisponibles);
        

        modelAndView.setViewName("Usuarios/infoUsuario");
        
        return modelAndView;
    }

    @PostMapping("/inscribirEnClase")
    public String inscribirEnClase(@RequestParam("claseId") Long claseId) {
        // Obtener la clase por su ID
        Clase clase = claseDAO.findById(claseId).orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));

        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        Usuario usuario = usuarioDAO.findByUsuario(nombreUsuario);

        // Obtener el jugador asociado al usuario
        Jugador jugador = usuario.getJugador();

        // Crear un nuevo objeto Enmarca
        Enmarca enmarca = new Enmarca();

        // Crear y asignar la clave primaria compuesta
        EnmarcaKey key = new EnmarcaKey();
        key.setClaseId(clase.getId());
        key.setJugadorId(jugador.getId());
        enmarca.setId(key);

        // Asignar la clase y el jugador al objeto Enmarca
        enmarca.setClase(clase);
        enmarca.setJugador(jugador);

        // Guardar el objeto Enmarca en la base de datos
        enmarcaDAO.save(enmarca);

        // Redirigir a la página de información del usuario
        return "redirect:/InformacionUsuario";
    }

    @GetMapping("/eliminarEnmarca/{idJugador}/{idClase}")
    public String eliminarEnmarca(@PathVariable long idJugador, @PathVariable long idClase) {
        EnmarcaKey key = new EnmarcaKey();
        key.setJugadorId(idJugador);
        key.setClaseId(idClase);
        enmarcaDAO.deleteById(key);
        


        return "redirect:/InformacionUsuario";
    }

}
