package com.dawes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dawes.modelo.UsuarioVO;

public interface UsuarioRepository extends CrudRepository<UsuarioVO, Integer> {
	UsuarioVO findByUserNameAndEncrytedPassword(String usuario, String password);
	UsuarioVO findByUserName(String userName);
	UsuarioVO findByDni(String dni);
}
