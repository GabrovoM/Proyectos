package com.dawes.controllers;

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

import com.dawes.modelo.SalaVO;
import com.dawes.serviciosImpl.ServicioSalaImpl;

@Controller
@RequestMapping("/sala")
public class SalaController {

	@Autowired
	private ServicioSalaImpl ss;

	@GetMapping(value={"","/"})
	public String sala(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		List<SalaVO> lista = ss.findAll();
		String username = userDetails.getUsername();
		model.addAttribute("sala", lista);
		model.addAttribute("username", username);
		return "/admin/salas/sala";}
	
	@GetMapping("/{id}")
	public String detallesSala(@PathVariable("id") int id, Model model) {
		SalaVO sala = ss.findById(id).get();		
		model.addAttribute("sala", sala);
		return "/admin/salas/detalles_sala";
	}
	
	@GetMapping("/editSala/{id}")
	public String editarSala(Model model, @PathVariable("id") int id) {
		SalaVO sala = ss.findById(id).get();
		model.addAttribute("editedSala", sala);		
		return "/admin/salas/formulario_editar_sala";
	}
	
	@PostMapping("/editSala/submit") 
	public String editarSalaSubmit(@ModelAttribute("editedSala") SalaVO sala, BindingResult bindingResult) {
		SalaVO salaEditar = ss.findById(sala.getIdsala()).get();
		salaEditar.setNombre(sala.getNombre());
		salaEditar.setAforo(sala.getAforo());
		ss.save(salaEditar);
		return "redirect:/sala";
	}
	
	@GetMapping("addSala")
	public String addSala(Model model ) {
		SalaVO sala = new SalaVO();
		model.addAttribute("nuevaSala", sala);
		return "/admin/salas/formulario_add_sala";
	}
	
	@PostMapping("/addSala/submit") 
	public String addSalaSubmit(@ModelAttribute("nuevaSala") SalaVO sala, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		try {
	       ss.save(sala);
	    } catch (DataIntegrityViolationException e) {		        
	       System.err.println("Clave unica duplicada " + e.getLocalizedMessage());		        
	       redirectAttributes.addFlashAttribute("errorMessage", "Clave única Nombre sala duplicada. No se puede guardar el registro.");
	    }		 
		return "redirect:/sala";
	}
	
	@GetMapping({"/delete/{id}"})
	public String borrarSala(@PathVariable int id, RedirectAttributes redirectAttributes) {
		try {
		ss.deleteById(id);		
		} catch (DataIntegrityViolationException e) {		        
	       System.err.println("tiene conciertos " + e.getLocalizedMessage());		        
	       redirectAttributes.addFlashAttribute("errorMessage", "Esta sala tiene conciertos asociados. Para eliminar la sala hay que eliminar los conciertos primero.");
	    }	
		return "redirect:/sala";		 
	}
	
}
