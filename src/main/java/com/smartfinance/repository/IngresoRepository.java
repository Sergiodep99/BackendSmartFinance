package com.smartfinance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartfinance.entity.Ingreso;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

	List<Ingreso> findByUsuarioId(Long usuarioId);

	@Query(value = "SELECT * FROM ingreso i WHERE i.usuario_id = :usuarioId AND MONTH(i.fecha) = :month AND YEAR(i.fecha) = :year", nativeQuery = true)
	List<Ingreso> ingresosMonth(Long usuarioId, int month, int year);

	@Query(value = "SELECT * FROM ingreso i WHERE i.usuario_id = :usuarioId AND YEAR(i.fecha) = :year", nativeQuery = true)
	List<Ingreso> ingresosYear(Long usuarioId, int year);
}
