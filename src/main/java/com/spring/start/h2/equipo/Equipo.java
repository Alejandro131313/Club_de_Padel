package com.spring.start.h2.equipo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.start.h2.jugador.Jugador;
import com.spring.start.h2.torneos.Torneo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Equipo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_equipo;
	
	 @Min(value = 0, message = "Los premios deben ser min 1")
	 @Max(value = 100, message = "Los premios deben ser max 100")
	private int Premios;
	
	@Size(min = 4, max = 20, message = "El nombre debe tener entre 4 y 20 letras")
	private String Nombre_equipo;


	@OneToMany(targetEntity=Jugador.class,mappedBy="equipo")
	private List<Jugador> jugadores;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_Torneo")
	@JsonBackReference
	Torneo torneo;
	
	
	

	public long getId_equipo() {
		return id_equipo;
	}


	public void setId_equipo(long id_equipo) {
		this.id_equipo = id_equipo;
	}


	public int getPremios() {
		return Premios;
	}


	public void setPremios(int premios) {
		Premios = premios;
	}


	public String getNombre_equipo() {
		return Nombre_equipo;
	}


	public void setNombre_equipo(String nombre_equipo) {
		Nombre_equipo = nombre_equipo;
	}


	public List<Jugador> getJugadores() {
		return jugadores;
	}


	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}


	public Torneo getTorneo() {
		return torneo;
	}


	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}


	@Override
	public String toString() {
	    return "Equipo [id_equipo=" + id_equipo + ", Premios=" + Premios + ", Nombre_equipo=" + Nombre_equipo + "]";
	}





}