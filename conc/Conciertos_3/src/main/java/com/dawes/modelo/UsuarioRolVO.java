package com.dawes.modelo;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios_roles")
public class UsuarioRolVO {

	@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "Id", nullable = false)
	   private int id;

	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name = "User_Id", nullable = false)
	   private UsuarioVO usuario;

	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name = "Role_Id", nullable = false)
	   private RolVO rol;

	public UsuarioRolVO(UsuarioVO usuario, RolVO rol) {
		super();
		this.usuario = usuario;
		this.rol = rol;
	}
	   
	   

}
