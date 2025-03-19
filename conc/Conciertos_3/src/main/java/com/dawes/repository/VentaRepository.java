package com.dawes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.VentaVO;

public interface VentaRepository extends JpaRepository<VentaVO, Integer> {
	// Listado de ventas por concierto
	Optional<List<VentaVO>> findByConcierto(ConciertoVO concierto);		
	// Listado de ventas por grupo y entre fechas
	@Query("select v from VentaVO v where v.concierto.grupo=?1 and v.fecha between ?2 and ?3")
	Optional<List<VentaVO>> buscarConciertosPorGrupoYFecha(GrupoVO grupo, LocalDate inicio,LocalDate fin);	
	// Consulta de ventas por DNI del comprador
	Optional<List<VentaVO>> findByDni(String dni);
}
