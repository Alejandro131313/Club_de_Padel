package com.spring.start.h2.Estadisticas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model) {
    	
        List<Jugador> jugadoresConMasClases = jugadorDao.findJugadoresConMasClases();
        model.addAttribute("jugadoresConMasClases", jugadoresConMasClases);
        
        
        Jugador jugadorMasJoven = jugadorDao.findJugadorMasJoven();
        model.addAttribute("jugadorMasJoven", jugadorMasJoven);
        
        
        Jugador jugadorMasViejo = jugadorDao.findJugadorMasViejo();
        model.addAttribute("jugadorMasViejo", jugadorMasViejo);
        
       
        List<Equipo> equiposConMasPremios = equipoDao.findEquipoConMasPremios();
        model.addAttribute("equiposConMasPremios", equiposConMasPremios);
        

        List<Equipo> equiposConMasJugadores = equipoDao.findEquipoConMasJugadores();
        model.addAttribute("equiposConMasJugadores", equiposConMasJugadores);
        
        
        return "estadisticas";
    }
    


    

    
}
