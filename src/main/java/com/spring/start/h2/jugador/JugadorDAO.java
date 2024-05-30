package com.spring.start.h2.jugador;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.start.h2.equipo.Equipo;

public interface JugadorDAO extends CrudRepository<Jugador, Long> {

	@Query("SELECT j FROM Jugador j WHERE j.equipo IS NULL")
    List<Jugador> findByEquipoIsNull();

	
	
	@Query("SELECT j FROM Jugador j ORDER BY j.Nombre ASC")
    List<Jugador> findAllOrderByNombreAsc();

    @Query("SELECT j FROM Jugador j ORDER BY j.Edad ASC")
    List<Jugador> findAllOrderByEdadAsc();
	
	
    
    @Query("SELECT j  FROM Jugador j " +
            "WHERE SIZE(j.Enmarca) = (SELECT MAX(SIZE(j2.Enmarca)) FROM Jugador j2)")
     List<Jugador> findJugadoresConMasClases();

    
    @Query("SELECT j FROM Jugador j WHERE j.Edad = (SELECT MIN(j2.Edad) FROM Jugador j2)")
    List<Jugador> findJugadorMasJoven();

    @Query("SELECT j FROM Jugador j WHERE j.Edad = (SELECT MAX(j2.Edad) FROM Jugador j2)")
    List<Jugador> findJugadorMasViejo();
    

    @Query("SELECT j FROM Jugador j WHERE j.Edad < :edad")
    List<Jugador> findJugadoresMenores(@Param("edad") int edad);








    
    
}
