package com.spring.start.h2.torneos;

import java.sql.Date;
import java.util.List;

import com.spring.start.h2.equipo.Equipo;
import com.spring.start.h2.jugador.Jugador;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Torneo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_torneo;
	
	private String Nombre;
	private Date Fecha;
	private double Premio;
	
	
	@OneToMany(targetEntity=Equipo.class,mappedBy="torneo",cascade = CascadeType.ALL)
	private List<Equipo> equipos;
	
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public long getId_torneo() {
		return id_torneo;
	}
	public void setId_torneo(long id_torneo) {
		this.id_torneo = id_torneo;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public double getPremio() {
		return Premio;
	}
	public void setPremio(double premio) {
		Premio = premio;
	}
	
	
	
	
	public List<Equipo> getEquipos() {
		return equipos;
	}
	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
	@Override
	public String toString() {
		return "Torneo [id_torneo=" + id_torneo + ", Nombre=" + Nombre + ", Fecha=" + Fecha + ", Premio=" + Premio
				+ ", equipos=" + equipos + "]";
	}



	
	
	
	
	
}
