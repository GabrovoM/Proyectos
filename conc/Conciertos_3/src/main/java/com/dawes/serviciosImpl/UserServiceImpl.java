package com.dawes.serviciosImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawes.modelo.UsuarioVO;
import com.dawes.repository.UsuarioRepository;
import com.dawes.servicios.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UsuarioRepository ur;
	@Autowired
	 private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UsuarioVO registrarUsuario(UsuarioVO usuario) {		
		return ur.save(usuario);
	}

	@Override
	public UsuarioVO findByUserNameAndEncrytedPassword(String usuario, String password) {
		UsuarioVO u = ur.findByUserName(usuario);
		if (u != null && passwordEncoder.matches(password, u.getEncrytedPassword())) {
	        return u;
	    } else {
	        return null;
	    }
	}

	@Override
	public UsuarioVO findByUserName(String userName) {		
		return ur.findByUserName(userName);
	}

	@Override
	public UsuarioVO findByDni(String dni) {		
		return ur.findByDni(dni);
	}

}
