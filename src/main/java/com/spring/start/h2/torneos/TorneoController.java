package com.spring.start.h2.torneos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.spring.start.h2.equipo.Equipo;
import com.spring.start.h2.equipo.EquipoDAO;
import com.spring.start.h2.jugador.JugadorDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TorneoController {

    @Autowired
    private TorneoDAO torneoDAO;

    @Autowired
    private EquipoDAO equipoDAO;

    @Autowired
    private JugadorDAO jugadorDAO;

    @GetMapping("/torneos")
    public ModelAndView torneos() {
        ModelAndView modelAndView = new ModelAndView();
        // Agregar nombre de usuario para mostrarlo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.setViewName("torneos");
        modelAndView.addObject("torneos", torneoDAO.findAll());
        return modelAndView;
    }

    @GetMapping("/torneo/{id}")
    public ModelAndView torneo(@PathVariable long id) {
        Torneo torneo = torneoDAO.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        modelAndView.addObject("nombreUsuario", nombreUsuario);
        modelAndView.setViewName("torneo");
        modelAndView.addObject("torneo", torneo);
        modelAndView.addObject("equipos", torneo.getEquipos()); 
        return modelAndView;
    }

    @GetMapping("/torneo/delete/{id}")
    public ModelAndView deleteTorneo(@PathVariable long id) {
        torneoDAO.deleteById(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/torneos");
        return model;
    }


//Crear otro html para crear 
    @GetMapping("/torneo/add")
    public ModelAndView addTorneo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formtorneos");
        modelAndView.addObject("torneo", new Torneo());
        modelAndView.addObject("equipos", equipoDAO.findAll());
        return modelAndView;
    }


    @GetMapping("/torneo/edit/{id}")
    public ModelAndView editTorneo(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formtorneos");
        modelAndView.addObject("torneo", torneoDAO.findById(id).orElse(null));
        Torneo torneo = torneoDAO.findById(id).orElse(null);
        modelAndView.addObject("equipos",equipoDAO.findAll() ); 
        return modelAndView;
    }


    @PostMapping("/torsneo/save")
    public ModelAndView saveTorneo(@ModelAttribute("torneo") @Valid Torneo torneo, BindingResult bindingResult,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("formtorneos");
            return modelAndView;
        }
        
        String[] equiposSeleccionados = request.getParameterValues("equiposSeleccionados");
        if (equiposSeleccionados != null) {
            List<Equipo> equiposSeleccionadosList = new ArrayList<>();
            for (String equipoId : equiposSeleccionados) {
                Long id = Long.valueOf(equipoId);
                Optional<Equipo> equipoOptional = equipoDAO.findById(id);
                equipoOptional.ifPresent(equipo -> {
                    equipo.setTorneo(torneo);
                    equiposSeleccionadosList.add(equipo);
                });
            }
            torneo.setEquipos(equiposSeleccionadosList);
        }
        
        torneoDAO.save(torneo);
        modelAndView.setViewName("redirect:/torneos");
        return modelAndView;
    }


    

    @GetMapping("/torneo/remove-equipo/{torneoId}/{equipoId}")
    public ModelAndView removeEquipoTorneo(@PathVariable long torneoId, @PathVariable long equipoId) {
        Optional<Torneo> torneoOptional = torneoDAO.findById(torneoId);
        Optional<Equipo> equipoOptional = equipoDAO.findById(equipoId);

        if (torneoOptional.isPresent() && equipoOptional.isPresent()) {
            Torneo torneo = torneoOptional.get();
            Equipo equipo = equipoOptional.get();

            equipo.setTorneo(null); 
            equipoDAO.save(equipo);

            
            return new ModelAndView("redirect:/torneo/" + torneoId);
        } else {
            return new ModelAndView("redirect:/torneos");
        }
    }


}
