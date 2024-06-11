package com.spring.start.h2.Usuarios;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class JugadorUsuarioDTO {

    @Size(min = 4, max = 50, message = "El nombre y Apellidos debe tener entre 4 y 50 letras")
    private String nombre;

    @Min(value = 5, message = "Eres demasiado joven para apuntarte")
    @Max(value = 70, message = "Eres demasiado viejo para apuntarte")
    private int edad;

    @Pattern(regexp = "^(Alto|Medio|Principiante)$", message = "El nivel debe ser Alto, Medio o Principiante")
    private String nivel;

    @Size(min = 4, max = 36, message = "El Usuario debe tener entre 4 y 36 letras")
    private String usuario;

    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    private String email;

    private int rol = 2;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}


}
