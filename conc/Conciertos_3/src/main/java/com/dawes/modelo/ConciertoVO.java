package com.dawes.modelo;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="conciertos", uniqueConstraints=@UniqueConstraint(columnNames= {"fecha","idgrupo"}))
public class ConciertoVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idconcierto;
	private LocalDate fecha;
	private Time hora;
	private int plazasdisponibles;
	private float precioanticipado;
	private float preciotaquilla;
	@ManyToOne
	@JoinColumn(name="idgrupo")
	private GrupoVO grupo;
	@ManyToOne
	@JoinColumn(name="idsala")
	private SalaVO sala;
	@OneToMany(mappedBy = "concierto", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonIgnore	
	private List<VentaVO> ventas;
	
	// para evitar recursión
	@Override
	public String toString() {
	    return "ConciertoVO{" +
	            "idconcierto=" + idconcierto +
	            ", fecha=" + fecha +
	            ", hora=" + hora +
	            ", plazasdisponibles=" + plazasdisponibles +
	            ", precioanticipado=" + precioanticipado +
	            ", preciotaquilla=" + preciotaquilla +
	            ", grupo=" + (grupo != null ? grupo.getIdgrupo() : null) + 
	            '}';
	}
}
