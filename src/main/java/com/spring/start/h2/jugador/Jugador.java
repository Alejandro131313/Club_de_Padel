package com.spring.start.h2.jugador;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.start.h2.Enmarca.Enmarca;
import com.spring.start.h2.Usuarios.Usuario;
import com.spring.start.h2.equipo.Equipo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Jugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Size(min = 4, max = 36, message = "El nombre debe tener entre 4 y 16 letras")
	private String Nombre;
	 @Min(value = 5, message = "Eres demasiado joven para apuntarte")
	 @Max(value = 70, message = "Eres demasiado viejo para apuntarte")
	private int Edad;
	 @Pattern(regexp = "^(Alto|Medio|Principiante)$", message = "El nivel debe ser Alto, Medio o Principiante")
	private String Nivel;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_Equipo")
	@JsonBackReference
	Equipo equipo;
	
	
	@OneToMany(targetEntity = Enmarca.class, mappedBy = "jugador",cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<Enmarca> Enmarca;
	
	
	 @OneToOne(mappedBy = "jugador", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	    private Usuario usuario;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public int getEdad() {
		return Edad;
	}


	public void setEdad(int edad) {
		Edad = edad;
	}


	public String getNivel() {
		return Nivel;
	}


	public void setNivel(String nivel) {
		Nivel = nivel;
	}


	public Equipo getEquipo() {
		return equipo;
	}


	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}


	public Set<Enmarca> getEnmarca() {
		return Enmarca;
	}


	public void setEnmarca(Set<Enmarca> enmarca) {
		Enmarca = enmarca;
	}


	@Override
	public String toString() {
	    return "Jugador [id=" + id + ", Nombre=" + Nombre + ", Edad=" + Edad + ", Nivel=" + Nivel + ", equipo=" + equipo
	            + "]";
	}

	



}