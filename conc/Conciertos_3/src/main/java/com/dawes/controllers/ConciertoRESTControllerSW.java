package com.dawes.controllers;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;
import com.dawes.modelo.VentaVO;
import com.dawes.servicios.ServicioConcierto;
import com.dawes.servicios.ServicioGrupo;
import com.dawes.servicios.ServicioSala;
import com.dawes.servicios.ServicioVenta;

@RestController
@RequestMapping("/api")
public class ConciertoRESTControllerSW {
	@Autowired
	private ServicioConcierto sc;
	@Autowired
	private ServicioGrupo sg;
	@Autowired
	private ServicioVenta sv;
	@Autowired
	private ServicioSala ss;
	
	// Servicios Web
	 
	// servicio web que permite consultar entre fechas los conciertos de una sala	
	@GetMapping("/conciertosala/{nombreSala}/{fecha1}/{fecha2}")
	public ResponseEntity<?> findByFechaBetween(@PathVariable String nombreSala, @PathVariable LocalDate fecha1, @PathVariable LocalDate fecha2) {
		try {
			if (fecha1.isAfter(fecha2)) {
				return new ResponseEntity("La fecha de fin debe ser posterior a la fecha de inicio", HttpStatus.NOT_FOUND);
			} else {
				SalaVO sala = ss.findByNombre(nombreSala).get();
			///	sala.setConciertos(sc.findBySalaAndFechaBetween(sala, fecha1, fecha2).get());
//				return new ResponseEntity<SalaVO>(sala, HttpStatus.OK);
				return new ResponseEntity<List<ConciertoVO>>(sala.getConciertos(), HttpStatus.OK);
			}
		} catch (Exception e) {
			if (ss.findByNombre(nombreSala).isEmpty()) {
				return new ResponseEntity<>("No se ha encontrado sala con este nombre", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>("Datos incorrectos", HttpStatus.NOT_FOUND);
			}
		}		
	}
	
	// servicio web que permite modificar el concierto de un grupo en una fecha concreta
	@PutMapping("/concierto/update/{nombreGrupo}/{fecha}")
	public ResponseEntity<?> modificarConcierto(@PathVariable String nombreGrupo,
												@PathVariable LocalDate fecha,
												@RequestBody ConciertoVO concierto) {
		try {
			ConciertoVO conciertoEditar = sc.findByFechaAndGrupo(fecha, sg.findByNombre(nombreGrupo).get()).get();
			if (conciertoEditar == null) {
	            return new ResponseEntity<>("No se encontró el concierto por grupo: " + nombreGrupo + " y fecha: " + fecha, 
	            							HttpStatus.NOT_FOUND);
	        }
			conciertoEditar.setFecha(concierto.getFecha());
			conciertoEditar.setHora(concierto.getHora());
			conciertoEditar.setGrupo(concierto.getGrupo());
			conciertoEditar.setSala(concierto.getSala());			
			conciertoEditar.setPlazasdisponibles(concierto.getPlazasdisponibles());
			conciertoEditar.setPrecioanticipado(concierto.getPrecioanticipado());
			conciertoEditar.setPreciotaquilla(concierto.getPreciotaquilla());
			sc.save(conciertoEditar);
			return new ResponseEntity<>("Concierto actualizado correctamente", HttpStatus.OK);	
		} catch (Exception e) {
			if (sg.findByNombre(nombreGrupo).isEmpty()) {
				return new ResponseEntity<>("No se ha encontrado grupo con este nombre", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>("Datos incorrectos", HttpStatus.NOT_FOUND);
			}
		}
	}
	
	// servicio web que permite comprar entradas por internet
	@PostMapping("/comprarentradas")
	public ResponseEntity<?> insertarVenta(@RequestBody VentaVO venta) {
//		VentaVO nuevaVenta = null;
//		nuevaVenta=sv.save(venta);
//		return new ResponseEntity<VentaVO>(nuevaVenta, HttpStatus.OK);
		try {	
			VentaVO nuevaVenta = new VentaVO();
			nuevaVenta.setConcierto(venta.getConcierto());
			nuevaVenta.setNumeroentradas(venta.getNumeroentradas());	
			nuevaVenta.setFecha(LocalDate.now());
			nuevaVenta.setHora(Time.valueOf(LocalTime.now()));
			nuevaVenta.setDni(venta.getDni());
			nuevaVenta.setTotal(venta.getNumeroentradas()*venta.getConcierto().getPrecioanticipado());	
			sv.save(nuevaVenta);
	//		return new ResponseEntity<VentaVO>(nuevaVenta, HttpStatus.OK);
			return new ResponseEntity<>("Venta realizada correctamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Datos incorrectos", HttpStatus.NOT_FOUND);
		}
	}
	
	
	// consultas generales
	@GetMapping("/concierto")
	public ResponseEntity<?> listaConciertos() {
		return new ResponseEntity<List<ConciertoVO>>(sc.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/concierto/{id}")
	public ResponseEntity<?> findConciertoById(@PathVariable int id) {
		try {
			return new ResponseEntity<ConciertoVO>(sc.findById(id).get(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("No existe concierto con este id",HttpStatus.NOT_FOUND);
		}		
	}
	
	 @GetMapping("/entradas/{id}")
	 public ResponseEntity<?> entrada(@PathVariable int id) {
		 try {
			return new ResponseEntity<VentaVO>(sv.findById(id).get(),HttpStatus.OK);
		 } catch (Exception e) {
			return new ResponseEntity("No existe venta con este id",HttpStatus.NOT_FOUND);
		}
	}	 
	
	// consultas para varias funcionalidades
	// recuperar datos para el formulario de añadir venta por parte de administrador
	 @GetMapping("/getFechasPorGrupo/{grupoId}")
	    public ResponseEntity<List<Map<String, Object>>> getDatesForGrupo(@PathVariable int grupoId) {	
		 
		 // devuelve las fechas y idconcierto
		 GrupoVO grupo = sg.findById(grupoId).get();
		    List<ConciertoVO> conciertos = sc.findByGrupo(grupo).get();
		    List<Map<String, Object>> conciertoDataList = conciertos.stream()
		            .map(concierto -> {
		                Map<String, Object> conciertoData = new HashMap<>();
		                conciertoData.put("idconcierto", concierto.getIdconcierto());
		                conciertoData.put("fecha", concierto.getFecha().toString());
		                return conciertoData;
		            })
		            .collect(Collectors.toList());
		    return ResponseEntity.ok(conciertoDataList);   		    
	   }
	 
	 // obtener las plazas disponibles para un concierto	 
	 @GetMapping("/getPlazasDisponibles/{fecha}/{idgrupo}")
	    public ResponseEntity<Integer> getPlazasDisponibles(@PathVariable int idgrupo,@PathVariable LocalDate fecha) {	      
	     GrupoVO g = sg.findById(idgrupo).get();  
		 ConciertoVO c = sc.findByFechaAndGrupo(fecha, g).get();
		 int plazas = c.getPlazasdisponibles();	        
        return ResponseEntity.ok(plazas);
    }

}
