package com.smartfinance.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
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
	private String password;

}
