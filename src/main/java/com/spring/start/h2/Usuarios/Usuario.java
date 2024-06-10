package com.spring.start.h2.Usuarios;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.start.h2.jugador.Jugador;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario implements UserDetails {

	@Id
	@Size(min = 4, max = 36, message = "El Usuario debe tener entre 4 y 36 letras")
	private String usuario;
	 @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
	private String password;
	
	 private int rol;
	
	 @OneToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "jugador_id")
	    private Jugador jugador;
	 
	 
	 public int getRol() {
	        return rol;
	    }

	    public void setRol(int rol) {
	        this.rol = rol;
	    }

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        ArrayList<SimpleGrantedAuthority> permisos = new ArrayList<>();
	        SimpleGrantedAuthority permiso;
	        if (rol == 1) {
	            permiso = new SimpleGrantedAuthority("ADMIN");
	        } else if (rol == 2) {
	            permiso = new SimpleGrantedAuthority("USER");
	        } else {
	            permiso = new SimpleGrantedAuthority("GUEST");
	        }
	        permisos.add(permiso);
	        return permisos;
	    }

	
	
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuario;
	}
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", password=" + password + "]";
	}



}