package com.smartfinance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartfinance.entity.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

	List<Gasto> findByUsuarioId(Long usuarioId);

	@Query(value = "SELECT * FROM gasto i WHERE i.usuario_id = :usuarioId AND MONTH(i.fecha) = :month AND YEAR(i.fecha) = :year", nativeQuery = true)
	List<Gasto> gastosMonth(Long usuarioId, int month, int year);

	@Query(value = "SELECT * FROM gasto i WHERE i.usuario_id = :usuarioId AND YEAR(i.fecha) = :year", nativeQuery = true)
	List<Gasto> gastosYear(Long usuarioId, int year);
}
