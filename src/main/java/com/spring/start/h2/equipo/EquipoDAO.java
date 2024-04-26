package com.spring.start.h2.equipo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EquipoDAO extends CrudRepository<Equipo, Long> {

	@Query(value = "SELECT * FROM EQUIPO WHERE ID like %:id%", nativeQuery = true)
	List<Equipo> comoNosDeLaGana(Long id);

}
