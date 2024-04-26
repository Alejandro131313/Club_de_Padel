package com.spring.start.h2.jugador;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JugadorDAO extends CrudRepository<Jugador, Long> {

	@Query("SELECT j FROM Jugador j WHERE j.equipo IS NULL")
    List<Jugador> findByEquipoIsNull();

	

	
}
