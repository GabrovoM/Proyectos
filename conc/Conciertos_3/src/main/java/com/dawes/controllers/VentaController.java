package com.dawes.controllers;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.dawes.modelo.VentaVO;
import com.dawes.serviciosImpl.ServicioConciertoImpl;
import com.dawes.serviciosImpl.ServicioGrupoImpl;
import com.dawes.serviciosImpl.ServicioVentaImpl;
import com.dawes.serviciosImpl.UserServiceImpl;

@Controller
@RequestMapping("/ventas")
public class VentaController {

	@Autowired
	private ServicioVentaImpl sv;
	@Autowired
	private ServicioConciertoImpl sc;
	@Autowired
	private ServicioGrupoImpl sg;
	@Autowired
	private UserServiceImpl us;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@GetMapping(value={"","/"})
	public String listadoVentas(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		List<VentaVO> listaVentas = sv.findAll();
		List<GrupoVO> listaGrupos = sg.findAll();
		String username = userDetails.getUsername();
		model.addAttribute("username", username);
 		model.addAttribute("listadoVentas", listaVentas);
 		model.addAttribute("listaGrupos", listaGrupos);
		return "/admin/ventas/ventas";
	}
	
	@GetMapping("/{id}")
	public String detallesVenta(@PathVariable("id") int idventa, Model model) {
		VentaVO venta = sv.findById(idventa).get();
		model.addAttribute("venta", venta);
		return "/admin/ventas/venta";
	}	
	
	// añadir nueva entrada desde el panel del admin
	@GetMapping("/entradas")
	public String ventaEntradas(Model model) {
		VentaVO venta = new VentaVO();
		List<GrupoVO> listaGrupos = sg.findAll();		
		model.addAttribute("nuevaVenta", venta);
		model.addAttribute("grupos", listaGrupos);
		return "/admin/ventas/formulario_venta_add";
	}
	
	// añadir entrada desde el panel del admin
	@PostMapping("/entradas/submit")
	public String nuevaVentaSubmit( 
	    @RequestParam("fechacon") int idConcierto,
	    @RequestParam("grupo") int idGrupo,
	    @ModelAttribute("nuevaVenta") VentaVO newVenta,
	    BindingResult bindingResult,
	    Model model) {		
	    GrupoVO grupo = sg.findById(idGrupo).get();
	    Optional<ConciertoVO> conciertoOpt = sc.findByFechaAndGrupo(sc.findById(idConcierto).get().getFecha(), grupo);
	    float total = 0;
	    if (conciertoOpt.isPresent()) {
	        ConciertoVO concierto = conciertoOpt.get();	       
	        int plazas = concierto.getPlazasdisponibles();	
	        if (concierto.getFecha().isAfter(LocalDate.now())) {
	        	total = concierto.getPrecioanticipado()*newVenta.getNumeroentradas();	
	        } else {
	        	total = concierto.getPreciotaquilla()*newVenta.getNumeroentradas();	
	        }	
	       // float total = concierto.getPrecioanticipado()*newVenta.getNumeroentradas();	        
	        newVenta.setFecha(LocalDate.now());
	        newVenta.setHora(Time.valueOf(LocalTime.now()));
	        newVenta.setConcierto(concierto);
	        newVenta.setTotal(total);	        
	        sv.save(newVenta);
	        return "redirect:/ventas";
	    } else {
	        return "/admin/home";
	    }
	}
	
	// consulta de ventas por concierto
	@GetMapping("/concierto/{id}")
	public String ventasByConcierto(@PathVariable("id") int idconcierto, Model model) {
		List<VentaVO> ventasConcierto = sv.findByConcierto(sc.findById(idconcierto).get()).get();
		ConciertoVO concierto = sc.findById(idconcierto).get();
		model.addAttribute("concierto", concierto);
		model.addAttribute("ventasConcierto", ventasConcierto);
		return "/admin/ventas/ventas_concierto";
	}
	
	// consulta de ventas por grupo y entre fechas
	@GetMapping("/{grupo}/{fecha1}/{fecha2}")
	public String ventasGrupoFechas(@PathVariable("grupo") GrupoVO grupo, 
									@PathVariable("fecha1") LocalDate fecha1,
									@PathVariable("fecha2") LocalDate fecha2,
									Model model) {		
		if (fecha1.isBefore(fecha2)) {
			List<VentaVO> ventasGrupoFechas = sv.buscarConciertosPorGrupoYFecha(grupo, fecha1, fecha2).get();
			model.addAttribute("listaFechas", ventasGrupoFechas);
			model.addAttribute("grupo", grupo);
			model.addAttribute("fecha1", fecha1);
			model.addAttribute("fecha2", fecha2);
			return "ventas_grupo_fechas";
		} else {
			return "redirect:/ventas";
		}		
	}
	
	// consulta de ventas por DNI del comprador
	@PostMapping("/fechas/submit")
	public String fechaBetweenSubmit(@RequestParam("fechaInicioStr") String fechaInicioStr, 
			@RequestParam("fechaFinStr") String fechaFinStr, @RequestParam("grupos") int idgrupo, Model model) {
		DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fechaInicio = LocalDate.parse(fechaInicioStr,dTF);
		LocalDate fechaFin = LocalDate.parse(fechaFinStr,dTF);	
		GrupoVO grupo = sg.findById(idgrupo).get();	
		List<VentaVO> ventasGrupoFechas = sv.buscarConciertosPorGrupoYFecha(grupo, fechaInicio, fechaFin).get();
		model.addAttribute("listaFechas", ventasGrupoFechas);
		model.addAttribute("grupo", grupo);
		model.addAttribute("fini", fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		model.addAttribute("ffin", fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		return "/admin/ventas/ventas_grupo_fechas";		
	}
	
	@PostMapping("/dni/submit")
	public String ventasPorDni(@RequestParam("dni") String dni, Model model) {
		List<VentaVO> ventasDni = sv.findByDni(dni).get();
		model.addAttribute("ventasDni", ventasDni);
		model.addAttribute("dni", dni);
		return "/admin/ventas/ventas_dni";
	}
	
	@GetMapping({"/delete/{id}"})
	public String borrarVenta(@PathVariable int id) {
		VentaVO v = sv.findById(id).get();
		ConciertoVO c = v.getConcierto();
		c.setPlazasdisponibles(c.getPlazasdisponibles()+v.getNumeroentradas());
		sv.deleteById(id);		
		return "redirect:/ventas";		 
	}
	
	@GetMapping("/editVenta/{id}")
	public String editarVenta(Model model, @PathVariable("id") int id) {
		VentaVO venta = sv.findById(id).get();
		List<ConciertoVO> conciertos = sc.findAll();
		List<GrupoVO> listaGrupos = sg.findAll();
		List<ConciertoVO> fechasConcierto = sc.findByGrupo(venta.getConcierto().getGrupo()).get();
		LocalDate fecha = venta.getFecha();	
		LocalDate fechacon = venta.getConcierto().getFecha();
		String fFecha = fecha.format(dtf);
		String fFechaCon = fechacon.format(dtf);
		ConciertoVO conciertoActual = venta.getConcierto();		
		model.addAttribute("formattedFecha", fFecha);
		model.addAttribute("formattedFechaCon", fFechaCon);
		model.addAttribute("editedVenta", venta);
		model.addAttribute("conciertos", conciertos);
		model.addAttribute("grupos", listaGrupos);
		model.addAttribute("fechasConcierto", fechasConcierto);		
		model.addAttribute("idconcierto",conciertoActual.getIdconcierto());
		model.addAttribute("numeroentradas",venta.getNumeroentradas());		
		return "/admin/ventas/formulario_editar_venta";		
	}
	
	
	@PostMapping("/editVenta/submit")
	public String editarVentaSubmit(@ModelAttribute("editedVenta") VentaVO venta, 
			@RequestParam("grupo") int grupo, 
			@RequestParam("fechacon") int idConcierto,  // nuevo
			@RequestParam("id_concierto") int id_concierto,  // antiguo
			@RequestParam("numentradas") int numentradas,
            BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            ) {			
		
	    VentaVO ventaEditar = sv.getById(venta.getIdventa());	    
	    ConciertoVO conciertoOriginal = sc.findById(id_concierto).get(); 	  
	    ConciertoVO conciertoNuevo = sc.findByFechaAndGrupo(sc.findById(idConcierto).get().getFecha(), sg.findById(grupo).get()).get();
	    
	    if (!conciertoNuevo.getFecha().isBefore(LocalDate.now())) {		
		    if (conciertoOriginal.getIdconcierto() != conciertoNuevo.getIdconcierto()) { 
		    	conciertoOriginal.setPlazasdisponibles(conciertoOriginal.getPlazasdisponibles()+numentradas);	
		    } else { // el mismo concierto
		    	if (venta.getNumeroentradas() != numentradas) {	    		
		    		conciertoOriginal.setPlazasdisponibles(conciertoOriginal.getPlazasdisponibles()+numentradas);
		    	} else {  // venta.getNumeroentradas() == numentradas
		    		conciertoOriginal.setPlazasdisponibles(conciertoOriginal.getPlazasdisponibles()+numentradas);
		    	}
		    }	    
		    ventaEditar.setDni(venta.getDni());
	        if (conciertoNuevo.getFecha().isAfter(LocalDate.now())) {
	        	ventaEditar.setTotal(venta.getNumeroentradas()*conciertoNuevo.getPrecioanticipado());
	        } else {
	        	ventaEditar.setTotal(venta.getNumeroentradas()*conciertoNuevo.getPreciotaquilla());
	        }	        
//		    ventaEditar.setTotal(venta.getNumeroentradas()*conciertoNuevo.getPrecioanticipado());
		    ventaEditar.setNumeroentradas(venta.getNumeroentradas());
		    ventaEditar.setConcierto(conciertoNuevo);
		    sv.save(ventaEditar);  
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "La fecha elegida ha pasado. No se puede editar la venta.");
		}	    
	    
	    return "redirect:/ventas";
	}
	
	// venta de entradas usuario registrado 
	@GetMapping("/comprarEntradasConcierto/{id}")
	public String comprarEntrada(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable("id") int id) {
		String username = userDetails.getUsername();
		String dni = us.findByUserName(username).getDni();		
		VentaVO venta = new VentaVO();
		ConciertoVO concierto = sc.findById(id).get();	
		String nombreGrupo = concierto.getGrupo().getNombre();
		int idgrupo=concierto.getGrupo().getIdgrupo();
		LocalDate fecha = concierto.getFecha();	
		Time hora = concierto.getHora();
		String fFecha = fecha.format(dtf);
		model.addAttribute("formattedFecha", fFecha);
		model.addAttribute("hora", hora);
		model.addAttribute("nuevaVenta", venta);
		model.addAttribute("idcon", id);
		model.addAttribute("nombreGrupo", nombreGrupo);
		model.addAttribute("idgrupo", idgrupo);
		model.addAttribute("dni", dni);
		return "/admin/ventas/formulario_venta";		
	}
	
	// usuario registrado
	@PostMapping("/entradascompra/submit")
	public String nuevaVentaSubmit( 	       
	    @ModelAttribute("nuevaVenta") VentaVO newVenta,
	    @RequestParam("idcon") int idcon,
	    @RequestParam("dni") String dni,
	    BindingResult bindingResult,
	    Model model) {				
		try {
			float total = 0;
			ConciertoVO concierto = sc.findById(idcon).get();
	        int plazas = concierto.getPlazasdisponibles();		        
	        if (concierto.getFecha().isAfter(LocalDate.now())) {
	        	total = concierto.getPrecioanticipado()*newVenta.getNumeroentradas();	
	        } else {
	        	total = concierto.getPreciotaquilla()*newVenta.getNumeroentradas();	
	        }	    
	        
	        ///float total = concierto.getPrecioanticipado()*newVenta.getNumeroentradas();	        
	        newVenta.setFecha(LocalDate.now());
	        newVenta.setHora(Time.valueOf(LocalTime.now()));
	        newVenta.setConcierto(concierto);
	        newVenta.setDni(dni);
	        newVenta.setTotal(total);	
	        String fFecha = concierto.getFecha().format(dtf);
			model.addAttribute("formattedFecha", fFecha);
	        model.addAttribute("nventa", newVenta);
	        sv.save(newVenta);	
	        return "/admin/ventas/venta_exito";
		} catch (Exception e) {
			System.out.println("Se ha producido un error al realizar la compra "+e.getLocalizedMessage());
			return "/admin/ventas/venta_fallada";
		}
	        
	}	
	
}
