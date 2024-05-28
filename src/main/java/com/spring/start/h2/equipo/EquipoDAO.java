package com.spring.start.h2.equipo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.start.h2.jugador.Jugador;

public interface EquipoDAO extends CrudRepository<Equipo, Long> {

	@Query(value = "SELECT * FROM EQUIPO WHERE ID like %:id%", nativeQuery = true)
	List<Equipo> comoNosDeLaGana(Long id);

	
	@Query("SELECT e FROM Equipo e WHERE e.Premios = (SELECT MAX(e2.Premios) FROM Equipo e2)")
    List<Equipo> findEquipoConMasPremios();

	 @Query("SELECT e FROM Equipo e WHERE SIZE(e.jugadores) = (SELECT MAX(SIZE(e2.jugadores)) FROM Equipo e2)")
	    List<Equipo> findEquipoConMasJugadores();


	 @Query("SELECT e.jugadores FROM Equipo e WHERE e.Nombre_equipo = :nombreEquipo")
	    List<Jugador> findByNombreEquipo(@Param("nombreEquipo") String nombreEquipo);
	  
}
