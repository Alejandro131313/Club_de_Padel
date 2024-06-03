package com.spring.start.h2.Enmarca;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.start.h2.clase.Clase;
import com.spring.start.h2.jugador.Jugador;



public interface EnmarcaDAO extends CrudRepository<Enmarca, EnmarcaKey> {

	

	@Query("SELECT c FROM Clase c WHERE c.id NOT IN (SELECT e.clase.id FROM Enmarca e WHERE e.jugador.id = :jugadorId)")
    List<Clase> findClasesNoRelacionadas(@Param("jugadorId") Long jugadorId);
	
	Enmarca findByJugadorAndClase(Jugador jugador, Clase clase);
}
