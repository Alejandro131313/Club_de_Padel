package com.spring.start.h2.Usuarios;


import org.springframework.data.repository.CrudRepository;

public interface UsuarioDAO extends CrudRepository<Usuario, String> {

	Usuario findByUsuario(String nombreUsuario);


	


}