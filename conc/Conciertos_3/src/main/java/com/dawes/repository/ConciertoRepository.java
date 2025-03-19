package com.dawes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;

public interface ConciertoRepository extends JpaRepository<ConciertoVO, Integer> {	
	// Búsqueda por la clave única
	Optional<ConciertoVO> findByFechaAndGrupo(LocalDate fecha, GrupoVO grupo);	
	// Concierto de un grupo entre fechas
	@Query("select c from ConciertoVO c where c.grupo=?1 and c.fecha between ?2 and ?3")
	Optional<List<ConciertoVO>> buscarConciertoDeGrupoEntreFechas(GrupoVO grupo, LocalDate inicio,LocalDate fin);
	// Buscar concierto entre fechas
	Optional<List<ConciertoVO>> findByFechaBetween(LocalDate fecha1, LocalDate fecha2);		
	@Query("select c.fecha from ConciertoVO c where c.grupo=?1")
	List<LocalDate> buscarFechasPorGrupo(GrupoVO grupo);		
	Optional<List<ConciertoVO>> findByGrupo(GrupoVO grupo);
	Optional<List<ConciertoVO>> findBySalaAndFechaBetween(SalaVO sala, LocalDate fecha1, LocalDate fecha2);
}
