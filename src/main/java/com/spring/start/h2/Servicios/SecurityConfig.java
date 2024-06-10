package com.spring.start.h2.Servicios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

	
	@SuppressWarnings({ "deprecation", "removal" })
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.csrf().disable().authorizeRequests(requests -> requests
				 .requestMatchers("/", "/login", "/loginUsuario", "/loginTrabajador").permitAll()
			.requestMatchers("/InformacionUsuario","/InformacionClub/**","/equipoUsuario/**","/crearEquipo","/cambiarEquipo","/jugador/cambiarEquipo","/editarTorneo/**","/cambiarContraseña").hasAuthority("USER")	
			.requestMatchers("/menu","/clases","/jugadores","/equipos","/torneos","/usuarios","/enmarca/**","/clase/**","/jugador/**","/equipo/**","/torneo/**","/usuario/**","/estadisticas","/cambiarContraseña").hasAuthority("ADMIN"))
			.formLogin()
                 .permitAll();
		 
		 

		return http.build();
	}
	
	
//	.formLogin(form -> form
//  .loginPage("/login")
//     .permitAll());
	
	
	    @Bean
	    BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    
	    
	   
	   @Bean
	   AuthenticationManager authenticationManager(
			   HttpSecurity http, 
			   BCryptPasswordEncoder bCryptPasswordEncoder, 
			   UserDetailsService userDetailsService) throws Exception {
		   
	       return http.getSharedObject(AuthenticationManagerBuilder.class)
	         .userDetailsService(userDetailsService)
	         .passwordEncoder(bCryptPasswordEncoder)
	         .and()
	         .build();
	   }
	   
	
}