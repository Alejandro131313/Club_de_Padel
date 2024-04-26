package com.spring.start.h2.clase;


import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.start.h2.Enmarca.Enmarca;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
public class Clase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Pattern(regexp = "^(Lunes|Martes|Miércoles|Jueves|Viernes)$", message = "El día debe ser Lunes, Martes, Miércoles, Jueves o Viernes")
	String dia;
	
	@Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "La hora debe tener el formato 00:00")
	String Hora;

	
	@OneToMany(mappedBy = "clase", targetEntity=Enmarca.class,cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<Enmarca> Enmarca = new HashSet<>();

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHora() {
		return Hora;
	}

	public void setHora(String hora) {
		Hora = hora;
	}

	public Set<Enmarca> getEnmarca() {
		return Enmarca;
	}

	public void setEnmarca(Set<Enmarca> enmarca) {
		Enmarca = enmarca;
	}

	@Override
	public String toString() {
		return "Clase [id=" + id + ", dia=" + dia + ", Hora=" + Hora + ", Enmarca=" + Enmarca + "]";
	}



}
