package com.dawes.modelo;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class RolVO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_Id", nullable = false)
    private int roleId;
	 
	    @Column(name = "Role_Name", length = 30, nullable = false)
	    private String roleName;
	 
	    @OneToMany(mappedBy="rol",fetch=FetchType.EAGER,cascade= {CascadeType.ALL}, orphanRemoval = true)
		private List<UsuarioRolVO> usuarios;
}


