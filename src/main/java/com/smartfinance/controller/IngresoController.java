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

import com.smartfinance.dto.IngresoDto;
import com.smartfinance.service.IngresoService;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("api/ingresos")
public class IngresoController {

	@Autowired
	private IngresoService ingresoService;

	@PostMapping(value = "/crearIngreso")
	public ResponseEntity<IngresoDto> crearIngreso(@RequestBody IngresoDto request) {
		IngresoDto response = ingresoService.crearIngreso(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "/obtenerTodos")
	public ResponseEntity<List<IngresoDto>> obtenerTodos() {
		List<IngresoDto> response = ingresoService.obtenerTodos();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/ingresosMonth/{month}/{year}")
	public ResponseEntity<List<IngresoDto>> ingresosMonth(@PathVariable int month, @PathVariable int year) {
		List<IngresoDto> response = ingresoService.ingresosMonth(month, year);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/ingresosYear/{year}")
	public ResponseEntity<List<IngresoDto>> ingresosYear(@PathVariable int year) {
		List<IngresoDto> response = ingresoService.ingresosYear(year);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/actualizarIngreso")
	public ResponseEntity<IngresoDto> actualizarIngreso(@RequestBody IngresoDto request) {
		IngresoDto response = ingresoService.actualizarIngreso(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/eliminarIngreso")
	public boolean eliminarIngreso(@RequestBody IngresoDto request) {
		boolean borrado = ingresoService.eliminarIngreso(request);
		return borrado;
	}
}
