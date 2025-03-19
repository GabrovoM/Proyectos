package com.dawes.servicios;

import com.dawes.modelo.RolVO;
import com.dawes.modelo.UsuarioRolVO;

public interface ServicioRol {
	RolVO findByRoleName(String rolename);	
	UsuarioRolVO buscarRolDeUsuario(String username);
}
