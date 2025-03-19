package com.dawes.serviciosImpl;

import java.time.LocalDate;
import java.util.List;
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

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;
import com.dawes.repository.ConciertoRepository;
import com.dawes.servicios.ServicioConcierto;

@Service
public class ServicioConciertoImpl implements ServicioConcierto {

	@Autowired
	private ConciertoRepository cr;

	@Override
	public Optional<ConciertoVO> findByFechaAndGrupo(LocalDate fecha, GrupoVO grupo) {
		return cr.findByFechaAndGrupo(fecha, grupo);
	}

	@Override
	public Optional<List<ConciertoVO>> buscarConciertoDeGrupoEntreFechas(GrupoVO grupo, LocalDate inicio,
			LocalDate fin) {
		return cr.buscarConciertoDeGrupoEntreFechas(grupo, inicio, fin);
	}

	@Override
	public <S extends ConciertoVO> S save(S entity) {
		// try/catch para el test- insertar clave duplicada - en casos de ejecución de la aplicación 
		// la excepción se maneja en ConciertoController
		
//		try {
//			return cr.save(entity);
//		} catch (DataIntegrityViolationException e) {
//			System.err.println("Clave unica duplicada "+e.getLocalizedMessage());
//			return null;
//		}
		
		return cr.save(entity);
	}

	@Override
	public <S extends ConciertoVO> List<S> saveAll(Iterable<S> entities) {
		return cr.saveAll(entities);
	}

	@Override
	public <S extends ConciertoVO> Optional<S> findOne(Example<S> example) {
		return cr.findOne(example);
	}

	@Override
	public List<ConciertoVO> findAll(Sort sort) {
		return cr.findAll(sort);
	}

	@Override
	public void flush() {
		cr.flush();
	}

	@Override
	public Page<ConciertoVO> findAll(Pageable pageable) {
		return cr.findAll(pageable);
	}

	@Override
	public <S extends ConciertoVO> S saveAndFlush(S entity) {
		return cr.saveAndFlush(entity);
	}

	@Override
	public <S extends ConciertoVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return cr.saveAllAndFlush(entities);
	}

	@Override
	public List<ConciertoVO> findAll() {
		return cr.findAll();
	}

	@Override
	public List<ConciertoVO> findAllById(Iterable<Integer> ids) {
		return cr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<ConciertoVO> entities) {
		cr.deleteInBatch(entities);
	}

	@Override
	public <S extends ConciertoVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return cr.findAll(example, pageable);
	}

	@Override
	public Optional<ConciertoVO> findById(Integer id) {
		return cr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<ConciertoVO> entities) {
		cr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return cr.existsById(id);
	}

	@Override
	public <S extends ConciertoVO> long count(Example<S> example) {
		return cr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		cr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends ConciertoVO> boolean exists(Example<S> example) {
		return cr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		cr.deleteAllInBatch();
	}

	@Override
	public ConciertoVO getOne(Integer id) {
		return cr.getOne(id);
	}

	@Override
	public <S extends ConciertoVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return cr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return cr.count();
	}

	@Override
	public void deleteById(Integer id) {
		cr.deleteById(id);
	}

	@Override
	public ConciertoVO getById(Integer id) {
		return cr.getById(id);
	}

	@Override
	public void delete(ConciertoVO entity) {
		cr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		cr.deleteAllById(ids);
	}

	@Override
	public ConciertoVO getReferenceById(Integer id) {
		return cr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends ConciertoVO> entities) {
		cr.deleteAll(entities);
	}

	@Override
	public <S extends ConciertoVO> List<S> findAll(Example<S> example) {
		return cr.findAll(example);
	}

	@Override
	public <S extends ConciertoVO> List<S> findAll(Example<S> example, Sort sort) {
		return cr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		cr.deleteAll();
	}

	@Override
	public Optional<List<ConciertoVO>> findByFechaBetween(LocalDate fecha1, LocalDate fecha2) {		
		return cr.findByFechaBetween(fecha1, fecha2);
	}

	@Override
	public List<LocalDate> buscarFechasPorGrupo(GrupoVO grupo) {		
		return cr.buscarFechasPorGrupo(grupo);
	}

	@Override
	public Optional<List<ConciertoVO>> findByGrupo(GrupoVO grupo) {		
		return cr.findByGrupo(grupo);
	}

	@Override
	public Optional<List<ConciertoVO>> findBySalaAndFechaBetween(SalaVO sala, LocalDate fecha1, LocalDate fecha2) {		
		return cr.findBySalaAndFechaBetween(sala, fecha1, fecha2);
	}
	
	
}
