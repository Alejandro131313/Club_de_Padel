package com.spring.start.h2.jugador;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JugadorDAO extends CrudRepository<Jugador, Long> {

	
}
