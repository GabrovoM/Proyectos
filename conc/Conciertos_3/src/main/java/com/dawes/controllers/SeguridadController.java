package com.dawes.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;
import com.dawes.modelo.UsuarioRolVO;
import com.dawes.serviciosImpl.ServicioConciertoImpl;
import com.dawes.serviciosImpl.ServicioGrupoImpl;
import com.dawes.serviciosImpl.ServicioRolImpl;
import com.dawes.serviciosImpl.ServicioSalaImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SeguridadController {
	@Autowired
	private ServicioConciertoImpl sc;
	@Autowired
	private ServicioGrupoImpl sg;
	@Autowired
	private ServicioSalaImpl ss;
	@Autowired
	private ServicioRolImpl rs;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");	
	LocalDate hoy = LocalDate.now();
	
	// En la BD en la tabla 'roles' hay que tener insertados 3 registros 
	// con los nombres 'ROLE_ADMIN', 'ROLE_USER' and 'ROLE_ANONYMOUS'
	// para que pasen las comprobaciones de rol de usuario aquí
		
	@RequestMapping("/admin")
	public String admin(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		String username = userDetails.getUsername();
		UsuarioRolVO urol = rs.buscarRolDeUsuario(username);		
		String role = urol.getRol().getRoleName();
		model.addAttribute("username", username);
		model.addAttribute("rolename", role);
		return "admin/home";
	}

	@RequestMapping("/registrado")
	public String registrado(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		List<ConciertoVO> listaConciertos = sc.findAll();
		List<GrupoVO> listaGrupos = sg.findAll();
		List<SalaVO> listaSalas = ss.findAll();
		String username = userDetails.getUsername();
		UsuarioRolVO urol = rs.buscarRolDeUsuario(username);		
		String role = urol.getRol().getRoleName();		 
		List<ConciertoVO> filtrados = new ArrayList<>();
	    for (ConciertoVO concierto : listaConciertos) {
	        if (!concierto.getFecha().isBefore(hoy)) {
	        	filtrados.add(concierto);
	        }
	    }		
		model.addAttribute("username", username);
		model.addAttribute("rolename", role);
//		model.addAttribute("listaConciertos", listaConciertos);	
		model.addAttribute("listaConciertos", filtrados);
		model.addAttribute("listaGrupos", listaGrupos);
		model.addAttribute("listaSalas", listaSalas);
		return "registrado/registrado";
	}

	@RequestMapping(value={"","/","/index"})
	public String index(Model model) {
		List<ConciertoVO> listaConciertos = sc.findAll();
		List<GrupoVO> listaGrupos = sg.findAll();
		List<SalaVO> listaSalas = ss.findAll();
		List<ConciertoVO> filtrados = new ArrayList<>();
	    for (ConciertoVO concierto : listaConciertos) {
	        if (!concierto.getFecha().isBefore(hoy)) {
	        	filtrados.add(concierto);
	        }
	    }
//		model.addAttribute("listaConciertos", listaConciertos);	
		model.addAttribute("listaConciertos", filtrados);
		model.addAttribute("listaGrupos", listaGrupos);
		model.addAttribute("listaSalas", listaSalas);
		model.addAttribute("rolename", "ROLE_ANONYMOUS");
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logoutG() {
	    return "logout";
	}

	@PostMapping("/logout")
	public String logout() {
		return "logout";
	}

	@RequestMapping(value = "/403")
	public String accesoDenegado(Model modelo) {
		modelo.addAttribute("message", "No tienes permiso de acceso a esta página");
		return "403Page";
	}
	
	@RequestMapping("/redirectByRole")
    public String redirectByRole(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    return "redirect:/admin";
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    return "redirect:/registrado";
                }
            }
        }
        // usuario anónimo
        return "redirect:/index";
    }	

}
