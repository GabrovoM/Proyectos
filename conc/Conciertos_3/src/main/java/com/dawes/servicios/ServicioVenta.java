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
import com.dawes.modelo.VentaVO;

public interface ServicioVenta {

	Optional<List<VentaVO>> findByConcierto(ConciertoVO concierto);

	Optional<List<VentaVO>> buscarConciertosPorGrupoYFecha(GrupoVO grupo, LocalDate inicio, LocalDate fin);

	Optional<List<VentaVO>> findByDni(String dni);

	<S extends VentaVO> S save(S entity);

	<S extends VentaVO> List<S> saveAll(Iterable<S> entities);

	<S extends VentaVO> Optional<S> findOne(Example<S> example);

	List<VentaVO> findAll(Sort sort);

	void flush();

	Page<VentaVO> findAll(Pageable pageable);

	<S extends VentaVO> S saveAndFlush(S entity);

	<S extends VentaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<VentaVO> findAll();

	List<VentaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<VentaVO> entities);

	<S extends VentaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<VentaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<VentaVO> entities);

	boolean existsById(Integer id);

	<S extends VentaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends VentaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	VentaVO getOne(Integer id);

	<S extends VentaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	VentaVO getById(Integer id);

	void delete(VentaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	VentaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends VentaVO> entities);

	<S extends VentaVO> List<S> findAll(Example<S> example);

	<S extends VentaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();
	
//	VentaVO insertarVenta(VentaVO venta);

}