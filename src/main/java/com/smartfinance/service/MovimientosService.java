package com.smartfinance.service;

import java.util.List;

import com.smartfinance.dto.TransaccionDto;

public interface MovimientosService {
	public List<TransaccionDto> obtenerTodosLosMovimientos();

	public List<TransaccionDto> obtenerMovimientosMonth(int month, int year);

	public List<TransaccionDto> obtenerMovimientosYear(int year);
}
