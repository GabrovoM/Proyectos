package com.dawes.serviciosImpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dawes.modelo.GrupoVO;
import com.dawes.repository.GrupoRepository;
import com.dawes.servicios.ServicioGrupo;

@Service
public class ServicioGrupoImpl implements ServicioGrupo {
	
	@Autowired
	private GrupoRepository gr;

	@Override
	public <S extends GrupoVO> S save(S entity) {
		// solo para el test - en otros casos de ejecución de la aplicación
		// la excepción se maneja en en GrupoController
//		try {
//			return gr.save(entity);
//		} catch (DataIntegrityViolationException e) {
//			System.err.println("Clave unica duplicada "+e.getLocalizedMessage());
//			return null;
//		}
		
		return gr.save(entity);
	}

	@Override
	public <S extends GrupoVO> List<S> saveAll(Iterable<S> entities) {
		return gr.saveAll(entities);
	}

	@Override
	public <S extends GrupoVO> Optional<S> findOne(Example<S> example) {
		return gr.findOne(example);
	}

	@Override
	public List<GrupoVO> findAll(Sort sort) {
		return gr.findAll(sort);
	}

	@Override
	public void flush() {
		gr.flush();
	}

	@Override
	public Page<GrupoVO> findAll(Pageable pageable) {
		return gr.findAll(pageable);
	}

	@Override
	public <S extends GrupoVO> S saveAndFlush(S entity) {
		return gr.saveAndFlush(entity);
	}

	@Override
	public <S extends GrupoVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return gr.saveAllAndFlush(entities);
	}

	@Override
	public List<GrupoVO> findAll() {
		return gr.findAll();
	}

	@Override
	public List<GrupoVO> findAllById(Iterable<Integer> ids) {
		return gr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<GrupoVO> entities) {
		gr.deleteInBatch(entities);
	}

	@Override
	public <S extends GrupoVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return gr.findAll(example, pageable);
	}

	@Override
	public Optional<GrupoVO> findById(Integer id) {
		return gr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<GrupoVO> entities) {
		gr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return gr.existsById(id);
	}

	@Override
	public <S extends GrupoVO> long count(Example<S> example) {
		return gr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		gr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends GrupoVO> boolean exists(Example<S> example) {
		return gr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		gr.deleteAllInBatch();
	}

	@Override
	public GrupoVO getOne(Integer id) {
		return gr.getOne(id);
	}

	@Override
	public <S extends GrupoVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return gr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return gr.count();
	}

	@Override
	public void deleteById(Integer id) {
		gr.deleteById(id);
	}

	@Override
	public GrupoVO getById(Integer id) {
		return gr.getById(id);
	}

	@Override
	public void delete(GrupoVO entity) {
		try {
			gr.delete(entity);
		} catch (NoSuchElementException e) {
			System.out.println("El grupo no existe en la BD "+e.getLocalizedMessage());
		}
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		gr.deleteAllById(ids);
	}

	@Override
	public GrupoVO getReferenceById(Integer id) {
		return gr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends GrupoVO> entities) {
		gr.deleteAll(entities);
	}

	@Override
	public <S extends GrupoVO> List<S> findAll(Example<S> example) {
		return gr.findAll(example);
	}

	@Override
	public <S extends GrupoVO> List<S> findAll(Example<S> example, Sort sort) {
		return gr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		gr.deleteAll();
	}

	@Override
	public Optional<GrupoVO> findById(int id) {		
		return gr.findById(id);
	}

	@Override
	public Optional<GrupoVO> findByNombre(String nombre) {
		try {
			return gr.findByNombre(nombre);
		} catch (NoSuchElementException e) {
			System.out.println("No existe grupo con este nombre en la BD "+e.getLocalizedMessage());
			return Optional.empty();
		}
		
	}
	
	

}
