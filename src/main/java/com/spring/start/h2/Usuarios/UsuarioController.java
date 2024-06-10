package com.spring.start.h2.Usuarios;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.Enmarca.Enmarca;
import com.spring.start.h2.Enmarca.EnmarcaDAO;
import com.spring.start.h2.Enmarca.EnmarcaKey;
import com.spring.start.h2.clase.Clase;
import com.spring.start.h2.clase.ClaseDAO;
import com.spring.start.h2.equipo.Equipo;
import com.spring.start.h2.equipo.EquipoDAO;
import com.spring.start.h2.jugador.Jugador;
import com.spring.start.h2.jugador.JugadorDAO;
import com.spring.start.h2.torneos.Torneo;
import com.spring.start.h2.torneos.TorneoDAO;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ClaseDAO claseDAO;

    @Autowired
    private EnmarcaDAO enmarcaDAO;
    
    @Autowired
    private EquipoDAO equipoDAO;
    
    @Autowired
    private TorneoDAO torneoDAO;
    
    @Autowired
    private JugadorDAO jugadorDAO;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    
    @GetMapping("/InformacionClub/{id}")
    public ModelAndView mostrarInfoClub(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Obtener el equipo por su ID
        Equipo equipo = equipoDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
        
        // Agregar el equipo al modelo
        modelAndView.addObject("equipo", equipo);
        
        // Obtener los jugadores del equipo
        List<Jugador> jugadores = equipo.getJugadores();
        modelAndView.addObject("jugadores", jugadores);
        
        modelAndView.setViewName("Usuarios/ClubUsuario");
        
        return modelAndView;
    }

    @GetMapping("/crearEquipo")
    public ModelAndView  mostrarFormularioCrearEquipo() {
        ModelAndView model = new ModelAndView();
        model.addObject("equipo", new Equipo());
        model.addObject("torneos", torneoDAO.findAll());

        
        model.setViewName("Usuarios/CrearEquipoUsuario");
        return model;
    }
    

    @PostMapping("/equipoUsuario/save")
    public String guardarEquipoUsuario(@ModelAttribute Equipo equipo) {

        // Guardar el equipo
        equipoDAO.save(equipo);
        
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        Usuario usuario = usuarioDAO.findByUsuario(nombreUsuario);

        // Obtener el jugador asociado al usuario
        Jugador jugador = usuario.getJugador();
        
        // Asignar el equipo al jugador
        jugador.setEquipo(equipo);
        
        // Guardar los cambios en el jugador
        jugadorDAO.save(jugador);

        return "redirect:/InformacionUsuario";
    }


    @GetMapping("/cambiarEquipo")
    public ModelAndView mostrarFormularioCambiarEquipo() {
    	ModelAndView model = new ModelAndView();
        model.addObject("equipos", equipoDAO.findAll());
        
        model.setViewName("Usuarios/cambiarEquipoUsuario");
        return model;
    }
    
    
    

    @PostMapping("/jugador/cambiarEquipo")
    public String cambiarEquipo(@RequestParam("equipoId") Long equipoId) {
        Equipo nuevoEquipo = equipoDAO.findById(equipoId).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
    
    	
     // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        Usuario usuario = usuarioDAO.findByUsuario(nombreUsuario);
        
     // Obtener el jugador asociado al usuario
        Jugador jugador = usuario.getJugador();
        
        
        jugador.setEquipo(nuevoEquipo);
        jugadorDAO.save(jugador);

        return "redirect:/InformacionUsuario";
    }
    
    
    @GetMapping("/editarTorneo/{id}")
    public ModelAndView mostrarFormularioEditarTorneo(@PathVariable Long id) {
        ModelAndView model = new ModelAndView();
        Equipo equipo = equipoDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
        model.addObject("equipo", equipo);
        model.addObject("torneos", torneoDAO.findAll());
        model.setViewName("Usuarios/CambiarTorneo");
        return model;
    }
    

    @PostMapping("/editarTorneo/save")
    public String actualizarTorneo(@RequestParam Long equipoId, @RequestParam Long torneoId) {
        Equipo equipo = equipoDAO.findById(equipoId).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
        Torneo torneo = torneoDAO.findById(torneoId).orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));
        equipo.setTorneo(torneo);
        equipoDAO.save(equipo);

        return "redirect:/InformacionClub/" + equipoId;
    }
    
    @GetMapping("/cambiarContraseña")
    public ModelAndView cambiarContraseña() {
        ModelAndView modelAndView = new ModelAndView("Usuarios/CambiarContraseña");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        
        // Obtener el usuario
        Usuario usuario = usuarioDAO.findByUsuario(nombreUsuario);
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }

    
    
    
    
    @PostMapping("/cambiarContraseña")
    public ModelAndView cambiarContraseña(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult, @RequestParam("confirmPassword") String confirmPassword) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("UsuariosAdmin/cambiarContraseña");
            return modelAndView;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        Usuario usuarioExistente = usuarioDAO.findByUsuario(nombreUsuario);

        if (usuarioExistente != null) {
            if (usuario.getPassword().equals(confirmPassword)) {
                usuarioExistente.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
                usuarioDAO.save(usuarioExistente);
                modelAndView.setViewName("redirect:/");
            } else {
                modelAndView.setViewName("UsuariosAdmin/cambiarContraseña");
                modelAndView.addObject("error", "Las contraseñas no coinciden. Inténtalo de nuevo.");
            }
        } else {
            modelAndView.setViewName("UsuariosAdmin/cambiarContraseña");
            modelAndView.addObject("error", "No se pudo cambiar la contraseña. Inténtalo de nuevo.");
        }

        return modelAndView;
    }

}

