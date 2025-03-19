package com.dawes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dawes.modelo.Estudiante;
import com.dawes.modelo.Greeting;
import com.dawes.servicios.EstudianteService;

@RestController
public class EstudianteController {
	@Autowired
	private EstudianteService es;
	
	@GetMapping("/api/estudiante")
	public List<Estudiante> obtenerEstudiantes() {
		List<Estudiante> listaEstudiantes = new ArrayList<>();
		Estudiante e1 = new Estudiante(1,"Maria","Stefanova","m@mail.com",10);
		Estudiante e2 = new Estudiante(2,"Plamena","Pencheva","p@mail.com",10);
		listaEstudiantes.add(e1);
		listaEstudiantes.add(e2);
		return listaEstudiantes;
		
	}
	
		
	@PostMapping("api/estudiantes")
	public Estudiante guardarEstudiante(
			@RequestBody Estudiante estudiante) {
		System.out.println(estudiante);
		es.insertarEstudiante(estudiante);
		return estudiante;
	}
	
	// guardar >1 objeto Estudiante
	@PostMapping("api/estudiantes/lista")
	public List<Estudiante> guardarEstudiantesL(
	        @RequestBody List<Estudiante> estudiantes) {	    
	    for (Estudiante estudiante : estudiantes) {
	        System.out.println(estudiante);
	        es.insertarEstudiante(estudiante);
	    }	    
	    return estudiantes;
	}
	
	@GetMapping("api/estudiantes")
	public List<Estudiante> listarEstudiantes() {
		return es.findAll();
	}
		
	@GetMapping("api/estudiantes/{id}")
	public Estudiante obtenerEstudiante(@PathVariable("id") Integer id) {
		return es.obtenerEstudiante(id);
	}
	
	@GetMapping("api/estudiantes/findAll")
	public ResponseEntity<List<Estudiante>> findAll() {
		List<Estudiante> estudiantes = es.findAll();
		return new ResponseEntity<>(estudiantes, HttpStatus.OK);
	}
	
	@DeleteMapping("api/estudiantes/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("mensaje", "El estudiante ha sido borrado.");
			es.eliminarEstudiante(id);
		} catch (Exception e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("api/estudiantes/insertar")
	public ResponseEntity<?> insertar(@RequestBody Estudiante estudiante) {
		Estudiante estudianteGuardado = null;
		estudianteGuardado = es.insertarEstudiante(estudiante);
		return new ResponseEntity<Estudiante>(estudianteGuardado, HttpStatus.OK);
	}
	
	// actualizar estudiante
	@PutMapping("api/estudiante_actualizar/{id}")
    public ResponseEntity<?> actualizarEstudiante(@PathVariable Integer id, 
    											  @RequestBody Estudiante estudiante) {
        Estudiante estudianteExistente = es.obtenerEstudiante(id);
        if (estudianteExistente == null) {
            return new ResponseEntity<>("No se encontró el estudiante con el ID: " + id, 
            							HttpStatus.NOT_FOUND);
        }
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setApellidos(estudiante.getApellidos());
        estudianteExistente.setEmail(estudiante.getEmail());
        estudianteExistente.setNota(estudiante.getNota());
       
        es.insertarEstudiante(estudianteExistente);

        return new ResponseEntity<>("Estudiante actualizado correctamente", HttpStatus.OK);
    }
	
	
	// ------------------ (Servicios Web)
	private static final String template="Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,  name));
	}

}

