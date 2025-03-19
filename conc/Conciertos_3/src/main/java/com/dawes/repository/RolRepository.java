package com.dawes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dawes.modelo.RolVO;
import com.dawes.modelo.UsuarioRolVO;

@Repository
public interface RolRepository extends CrudRepository<RolVO, Integer> {
	RolVO findByRoleName(String rolename);		
	@Query("select ur from UsuarioRolVO ur where ur.usuario.userName = ?1")	
	UsuarioRolVO buscarRolDeUsuario(String username);
}
