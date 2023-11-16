package com.smartfinance.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfinance.dto.GastoDto;
import com.smartfinance.dto.IngresoDto;
import com.smartfinance.dto.TransaccionDto;
import com.smartfinance.service.GastoService;
import com.smartfinance.service.IngresoService;

@Service
public class MovimientosService implements com.smartfinance.service.MovimientosService {

	@Autowired
	private IngresoService ingresoService;

	@Autowired
	private GastoService gastoService;

	@Override
	public List<TransaccionDto> obtenerTodosLosMovimientos() {
		List<IngresoDto> ingresos = ingresoService.obtenerTodos();
		List<GastoDto> gastos = gastoService.obtenerTodos();

		List<TransaccionDto> transacciones = new ArrayList<>();
		transacciones.addAll(mapIngresosToTransaccionDto(ingresos));
		transacciones.addAll(mapGastosToTransaccionDto(gastos));

		// Ordenar las transacciones por fecha
		List<TransaccionDto> transaccionesOrdenadas = transacciones.stream()
				.sorted(Comparator.comparing(TransaccionDto::getFecha)).collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(transaccionesOrdenadas);

		return transaccionesOrdenadas;
	}

	@Override
	public List<TransaccionDto> obtenerMovimientosMonth(int month, int year) {
		List<IngresoDto> ingresos = ingresoService.ingresosMonth(month, year);
		List<GastoDto> gastos = gastoService.gastosMonth(month, year);

		List<TransaccionDto> transacciones = new ArrayList<>();
		transacciones.addAll(mapIngresosToTransaccionDto(ingresos));
		transacciones.addAll(mapGastosToTransaccionDto(gastos));

		// Ordenar las transacciones por fecha
		List<TransaccionDto> transaccionesOrdenadas = transacciones.stream()
				.sorted(Comparator.comparing(TransaccionDto::getFecha)).collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(transaccionesOrdenadas);

		return transaccionesOrdenadas;
	}

	@Override
	public List<TransaccionDto> obtenerMovimientosYear(int year) {
		List<IngresoDto> ingresos = ingresoService.ingresosYear(year);
		List<GastoDto> gastos = gastoService.gastosYear(year);

		List<TransaccionDto> transacciones = new ArrayList<>();
		transacciones.addAll(mapIngresosToTransaccionDto(ingresos));
		transacciones.addAll(mapGastosToTransaccionDto(gastos));

		// Ordenar las transacciones por fecha
		List<TransaccionDto> transaccionesOrdenadas = transacciones.stream()
				.sorted(Comparator.comparing(TransaccionDto::getFecha)).collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(transaccionesOrdenadas);

		return transaccionesOrdenadas;
	}

	private List<TransaccionDto> mapIngresosToTransaccionDto(List<IngresoDto> ingresos) {
		return ingresos
				.stream().map(ingreso -> new TransaccionDto(ingreso.getId(), ingreso.getDescripcion(),
						ingreso.getCantidad(), ingreso.getFecha(), ingreso.getUsuarioId(), "ingreso"))
				.collect(Collectors.toList());
	}

	private List<TransaccionDto> mapGastosToTransaccionDto(List<GastoDto> gastos) {
		return gastos.stream().map(gasto -> new TransaccionDto(gasto.getId(), gasto.getDescripcion(),
				gasto.getCantidad(), gasto.getFecha(), gasto.getUsuarioId(), "gasto")).collect(Collectors.toList());
	}

}
