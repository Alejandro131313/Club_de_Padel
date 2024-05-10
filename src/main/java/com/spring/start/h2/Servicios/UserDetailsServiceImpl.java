package com.spring.start.h2.Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.start.h2.Usuarios.Usuario;
import com.spring.start.h2.Usuarios.UsuarioDAO;





@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Usuario> usuario = usuarioDao.findById(username);
		if(usuario.isPresent()) {
			
			return (UserDetails)usuario.get();
		}
		
		throw new UsernameNotFoundException(username);
	}
}