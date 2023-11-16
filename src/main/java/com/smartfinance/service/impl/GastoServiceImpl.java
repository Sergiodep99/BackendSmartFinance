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

import com.smartfinance.dto.GastoDto;
import com.smartfinance.entity.Gasto;
import com.smartfinance.entity.Usuario;
import com.smartfinance.exception.CustomAuthorizationException;
import com.smartfinance.exception.CustomResourceNotFoundException;
import com.smartfinance.repository.GastoRepository;
import com.smartfinance.repository.UsuarioRepository;
import com.smartfinance.security.services.UserDetailsImpl;
import com.smartfinance.service.GastoService;

@Service
public class GastoServiceImpl implements GastoService {

	@Autowired
	GastoRepository gastoRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public GastoDto crearGasto(GastoDto request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		if (request.getUsuarioId() == tokenUsuarioId) {
			// Peticion o entrada
			Gasto gasto = modelMapper.map(request, Gasto.class);

			// Comprobamos si la cantidad recibida es negativo, en caso de no serlo lo
			// cambiamos a negativo
			if (gasto.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
				gasto.setCantidad(gasto.getCantidad().negate());
			}

			// Buscamos el usuario por id para recuperar sus datos y lo añadimos a la clase
			// de gasto
			Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
					.orElseThrow(() -> new CustomResourceNotFoundException("Usuario no encontrado"));
			gasto.setUsuario(usuario);

			// Respuesta
			GastoDto response = modelMapper.map(gastoRepository.save(gasto), GastoDto.class);
			return response;
		} else {
			throw new CustomAuthorizationException("El usuario no tiene permiso para crear gastos en otro usuario.");
		}
	}

	@Override
	public List<GastoDto> obtenerTodos() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		List<Gasto> gastos = gastoRepository.findByUsuarioId(tokenUsuarioId);

		List<GastoDto> gastoDto = new ArrayList<>();

		for (Gasto gasto : gastos) {
			gastoDto.add(modelMapper.map(gasto, GastoDto.class));
		}

		// Ordenar los gastos por fecha
		List<GastoDto> gastosOrdenados = gastoDto.stream().sorted(Comparator.comparing(GastoDto::getFecha))
				.collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(gastosOrdenados);

		return gastosOrdenados;
	}

	@Override
	public List<GastoDto> gastosMonth(int month, int year) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		List<Gasto> gastos = gastoRepository.gastosMonth(tokenUsuarioId, month, year);

		List<GastoDto> gastoDto = new ArrayList<>();

		for (Gasto gasto : gastos) {
			gastoDto.add(modelMapper.map(gasto, GastoDto.class));
		}

		// Ordenar los gastos por fecha
		List<GastoDto> gastosOrdenados = gastoDto.stream().sorted(Comparator.comparing(GastoDto::getFecha))
				.collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(gastosOrdenados);

		return gastosOrdenados;
	}

	@Override
	public List<GastoDto> gastosYear(int year) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		List<Gasto> gastos = gastoRepository.gastosYear(tokenUsuarioId, year);

		List<GastoDto> gastoDto = new ArrayList<>();

		for (Gasto gasto : gastos) {
			gastoDto.add(modelMapper.map(gasto, GastoDto.class));
		}

		// Ordenar los gastos por fecha
		List<GastoDto> gastosOrdenados = gastoDto.stream().sorted(Comparator.comparing(GastoDto::getFecha))
				.collect(Collectors.toList());

		// Invertir la lista
		Collections.reverse(gastosOrdenados);

		return gastosOrdenados;
	}

	@Override
	public GastoDto actualizarGasto(GastoDto request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		// Peticion o entrada
		Gasto gasto = modelMapper.map(request, Gasto.class);

		// Comprobamos si el gasto a actualizar existe
		Optional<Gasto> optionalGasto = gastoRepository.findById(request.getId());
		if (optionalGasto.isPresent()) {
			Gasto gastoActualizar = optionalGasto.get();

			// Comprobamos que el gasto pertenece al usuario del token (usuario con sesión
			// iniciada)
			if (gastoActualizar.getUsuario().getId() == tokenUsuarioId) {
				// Comprobamos si la cantidad recibida es negativo, en caso de no serlo lo
				// cambiamos a negativo
				if (gasto.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
					gasto.setCantidad(gasto.getCantidad().negate());
				}

				// Añadimos el usuario del gasto anterior al nuevo gasto actualizado
				gasto.setUsuario(gastoActualizar.getUsuario());

				// Respuesta
				GastoDto response = modelMapper.map(gastoRepository.save(gasto), GastoDto.class);
				return response;
			} else {
				throw new CustomAuthorizationException("El usuario no tiene permiso para actualizar este gasto.");
			}
		} else {
			throw new CustomResourceNotFoundException("Gasto no encontrado");
		}
	}

	@Override
	public boolean eliminarGasto(GastoDto request) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long tokenUsuarioId = ((UserDetailsImpl) userDetails).getId();

		// Comprobamos si el gasto a borrar existe
		Optional<Gasto> optionalGasto = gastoRepository.findById(request.getId());
		if (optionalGasto.isPresent()) {
			Gasto gasto = optionalGasto.get();

			// Comprobamos que el gasto pertenece al usuario del token (usuario con sesión
			// iniciada)
			if (gasto.getUsuario().getId() == tokenUsuarioId) {
				gastoRepository.delete(gasto);
				return true;
			} else {
				throw new CustomAuthorizationException("El usuario no tiene permiso para eliminar este gasto.");
			}

		} else {
			throw new CustomResourceNotFoundException("Gasto no encontrado");
		}
	}
}
