package com.dawes.servicios;

import java.util.Optional;

import com.dawes.modelo.RolVO;
import com.dawes.modelo.UsuarioVO;

public interface UserService {	
	UsuarioVO registrarUsuario(UsuarioVO usuario);	
	UsuarioVO findByUserNameAndEncrytedPassword(String usuario, String password);
	UsuarioVO findByUserName(String userName);
	UsuarioVO findByDni(String dni);
}
