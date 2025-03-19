package com.dawes.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.dawes.modelo.GrupoVO;

public interface ServicioGrupo {	

	<S extends GrupoVO> S save(S entity);

	<S extends GrupoVO> List<S> saveAll(Iterable<S> entities);

	<S extends GrupoVO> Optional<S> findOne(Example<S> example);

	List<GrupoVO> findAll(Sort sort);

	void flush();

	Page<GrupoVO> findAll(Pageable pageable);

	<S extends GrupoVO> S saveAndFlush(S entity);

	<S extends GrupoVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<GrupoVO> findAll();

	List<GrupoVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<GrupoVO> entities);

	<S extends GrupoVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<GrupoVO> findById(Integer id);

	void deleteAllInBatch(Iterable<GrupoVO> entities);

	boolean existsById(Integer id);

	<S extends GrupoVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends GrupoVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	GrupoVO getOne(Integer id);

	<S extends GrupoVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	GrupoVO getById(Integer id);

	void delete(GrupoVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	GrupoVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends GrupoVO> entities);

	<S extends GrupoVO> List<S> findAll(Example<S> example);

	<S extends GrupoVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();
	
	Optional<GrupoVO> findById(int id);
	
	Optional<GrupoVO> findByNombre(String nombre);

}