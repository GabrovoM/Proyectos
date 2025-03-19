package com.dawes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawes.modelo.RolVO;
import com.dawes.modelo.UsuarioRolVO;
import com.dawes.modelo.UsuarioVO;
import com.dawes.serviciosImpl.ServicioRolImpl;
import com.dawes.serviciosImpl.UserServiceImpl;

@Controller
public class RegistroController {
	 @Autowired
	 private UserServiceImpl us; 
	 @Autowired
	 private ServicioRolImpl rs;
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/registrarse")
    public String formularioRegistrarse() {	    	
        return "registrarse";
    }

    @PostMapping("/registrarse")
    public String procesarRegistro(@RequestParam("username") String username, 
    							   @RequestParam("password") String password, 
    							   @RequestParam("dni") String dni, 
    							   RedirectAttributes redirectAttributes) {	
    	UsuarioVO existente = null;
    	boolean existe = false;
    	if (us.findByDni(dni) != null) {  // existe este dni 
    		existente = us.findByDni(dni);
    		System.err.println(existente);
    		existe=true;
    	}
    	UsuarioVO newUser = new UsuarioVO(); 
    	RolVO rol = rs.findByRoleName("ROLE_USER");
    	String encodedPassword = passwordEncoder.encode(password);  
	   	if (us.findByUserNameAndEncrytedPassword(username, password) == null) { // no existe registro con estos credenciales
	   		if (!existe) {  // no existe este dni en la BD => se inserta nuevo registro
	   			newUser.setUserName(username);
	   			newUser.setEncrytedPassword(encodedPassword);
	   			newUser.setEnabled(true);
	   			newUser.setDni(dni);	   			
		    	UsuarioRolVO urvo = new UsuarioRolVO(newUser, rol);
		    	newUser.getRoles().add(urvo);
		    	rol.getUsuarios().add(urvo);
		        us.registrarUsuario(newUser);
	   		} else {  // el dni existe en la bd => se cambian username & password y se modifica el registro con este dni
	   			existente.setUserName(username);
	   			existente.setEncrytedPassword(encodedPassword);
	   			existente.setEncrytedPassword(encodedPassword);
	   			us.registrarUsuario(existente);
	   		}  
	        return "redirect:/login"; 
	   	} else {   // existe registro con este username & password
			redirectAttributes.addFlashAttribute("errorMessage", "Ya existe registro con estos credenciales.");
			return "redirect:/registrarse";
	   	}
    }

}
