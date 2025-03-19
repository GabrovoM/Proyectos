package com.dawes.controllers;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;
import com.dawes.modelo.UsuarioRolVO;
import com.dawes.serviciosImpl.ServicioConciertoImpl;
import com.dawes.serviciosImpl.ServicioGrupoImpl;
import com.dawes.serviciosImpl.ServicioRolImpl;
import com.dawes.serviciosImpl.ServicioSalaImpl;

@Controller
@RequestMapping("/conciertos")
public class ConciertoController {
	
	@Autowired
	private ServicioConciertoImpl sc;
	@Autowired
	private ServicioGrupoImpl sg;
	@Autowired
	private ServicioSalaImpl ss;	
	@Autowired
	private ServicioRolImpl rs;		
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@GetMapping(value={"","/"})
	public String listaConciertos(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		List<ConciertoVO> listaConciertos = sc.findAll();
		List<GrupoVO> listaGrupos = sg.findAll();
		List<SalaVO> listaSalas = ss.findAll();		
		String username = userDetails.getUsername();
		UsuarioRolVO urol = rs.buscarRolDeUsuario(username);		
		String role = urol.getRol().getRoleName();
		model.addAttribute("username", username);
		model.addAttribute("rolename", role);	
		model.addAttribute("listaConciertos", listaConciertos);	
		model.addAttribute("listaGrupos", listaGrupos);
		model.addAttribute("listaSalas", listaSalas);
		return "/admin/conciertos/conciertos";
	}
	
	@GetMapping("/{id}")
	public String detallesConcierto(@PathVariable("id") int id, Model model) {
		ConciertoVO concierto = sc.findById(id).get();		
		LocalDate fecha = concierto.getFecha();		
		String fFecha = fecha.format(dtf);		   
		model.addAttribute("concierto", concierto);
		model.addAttribute("formattedFecha", fFecha);
		return "/admin/conciertos/concierto";
	}
	
	@GetMapping("/editConcierto/{id}")
	public String editarConcierto(Model model, @PathVariable("id") int id) {
		ConciertoVO concierto = sc.findById(id).get();	
		List<GrupoVO> grupos = sg.findAll();
		List<SalaVO> listaSalas = ss.findAll();
		LocalDate fecha = concierto.getFecha();	
		String fFecha = fecha.format(dtf);
		model.addAttribute("formattedFecha", fFecha);
		model.addAttribute("editedConcierto", concierto);
		model.addAttribute("grupos", grupos);
		model.addAttribute("salas", listaSalas);
		return "/admin/conciertos/formulario_editar_concierto";
	}
	
	@PostMapping("/editConcierto/submit")
	public String editConciertoSubmit(@ModelAttribute("editedConcierto") ConciertoVO concierto,
			 @RequestParam("grupos") int grupo, @RequestParam("salas") int sala, 
			 @RequestParam("hora") String horaStr, 
			 BindingResult bindingResult) {	
		ConciertoVO conciertoEdit = sc.findById(concierto.getIdconcierto()).get();			
		if (concierto.getFecha() != null) {			
			conciertoEdit.setFecha(concierto.getFecha());
		}
		String timeString = horaStr;
		Time time = Time.valueOf(timeString);
		conciertoEdit.setHora(time);
		conciertoEdit.setPlazasdisponibles(concierto.getPlazasdisponibles());
		conciertoEdit.setPrecioanticipado(concierto.getPrecioanticipado());
		conciertoEdit.setPreciotaquilla(concierto.getPreciotaquilla());	
		GrupoVO selectedGrupo = sg.findById(grupo).get();
		SalaVO selectedSala = ss.findById(sala).get();		 
	    conciertoEdit.setGrupo(selectedGrupo);		   
	    conciertoEdit.setSala(selectedSala);
		sc.save(conciertoEdit);		
		return "redirect:/conciertos";		
	}
	
	@GetMapping({"/delete/{id}"})
	public String borrarConcierto(@PathVariable int id) {
		sc.deleteById(id);		
		return "redirect:/conciertos";		 
	}
	
	@GetMapping("/addConcierto")
	public String addNuevoConcierto(Model model) {
		ConciertoVO concierto = new ConciertoVO();
		List<GrupoVO> grupos = sg.findAll();
		List<SalaVO> listaSalas = ss.findAll();
		model.addAttribute("nuevoConcierto", concierto);
		model.addAttribute("listaGrupos", grupos);
		model.addAttribute("listaSalas", listaSalas);
		return "/admin/conciertos/formulario_add_concierto";
	}
	
	@PostMapping("/addConcierto/submit")
	public String addConciertoSubmit(@ModelAttribute("nuevoConcierto") ConciertoVO concierto, 
			@RequestParam("grupos") int grupo, @RequestParam("salas") int sala,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		try {		
			GrupoVO selectedGrupo = sg.findById(grupo).get();
			SalaVO selectedSala = ss.findById(sala).get();	
		    concierto.setGrupo(selectedGrupo);
		    concierto.setSala(selectedSala);
			sc.save(concierto);
		} catch (DataIntegrityViolationException e) {		        
		       System.err.println("Clave unica duplicada " + e.getLocalizedMessage());		        
		       redirectAttributes.addFlashAttribute("errorMessage", "Clave única Id grupo y Fecha concierto duplicada. No se puede guardar el registro.");
		}	
		return "redirect:/conciertos";
	}	
	
	@PostMapping("/fechas/submit")
	public String fechaConcirtoGrupoBetweenSubmit(@RequestParam("fechaInicioStr") String fechaInicioStr, 
			@RequestParam("fechaFinStr") String fechaFinStr, 
			@RequestParam("gruposc") int idgrupo, 
			@RequestParam("rolename") String rolename,
			Model model) {
		DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fechaInicio = LocalDate.parse(fechaInicioStr,dTF);
		LocalDate fechaFin = LocalDate.parse(fechaFinStr,dTF);	
		GrupoVO grupo = sg.findById(idgrupo).get();	
		List<ConciertoVO> conciertoGrupoFechas = sc.buscarConciertoDeGrupoEntreFechas(grupo, fechaInicio, fechaFin).get();
		model.addAttribute("listaFechas", conciertoGrupoFechas);
		model.addAttribute("grupo", grupo);
		model.addAttribute("fini", fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		model.addAttribute("ffin", fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		model.addAttribute("rolename",rolename);
		return "/admin/conciertos/conciertos_grupo_fechas";		
	}
}
