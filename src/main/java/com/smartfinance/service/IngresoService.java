package com.smartfinance.service;

import java.util.List;

import com.smartfinance.dto.IngresoDto;

public interface IngresoService {

	public IngresoDto crearIngreso(IngresoDto request);

	public List<IngresoDto> obtenerTodos();

	public List<IngresoDto> ingresosMonth(int month, int year);

	public List<IngresoDto> ingresosYear(int year);

	public IngresoDto actualizarIngreso(IngresoDto request);

	public boolean eliminarIngreso(IngresoDto request);
}
