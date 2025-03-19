package com.dawes.modelo;

import java.util.ArrayList;
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
@Table(name = "usuarios")
public class UsuarioVO {
	
	@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "User_Id", nullable = false)
	   private int userId;

	   @Column(name = "User_Name", length = 36, nullable = false)
	   private String userName;
	   
	   @Column(name = "dni", length = 15, nullable = false)
	   private String dni;

	   @Column(name = "Encryted_Password", length = 128, nullable = false)
	   private String encrytedPassword;

	   @Column(name = "Enabled", length = 1, nullable = false)
	   private boolean enabled;
	   
	   @OneToMany(mappedBy="usuario",fetch=FetchType.EAGER,cascade= {CascadeType.ALL}, orphanRemoval = true)
		private List<UsuarioRolVO> roles  = new ArrayList<>();
	   
	   @Override
	    public String toString() {
	        return "UsuarioVO{" +
	                "userId=" + userId +
	                ", userName='" + userName + '\'' +
	                ", encrytedPassword='" + encrytedPassword + '\'' +
	                ", enabled=" + enabled +
	                '}';
	    }

}
