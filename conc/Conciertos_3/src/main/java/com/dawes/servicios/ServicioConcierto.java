package com.dawes.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;

public interface ServicioConcierto {
	Optional<List<ConciertoVO>> findBySalaAndFechaBetween(SalaVO sala, LocalDate fecha1, LocalDate fecha2);
	
	Optional<List<ConciertoVO>> findByGrupo(GrupoVO grupo);
	
	List<LocalDate> buscarFechasPorGrupo(GrupoVO grupo);
	
	Optional<List<ConciertoVO>> findByFechaBetween(LocalDate fecha1, LocalDate fecha2);

	Optional<ConciertoVO> findByFechaAndGrupo(LocalDate fecha, GrupoVO grupo);

	Optional<List<ConciertoVO>> buscarConciertoDeGrupoEntreFechas(GrupoVO grupo, LocalDate inicio, LocalDate fin);

	<S extends ConciertoVO> S save(S entity);

	<S extends ConciertoVO> List<S> saveAll(Iterable<S> entities);

	<S extends ConciertoVO> Optional<S> findOne(Example<S> example);

	List<ConciertoVO> findAll(Sort sort);

	void flush();

	Page<ConciertoVO> findAll(Pageable pageable);

	<S extends ConciertoVO> S saveAndFlush(S entity);

	<S extends ConciertoVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<ConciertoVO> findAll();

	List<ConciertoVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<ConciertoVO> entities);

	<S extends ConciertoVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<ConciertoVO> findById(Integer id);

	void deleteAllInBatch(Iterable<ConciertoVO> entities);

	boolean existsById(Integer id);

	<S extends ConciertoVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends ConciertoVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	ConciertoVO getOne(Integer id);

	<S extends ConciertoVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	ConciertoVO getById(Integer id);

	void delete(ConciertoVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	ConciertoVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends ConciertoVO> entities);

	<S extends ConciertoVO> List<S> findAll(Example<S> example);

	<S extends ConciertoVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}