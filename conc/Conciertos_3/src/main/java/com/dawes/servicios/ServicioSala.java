package com.dawes.servicios;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.dawes.modelo.SalaVO;

public interface ServicioSala {
	Optional<SalaVO> findByNombre(String nombre);

	<S extends SalaVO> S save(S entity);

	<S extends SalaVO> List<S> saveAll(Iterable<S> entities);

	<S extends SalaVO> Optional<S> findOne(Example<S> example);

	List<SalaVO> findAll(Sort sort);

	void flush();

	Page<SalaVO> findAll(Pageable pageable);

	<S extends SalaVO> S saveAndFlush(S entity);

	<S extends SalaVO> List<S> saveAllAndFlush(Iterable<S> entities);

	List<SalaVO> findAll();

	List<SalaVO> findAllById(Iterable<Integer> ids);

	void deleteInBatch(Iterable<SalaVO> entities);

	<S extends SalaVO> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<SalaVO> findById(Integer id);

	void deleteAllInBatch(Iterable<SalaVO> entities);

	boolean existsById(Integer id);

	<S extends SalaVO> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	<S extends SalaVO> boolean exists(Example<S> example);

	void deleteAllInBatch();

	SalaVO getOne(Integer id);

	<S extends SalaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	long count();

	void deleteById(Integer id);

	SalaVO getById(Integer id);

	void delete(SalaVO entity);

	void deleteAllById(Iterable<? extends Integer> ids);

	SalaVO getReferenceById(Integer id);

	void deleteAll(Iterable<? extends SalaVO> entities);

	<S extends SalaVO> List<S> findAll(Example<S> example);

	<S extends SalaVO> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll();

}