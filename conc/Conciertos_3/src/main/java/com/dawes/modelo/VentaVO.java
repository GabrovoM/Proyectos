package com.dawes.modelo;

import java.sql.Time;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="ventas")
public class VentaVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idventa;
	private LocalDate fecha;
	private Time hora;
	private String dni;
	private float total;
	private int numeroentradas;
	@ManyToOne
	@JoinColumn(name="idconcierto")
	private ConciertoVO concierto;
	
}
