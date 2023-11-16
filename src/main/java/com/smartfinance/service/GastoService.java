package com.smartfinance.service;

import java.util.List;

import com.smartfinance.dto.GastoDto;

public interface GastoService {
	public GastoDto crearGasto(GastoDto request);

	public List<GastoDto> obtenerTodos();

	public List<GastoDto> gastosMonth(int month, int year);

	public List<GastoDto> gastosYear(int year);

	public GastoDto actualizarGasto(GastoDto request);

	public boolean eliminarGasto(GastoDto request);
}
