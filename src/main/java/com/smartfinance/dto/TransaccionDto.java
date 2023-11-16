package com.smartfinance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDto {
	private Long id;
	private String descripcion;
	private BigDecimal cantidad;
	private LocalDateTime fecha;
	private Long usuarioId;
	private String tipoMovimiento;
}
