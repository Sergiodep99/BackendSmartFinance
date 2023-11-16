package com.smartfinance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartfinance.dto.IngresoDto;
import com.smartfinance.entity.Ingreso;
import com.smartfinance.entity.Usuario;
import com.smartfinance.exception.CustomAuthorizationException;
import com.smartfinance.exception.CustomResourceNotFoundException;
import com.smartfinance.repository.IngresoRepository;
import com.smartfinance.repository.UsuarioRepository;
import com.smartfinance.security.services.UserDetailsImpl;
import com.smartfinance.service.IngresoService;

@Service
public class IngresoServiceImpl implements IngresoService {

	@Autowired
	IngresoRepository ingresoRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public IngresoDto crearIngreso(IngresoDto request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		if (request.getUsuarioId() == tokenUsuarioId) {
			// Peticion o entrada
			Ingreso ingreso = modelMapper.map(request, Ingreso.class);

			// Comprobamos si la cantidad recibida es positivo, en caso de no serlo lo
			// cambiamos a positivo
			if (ingreso.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
				ingreso.setCantidad(ingreso.getCantidad().abs());
			}

			// Buscamos el usuario por id para recuperar sus datos y lo añadimos a la clase
			// de ingreso
			Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
					.orElseThrow(() -> new CustomResourceNotFoundException("Usuario no encontrado"));
			ingreso.setUsuario(usuario);

			// Respuesta
			IngresoDto response = modelMapper.map(ingresoRepository.save(ingreso), IngresoDto.class);
			return response;
		} else {
			throw new CustomAuthorizationException("El usuario no tiene permiso para crear ingresos en otro usuario.");
		}
	}

	@Override
	public List<IngresoDto> obtenerTodos() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		List<Ingreso> ingresos = ingresoRepository.findByUsuarioId(tokenUsuarioId);

		List<IngresoDto> ingresoDto = new ArrayList<>();

		for (Ingreso ingreso : ingresos) {
			ingresoDto.add(modelMapper.map(ingreso, IngresoDto.class));
		}

		// Ordenar los ingresos por fecha
		List<IngresoDto> ingresosOrdenados = ingresoDto.stream().sorted(Comparator.comparing(IngresoDto::getFecha))
				.collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(ingresosOrdenados);

		return ingresosOrdenados;
	}

	@Override
	public List<IngresoDto> ingresosMonth(int month, int year) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		List<Ingreso> ingresos = ingresoRepository.ingresosMonth(tokenUsuarioId, month, year);

		List<IngresoDto> ingresoDto = new ArrayList<>();

		for (Ingreso ingreso : ingresos) {
			ingresoDto.add(modelMapper.map(ingreso, IngresoDto.class));
		}

		// Ordenar los ingresos por fecha
		List<IngresoDto> ingresosOrdenados = ingresoDto.stream().sorted(Comparator.comparing(IngresoDto::getFecha))
				.collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(ingresosOrdenados);

		return ingresosOrdenados;
	}

	@Override
	public List<IngresoDto> ingresosYear(int year) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		List<Ingreso> ingresos = ingresoRepository.ingresosYear(tokenUsuarioId, year);

		List<IngresoDto> ingresoDto = new ArrayList<>();

		for (Ingreso ingreso : ingresos) {
			ingresoDto.add(modelMapper.map(ingreso, IngresoDto.class));
		}

		// Ordenar los ingresos por fecha
		List<IngresoDto> ingresosOrdenados = ingresoDto.stream().sorted(Comparator.comparing(IngresoDto::getFecha))
				.collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(ingresosOrdenados);

		return ingresosOrdenados;
	}

	@Override
	public IngresoDto actualizarIngreso(IngresoDto request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		// Peticion o entrada
		Ingreso ingreso = modelMapper.map(request, Ingreso.class);

		// Comprobamos si el ingreso a actualizar existe
		Optional<Ingreso> optionalIngreso = ingresoRepository.findById(request.getId());
		if (optionalIngreso.isPresent()) {
			Ingreso ingresoActualizar = optionalIngreso.get();

			// Comprobamos que el ingreso pertenece al usuario del token (usuario con sesión
			// iniciada)
			if (ingresoActualizar.getUsuario().getId() == tokenUsuarioId) {
				// Comprobamos si la cantidad recibida es positivo, en caso de no serlo lo
				// cambiamos a positivo
				if (ingreso.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
					ingreso.setCantidad(ingreso.getCantidad().abs());
				}

				// Añadimos el usuario del ingreso anterior al nuevo ingreso actualizado
				ingreso.setUsuario(ingresoActualizar.getUsuario());

				// Respuesta
				IngresoDto response = modelMapper.map(ingresoRepository.save(ingreso), IngresoDto.class);
				return response;
			} else {
				throw new CustomAuthorizationException("El usuario no tiene permiso para actualizar este ingreso.");
			}
		} else {
			throw new CustomResourceNotFoundException("Ingreso no encontrado");
		}
	}

	@Override
	public boolean eliminarIngreso(IngresoDto request) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		// Comprobamos si el ingreso a borrar existe
		Optional<Ingreso> optionalIngreso = ingresoRepository.findById(request.getId());
		if (optionalIngreso.isPresent()) {
			Ingreso ingreso = optionalIngreso.get();

			// Comprobamos que el ingreso pertenece al usuario del token (usuario con sesión
			// iniciada)
			if (ingreso.getUsuario().getId() == tokenUsuarioId) {
				ingresoRepository.delete(ingreso);
				return true;
			} else {
				throw new CustomAuthorizationException("El usuario no tiene permiso para eliminar este ingreso.");
			}

		} else {
			throw new CustomResourceNotFoundException("Ingreso no encontrado");
		}
	}

}
