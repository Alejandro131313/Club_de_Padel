package com.spring.start.h2.Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.h2.Usuarios.Usuario;
import com.spring.start.h2.Usuarios.UsuarioDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SecurityController {
	
	
	 @Autowired
	    private BCryptPasswordEncoder encriptador;


	    @Autowired
	    private UserDetailsServiceImpl userDetailsService;
	    
	 @Autowired
	    private UsuarioDAO usuarioDao;
	 

	
	  
	  
	  
		 @GetMapping("/")
		    public ModelAndView pagina() {
			 ModelAndView modelAndView = new ModelAndView();
		       
		        
		        modelAndView.setViewName("PaginaWeb/PaginaPrincipal.html"); 
		        return modelAndView;

		    }
		 
		 
		 @GetMapping("/PaginaClases")
		    public ModelAndView paginaclases() {
			 ModelAndView modelAndView = new ModelAndView();
		       
		        
		        modelAndView.setViewName("PaginaWeb/Clases.html"); 
		        return modelAndView;

		    }
		 @GetMapping("/PaginaTorneos")
		    public ModelAndView paginaTorneos() {
			 ModelAndView modelAndView = new ModelAndView();
		       
		        
		        modelAndView.setViewName("PaginaWeb/Torneos.html"); 
		        return modelAndView;

		    }
		 
		 @GetMapping("/PaginaInstalaciones")
		    public ModelAndView paginaInstalaciones() {
			 ModelAndView modelAndView = new ModelAndView();
		       
		        
		        modelAndView.setViewName("PaginaWeb/Instalaciones.html"); 
		        return modelAndView;

		    }
	
		 @GetMapping("/PaginaFormulario")
		    public ModelAndView paginaForm() {
			 ModelAndView modelAndView = new ModelAndView();
		       
		        
		        modelAndView.setViewName("PaginaWeb/Formulario.html"); 
		        return modelAndView;

		    }
		 
	 
//	 
//	  @GetMapping("/login")
//	    public ModelAndView login() {
//		 ModelAndView modelAndView = new ModelAndView();
//	       
//	       modelAndView.addObject("user", new Usuario());
//	        modelAndView.setViewName("login");
//      return modelAndView;
//
//  }
//	
//	
//	  @PostMapping("/loginUsuario")
//	  public ModelAndView loginUsuario(@ModelAttribute Usuario user, HttpServletRequest request) {
//	      return authenticateAndLogin(user, request, "/InformacionUsuario", "login");
//	  }
//
//	  @PostMapping("/loginTrabajador")
//	  public ModelAndView loginTrabajador(@ModelAttribute Usuario user, HttpServletRequest request) {
//	      return authenticateAndLogin(user, request, "/menu", "login");
//	  }
//
//	  
//	  
//	  public ModelAndView authenticateAndLogin(Usuario user, HttpServletRequest request, String successRedirect, String errorRedirect) {
//	      ModelAndView modelAndView = new ModelAndView();
//	      Optional<Usuario> usuarioOptional = usuarioDao.findById(user.getUsuario());
//
//	      if (usuarioOptional.isPresent()) {
//	          Usuario usuario = usuarioOptional.get();
//
//	          if (user.getPassword().equals(usuario.getPassword())== true) {
//	        	  System.out.println("Hola");
//	              UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsuario());
//	              HttpSession session = request.getSession();
//	              session.setAttribute("user", usuario);
//	              System.out.println(user);
//	              System.out.println(usuario);
//	              modelAndView.setViewName("redirect:" + successRedirect);
//	          } else {
//	              modelAndView.addObject("message", "Invalid username or password");
//	              modelAndView.setViewName(errorRedirect);
//	          }
//	      } else {
//	          modelAndView.addObject("message", "User not found");
//	          modelAndView.setViewName(errorRedirect);
//	      }
//
//	      return modelAndView;
//	  }
	 
	 
	 
	 

		

	
	

}
