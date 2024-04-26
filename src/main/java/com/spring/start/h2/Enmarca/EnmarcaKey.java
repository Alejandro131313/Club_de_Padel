package com.spring.start.h2.Enmarca;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EnmarcaKey implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	private Long jugadorId;
	@Column
	private Long claseId;
	public Long getJugadorId() {
		return jugadorId;
	}
	public void setJugadorId(Long jugadorId) {
		this.jugadorId = jugadorId;
	}
	public Long getClaseId() {
		return claseId;
	}
	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(claseId, jugadorId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnmarcaKey other = (EnmarcaKey) obj;
		return Objects.equals(claseId, other.claseId) && Objects.equals(jugadorId, other.jugadorId);
	}



}
