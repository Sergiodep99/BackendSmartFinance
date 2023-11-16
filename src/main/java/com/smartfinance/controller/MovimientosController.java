package com.smartfinance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfinance.dto.TransaccionDto;
import com.smartfinance.service.impl.MovimientosService;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("api/movimientos")
public class MovimientosController {

	@Autowired
	private MovimientosService movimientosService;

	@GetMapping(value = "/obtenerTodosLosMovimientos")
	public ResponseEntity<List<TransaccionDto>> obtenerTodosLosMovimientos() {
		List<TransaccionDto> response = movimientosService.obtenerTodosLosMovimientos();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/obtenerMovimientosMonth/{month}/{year}")
	public ResponseEntity<List<TransaccionDto>> obtenerMovimientosMonth(@PathVariable int month,
			@PathVariable int year) {
		List<TransaccionDto> response = movimientosService.obtenerMovimientosMonth(month, year);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/obtenerMovimientosYear/{year}")
	public ResponseEntity<List<TransaccionDto>> obtenerMovimientosYear(@PathVariable int year) {
		List<TransaccionDto> response = movimientosService.obtenerMovimientosYear(year);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
