package com.dawes.modelo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="grupos")
public class GrupoVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idgrupo;
	@Column(unique=true)
	private String nombre;
	private String descripcion;
	private LocalDate fechacreacion;
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ConciertoVO> conciertos;
	
	@Override
	public String toString() {
	    return "GrupoVO{" +
	            "idgrupo=" + idgrupo +
	            ", nombre='" + nombre + '\'' +
	            ", descripcion='" + descripcion + '\'' +
	            ", fechacreacion=" + fechacreacion +
	            '}';
	}

}
