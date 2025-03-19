package com.dawes.serviciosImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.modelo.Estudiante;
import com.dawes.repository.EstudianteRepository;
import com.dawes.servicios.EstudianteService;

@Service
public class EstudianteServicioImpl implements EstudianteService {

	@Autowired
	private EstudianteRepository er;
	
	@Override
	public Estudiante insertarEstudiante(Estudiante estudiante) {		
		return er.save(estudiante);
	}

	@Override
	public List<Estudiante> findAll() {		
		return er.findAll();
	}

	@Override
	public void eliminarEstudiante(Integer id) {
		er.deleteById(id);
		
	}

	@Override
	public Estudiante obtenerEstudiante(Integer id) {
//		return er.findById(id).orElse(null);
		 return er.getReferenceById(id);
	}

	@Override
	public Estudiante modificarEstudiante(Estudiante estudiante) {
		Optional<Estudiante> e = er.findById(estudiante.getId());
		if (e.isPresent()) {
			Estudiante est = e.get();
			est.setNombre(estudiante.getNombre());
			est.setApellidos(estudiante.getApellidos());
			est.setEmail(estudiante.getEmail());
			est.setNota(estudiante.getNota());
			return er.save(est);
		} else {
			throw new RuntimeException("Estudiante not found with id: " + estudiante.getId());
		}
		
	}


}
