package com.spring.start.h2.Enmarca;

import com.spring.start.h2.jugador.Jugador;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.start.h2.clase.Clase;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Enmarca {

	@EmbeddedId
	private EnmarcaKey id;

	@ManyToOne
	@MapsId("jugadorId")
	@JoinColumn(name = "jugador_id")
	@JsonManagedReference
	Jugador jugador;

	@ManyToOne
	@MapsId("claseId")
	@JoinColumn(name = "clase_id")
	@JsonManagedReference
	Clase clase;

	public EnmarcaKey getId() {
		return id;
	}

	public void setId(EnmarcaKey id) {
		this.id = id;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}
	

	@Override
	public String toString() {
		return "JugadorClase [" + ", jugador=" + jugador + ", clase=" + clase + "]";
	}

}
