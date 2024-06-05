package com.spring.start.h2.Servicios;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {
	
	
	
	 @GetMapping("/login")
	    public ModelAndView login() {
		 ModelAndView modelAndView = new ModelAndView();
	       
	        
	        modelAndView.setViewName("login"); 
	        return modelAndView;

	    }

		
	 @GetMapping("/")
	    public ModelAndView pagina() {
		 ModelAndView modelAndView = new ModelAndView();
	       
	        
	        modelAndView.setViewName("PaginaWeb/PaginaPrincipal.html"); 
	        return modelAndView;

	    }
	
	

}
