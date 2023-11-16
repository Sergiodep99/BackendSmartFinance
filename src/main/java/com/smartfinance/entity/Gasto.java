package com.smartfinance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Gasto")
public class Gasto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "La descripción del gasto no puede estar en blanco")
	@Size(max = 255)
	private String descripcion;

	@NotNull(message = "El monto del gasto no puede estar en blanco")
	@DecimalMax(value = "-0.01", message = "El monto del gasto debe ser menor que 0")
	private BigDecimal cantidad;

	@NotNull(message = "La fecha del gasto no puede estar en blanco")
	private LocalDateTime fecha;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
}
