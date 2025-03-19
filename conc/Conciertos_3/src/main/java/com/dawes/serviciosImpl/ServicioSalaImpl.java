package com.dawes.serviciosImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dawes.modelo.SalaVO;
import com.dawes.repository.SalaRepository;
import com.dawes.servicios.ServicioSala;

@Service
public class ServicioSalaImpl implements ServicioSala {
	@Autowired
	private SalaRepository sr;

	@Override
	public <S extends SalaVO> S save(S entity) {
		// para el test, en otros casos la excepción se maneja en el controlador
//		try {
//			return sr.save(entity);
//		} catch (DataIntegrityViolationException e) {
//			System.err.println("Clave unica duplicada "+e.getLocalizedMessage());
//			return null;
//		}
		
		return sr.save(entity);
		
	}

	@Override
	public <S extends SalaVO> List<S> saveAll(Iterable<S> entities) {
		return sr.saveAll(entities);
	}

	@Override
	public <S extends SalaVO> Optional<S> findOne(Example<S> example) {
		return sr.findOne(example);
	}

	@Override
	public List<SalaVO> findAll(Sort sort) {
		return sr.findAll(sort);
	}

	@Override
	public void flush() {
		sr.flush();
	}

	@Override
	public Page<SalaVO> findAll(Pageable pageable) {
		return sr.findAll(pageable);
	}

	@Override
	public <S extends SalaVO> S saveAndFlush(S entity) {
		return sr.saveAndFlush(entity);
	}

	@Override
	public <S extends SalaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return sr.saveAllAndFlush(entities);
	}

	@Override
	public List<SalaVO> findAll() {
		return sr.findAll();
	}

	@Override
	public List<SalaVO> findAllById(Iterable<Integer> ids) {
		return sr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<SalaVO> entities) {
		sr.deleteInBatch(entities);
	}

	@Override
	public <S extends SalaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return sr.findAll(example, pageable);
	}

	@Override
	public Optional<SalaVO> findById(Integer id) {
		return sr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<SalaVO> entities) {
		sr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return sr.existsById(id);
	}

	@Override
	public <S extends SalaVO> long count(Example<S> example) {
		return sr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		sr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends SalaVO> boolean exists(Example<S> example) {
		return sr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		sr.deleteAllInBatch();
	}

	@Override
	public SalaVO getOne(Integer id) {
		return sr.getOne(id);
	}

	@Override
	public <S extends SalaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return sr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return sr.count();
	}

	@Override
	public void deleteById(Integer id) {
		sr.deleteById(id);
	}

	@Override
	public SalaVO getById(Integer id) {
		return sr.getById(id);
	}

	@Override
	public void delete(SalaVO entity) {
		sr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		sr.deleteAllById(ids);
	}

	@Override
	public SalaVO getReferenceById(Integer id) {
		return sr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends SalaVO> entities) {
		sr.deleteAll(entities);
	}

	@Override
	public <S extends SalaVO> List<S> findAll(Example<S> example) {
		return sr.findAll(example);
	}

	@Override
	public <S extends SalaVO> List<S> findAll(Example<S> example, Sort sort) {
		return sr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		sr.deleteAll();
	}

	@Override
	public Optional<SalaVO> findByNombre(String nombre) {		
		return sr.findByNombre(nombre);
	}
	
	
}
