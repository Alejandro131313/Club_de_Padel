package com.spring.start.h2.clase;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.start.h2.jugador.Jugador;

public interface ClaseDAO extends CrudRepository<Clase, Long> {

	@Query(value = "SELECT * FROM JUGADOR WHERE ID like %:id%", nativeQuery = true)
	List<Jugador> comoNosDeLaGana(Long id);

	
	@Query("SELECT c FROM Clase c WHERE c.id NOT IN (SELECT e.clase.id FROM Enmarca e WHERE e.jugador.id = :jugadorId)")
	List<Clase> findClasesNoRelacionadasPorJugadorId(@Param("jugadorId") Long jugadorId);
	
	

	
	@Query("SELECT c.Hora FROM Clase c WHERE c.dia = :dia")
	List<String> findClasesPorDia(@Param("dia") String dia);

	
	@Query("SELECT c FROM Clase c WHERE c.id NOT IN (SELECT e.clase.id FROM Enmarca e WHERE e.jugador.id = :jugadorId) " +
		       "AND c.Nivel = :Nivel AND (SELECT COUNT(e2) FROM Enmarca e2 WHERE e2.clase.id = c.id) < 4")
		List<Clase> findClasesDispoJugador(@Param("jugadorId") long jugadorId, @Param("Nivel") String Nivel);

	
	
	@Query("SELECT c FROM Clase c WHERE c.id NOT IN (SELECT e.clase.id FROM Enmarca e WHERE e.jugador.id = :jugadorId) " )
		List<Clase> findClasesJugador(@Param("jugadorId") long jugadorId);
	
	
	
	

}