package com.spring.start.h2.Estadisticas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.start.h2.clase.Clase;
import com.spring.start.h2.clase.ClaseDAO;
import com.spring.start.h2.equipo.Equipo;
import com.spring.start.h2.equipo.EquipoDAO;
import com.spring.start.h2.jugador.Jugador;
import com.spring.start.h2.jugador.JugadorDAO;

@Controller
public class EstadisticasController {

    @Autowired
    private JugadorDAO jugadorDao;
    
    @Autowired
    private EquipoDAO equipoDao;
    
    @Autowired
    private ClaseDAO claseDao;
    
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model) {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String nombreUsuario = auth.getName();
         model.addAttribute("nombreUsuario", nombreUsuario);

        List<Jugador> jugadoresConMasClases = jugadorDao.findJugadoresConMasClases();
        model.addAttribute("jugadoresConMasClases", jugadoresConMasClases);
        
        
        List<Jugador> jugadorMasJoven = jugadorDao.findJugadorMasJoven();
        model.addAttribute("jugadorMasJoven", jugadorMasJoven);
        
        
        List<Jugador> jugadorMasViejo = jugadorDao.findJugadorMasViejo();
        model.addAttribute("jugadorMasViejo", jugadorMasViejo);
   
        List<Jugador> jugadoresMenoresDeEdad = jugadorDao.findJugadoresMenores(18);
        model.addAttribute("jugadoresMenoresDeEdad", jugadoresMenoresDeEdad);
        
        
       
        List<Equipo> equiposConMasPremios = equipoDao.findEquipoConMasPremios();
        model.addAttribute("equiposConMasPremios", equiposConMasPremios);
        

        List<Equipo> equiposConMasJugadores = equipoDao.findEquipoConMasJugadores();
        model.addAttribute("equiposConMasJugadores", equiposConMasJugadores);
        
        
        return "estadisticas";
    }
    


    @GetMapping("/clasesPorDia")
    @ResponseBody
    public List<String> getClasesPorDia(@RequestParam String dia) {
    	 return claseDao.findClasesPorDia(dia);
    }

    
}
