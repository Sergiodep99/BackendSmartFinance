package com.smartfinance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "El nombre no puede estar en blanco")
	@Size(max = 50)
	private String nombre;
	@NotBlank(message = "Los apellidos no pueden estar en blanco")
	@Size(max = 100)
	private String apellidos;
	@NotBlank(message = "El correo no puede estar en blanco")
	@Email(message = "El correo debe ser válido")
	@Size(max = 100)
	@Column(unique = true)
	private String email;
	@NotBlank(message = "La contraseña no puede estar en blanco")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	@JsonIgnore
	private String password;

	public Usuario(String nombre, String apellidos, String email, String password) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
	}
}
