package com.dawes.serviciosImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.VentaVO;
import com.dawes.repository.ConciertoRepository;
import com.dawes.repository.VentaRepository;
import com.dawes.servicios.ServicioVenta;

@Service
public class ServicioVentaImpl implements ServicioVenta {
	
	@Autowired
	private VentaRepository vr;	
	@Autowired
	private ConciertoRepository cr;	

	@Override
	public Optional<List<VentaVO>> findByConcierto(ConciertoVO concierto) {
		return vr.findByConcierto(concierto);
	}

	@Override
	public Optional<List<VentaVO>> buscarConciertosPorGrupoYFecha(GrupoVO grupo, LocalDate inicio, LocalDate fin) {
		if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }
		return vr.buscarConciertosPorGrupoYFecha(grupo, inicio, fin);
	}

	@Override
	public Optional<List<VentaVO>> findByDni(String dni) {
		return vr.findByDni(dni);
	}

	@Override
	public <S extends VentaVO> S save(S entity) {
		ConciertoVO concierto = entity.getConcierto();
		int plazas = concierto.getPlazasdisponibles();
		int numentradas = entity.getNumeroentradas();
		if (plazas > 0) {
			if (plazas > numentradas) {
				concierto.setPlazasdisponibles(plazas-numentradas);
			} else {
				concierto.setPlazasdisponibles(0);
				System.out.println("Hay solo "+plazas+" disponibles.");
			}
		} else {
			System.out.println("No hay plazas disponibles.");
		}
		
		cr.save(concierto);
		return vr.save(entity);
	}

	@Override
	public <S extends VentaVO> List<S> saveAll(Iterable<S> entities) {
		return vr.saveAll(entities);
	}

	@Override
	public <S extends VentaVO> Optional<S> findOne(Example<S> example) {
		return vr.findOne(example);
	}

	@Override
	public List<VentaVO> findAll(Sort sort) {
		return vr.findAll(sort);
	}

	@Override
	public void flush() {
		vr.flush();
	}

	@Override
	public Page<VentaVO> findAll(Pageable pageable) {
		return vr.findAll(pageable);
	}

	@Override
	public <S extends VentaVO> S saveAndFlush(S entity) {
		return vr.saveAndFlush(entity);
	}

	@Override
	public <S extends VentaVO> List<S> saveAllAndFlush(Iterable<S> entities) {
		return vr.saveAllAndFlush(entities);
	}

	@Override
	public List<VentaVO> findAll() {
		return vr.findAll();
	}

	@Override
	public List<VentaVO> findAllById(Iterable<Integer> ids) {
		return vr.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<VentaVO> entities) {
		vr.deleteInBatch(entities);
	}

	@Override
	public <S extends VentaVO> Page<S> findAll(Example<S> example, Pageable pageable) {
		return vr.findAll(example, pageable);
	}

	@Override
	public Optional<VentaVO> findById(Integer id) {
		return vr.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<VentaVO> entities) {
		vr.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return vr.existsById(id);
	}

	@Override
	public <S extends VentaVO> long count(Example<S> example) {
		return vr.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		vr.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends VentaVO> boolean exists(Example<S> example) {
		return vr.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		vr.deleteAllInBatch();
	}

	@Override
	public VentaVO getOne(Integer id) {
		return vr.getOne(id);
	}

	@Override
	public <S extends VentaVO, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return vr.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return vr.count();
	}

	@Override
	public void deleteById(Integer id) {
		vr.deleteById(id);
	}

	@Override
	public VentaVO getById(Integer id) {
		return vr.getById(id);
	}

	@Override
	public void delete(VentaVO entity) {
		vr.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		vr.deleteAllById(ids);
	}

	@Override
	public VentaVO getReferenceById(Integer id) {
		return vr.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends VentaVO> entities) {
		vr.deleteAll(entities);
	}

	@Override
	public <S extends VentaVO> List<S> findAll(Example<S> example) {
		return vr.findAll(example);
	}

	@Override
	public <S extends VentaVO> List<S> findAll(Example<S> example, Sort sort) {
		return vr.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		vr.deleteAll();
	}
	

}
