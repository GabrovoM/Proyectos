package com.dawes.serviciosImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.modelo.RolVO;
import com.dawes.modelo.UsuarioRolVO;
import com.dawes.repository.RolRepository;
import com.dawes.servicios.ServicioRol;
@Service
public class ServicioRolImpl implements ServicioRol {
@Autowired
private RolRepository rr;

	@Override
	public RolVO findByRoleName(String rolename) {	
		return rr.findByRoleName(rolename);
	}

	@Override
	public UsuarioRolVO buscarRolDeUsuario(String username) {		
		return rr.buscarRolDeUsuario(username);
	}

}
