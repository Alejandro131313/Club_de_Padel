package com.spring.start.h2.torneos;

import java.sql.Date;
import java.util.List;

import com.spring.start.h2.equipo.Equipo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Torneo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_torneo;
	
	@Size(min = 4, max = 50, message = "El nombre  debe tener entre 4 y 50 letras")
	private String Nombre;
	private Date Fecha;
	 @Min(value = 0, message = "El premio debe ser un valor positivo")
	private double Premio;
	
	
	@OneToMany(targetEntity=Equipo.class,mappedBy="torneo")
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
