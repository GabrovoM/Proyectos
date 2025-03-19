package com.dawes.servicios;

import java.util.List;

import com.dawes.modelo.Estudiante;


public interface EstudianteService {
	Estudiante insertarEstudiante(Estudiante estudiante);
	List<Estudiante> findAll();
	void eliminarEstudiante(Integer id);
	Estudiante obtenerEstudiante(Integer id);
	Estudiante modificarEstudiante(Estudiante estudiante);
}
