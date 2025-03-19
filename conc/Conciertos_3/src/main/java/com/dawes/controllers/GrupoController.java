package com.dawes.controllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawes.modelo.GrupoVO;
import com.dawes.serviciosImpl.ServicioGrupoImpl;

@Controller
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private ServicioGrupoImpl sg;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@GetMapping(value={"","/"})
	public String listadoGrupos(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		List<GrupoVO> listaGrupos = sg.findAll();
		String username = userDetails.getUsername();
		model.addAttribute("listadoGrupos", listaGrupos);
		model.addAttribute("username", username);
		return "/admin/grupos/grupos";
	}
	
	@GetMapping("/{id}")
	public String detallesGrupo(@PathVariable("id") int id, Model model) {
		GrupoVO grupo = sg.findById(id).get();		
		LocalDate fechaCreacion = grupo.getFechacreacion();		
		String fFechaCreacion = fechaCreacion.format(dtf);		   
		model.addAttribute("grupo", grupo);
		model.addAttribute("formattedFechaCreacion", fFechaCreacion);
		return "/admin/grupos/grupo";
	}
	
	@GetMapping("/editGrupo/{id}")
	public String editarGrupo(Model model, @PathVariable("id") int id) {
		GrupoVO grupo = sg.findById(id).get();		
		LocalDate fechaCreacion = grupo.getFechacreacion();	
		String fFechaCreacion = fechaCreacion.format(dtf);
		model.addAttribute("formattedFechaCreacion", fFechaCreacion);
		model.addAttribute("editedGrupo", grupo);
		return "/admin/grupos/formulario_editar_grupo";
	}
	
	@PostMapping("/editGrupo/submit")
	public String editGrupoSubmit(@ModelAttribute("editedGrupo") GrupoVO grupo, BindingResult bindingResult) {
		GrupoVO grupoEdit = sg.findById(grupo.getIdgrupo()).get();
		grupoEdit.setNombre(grupo.getNombre());
		grupoEdit.setDescripcion(grupo.getDescripcion());
		if (grupo.getFechacreacion() != null) {  // no ha sido cambiada
			grupoEdit.setFechacreacion(grupo.getFechacreacion());
		}		
		sg.save(grupoEdit);
		return "redirect:/grupos";
		
	}
	
	@GetMapping({"/delete/{id}"})
	public String borrarGrupo(@PathVariable int id) {
		sg.deleteById(id);		
		return "redirect:/grupos";		 
	}
	
	@GetMapping("/addGrupo")
	public String addNuevoGrupo(Model model) {
		GrupoVO grupo = new GrupoVO();
		model.addAttribute("nuevoGrupo", grupo);
		return "/admin/grupos/formulario_add_grupo";
	}
	
	@PostMapping("/addGrupo/submit")
	public String addGrupoSubmit(@ModelAttribute("nuevoGrupo") GrupoVO grupo, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		try {
			sg.save(grupo);
		} catch (DataIntegrityViolationException e) {		        
	        System.err.println("Clave unica duplicada " + e.getLocalizedMessage());		        
	        redirectAttributes.addFlashAttribute("errorMessage", "Clave única Nombre grupo duplicada. No se puede guardar el registro.");
		}		
		return "redirect:/grupos";
	}	

}
