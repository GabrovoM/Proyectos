package com.dawes.modelo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="salas")
public class SalaVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idsala;
	@Column(unique=true)
	private String nombre;
	private int aforo;
	
	@OneToMany(mappedBy = "sala")
	@JsonIgnore
	private List<ConciertoVO> conciertos;
}
