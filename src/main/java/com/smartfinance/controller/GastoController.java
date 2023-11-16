package com.smartfinance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfinance.dto.GastoDto;
import com.smartfinance.service.GastoService;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("api/gastos")
public class GastoController {

	@Autowired
	private GastoService gastoService;

	@PostMapping(value = "/crearGasto")
	public ResponseEntity<GastoDto> crearGasto(@RequestBody GastoDto request) {
		GastoDto response = gastoService.crearGasto(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "/obtenerTodos")
	public ResponseEntity<List<GastoDto>> obtenerTodos() {
		List<GastoDto> response = gastoService.obtenerTodos();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/gastosMonth/{month}/{year}")
	public ResponseEntity<List<GastoDto>> gastosMonth(@PathVariable int month, @PathVariable int year) {

		List<GastoDto> response = gastoService.gastosMonth(month, year);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/gastosYear/{year}")
	public ResponseEntity<List<GastoDto>> gastosYear(@PathVariable int year) {
		List<GastoDto> response = gastoService.gastosYear(year);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/actualizarGasto")
	public ResponseEntity<GastoDto> actualizarGasto(@RequestBody GastoDto request) {
		GastoDto response = gastoService.actualizarGasto(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/eliminarGasto")
	public boolean eliminarGasto(@RequestBody GastoDto request) {
		boolean borrado = gastoService.eliminarGasto(request);
		return borrado;
	}
}
