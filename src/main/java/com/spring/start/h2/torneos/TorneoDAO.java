package com.spring.start.h2.torneos;

	import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.start.h2.equipo.Equipo;
import com.spring.start.h2.jugador.Jugador;

	public interface TorneoDAO  extends CrudRepository<Torneo, Long> {
		
		@Query(value = "SELECT * FROM Torneo WHERE ID like %:id%", nativeQuery = true)
		List<Equipo> comoNosDeLaGana(Long id);

	
	
	}

