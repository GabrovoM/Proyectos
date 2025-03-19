package com.dawes;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.VentaVO;
import com.dawes.serviciosImpl.ServicioConciertoImpl;
import com.dawes.serviciosImpl.ServicioGrupoImpl;
import com.dawes.serviciosImpl.ServicioVentaImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestVenta {
	@Autowired 
	ServicioVentaImpl svi;
	@Autowired 
	ServicioConciertoImpl sci;
	@Autowired
	ServicioGrupoImpl sgi;

	@Test
	@Order(1)
	public void test01InsertarVenta() {
		ConciertoVO c1 = sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 13), sgi.findByNombre("Karizma").get()).get();
		float precioanticipado1 = c1.getPrecioanticipado();		
		svi.save(new VentaVO(0,LocalDate.now(),Time.valueOf(LocalTime.now()),"12345678B",3*precioanticipado1,3,c1));
		ConciertoVO c2 = sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 11), sgi.findByNombre("BTR").get()).get();
		float precioanticipado2 = c2.getPrecioanticipado();	
		assertNotNull(svi.save(new VentaVO(0,LocalDate.now(),Time.valueOf(LocalTime.now()),"23456789C",2*precioanticipado2,2,c2)));
	}
	
	@Test
	@Order(2)
	public void test02FindByConcierto() {
		assertEquals(1,svi.findByConcierto(sci.findByFechaAndGrupo(LocalDate.of(2024,06,13), sgi.findByNombre("Karizma").get()).get()).get().size());
	}
	
	@Test
	@Order(3)
	public void test03BuscarConciertosPorGrupoYFecha() {
		assertEquals(1, svi.buscarConciertosPorGrupoYFecha(sgi.findByNombre("Karizma").get(), LocalDate.of(2024,05,15), LocalDate.of(2024,06,15)).get().size());
	}
	
	@Test
	@Order(4)
	public void test04FindByDni() {
		assertEquals("Karizma", svi.findByDni("12345678B").get().get(0).getConcierto().getGrupo().getNombre());
	}
	
	@Test
	@Order(5)
	public void test05FindAll() {
		assertEquals(2,svi.findAll().size());
	}
	
	@Test
	@Order(6)
	public void test06ModificarVenta() {
		VentaVO v = svi.findAll().get(1);
		v.setDni("34567890D");
		assertEquals("34567890D", svi.save(v).getDni());
	}
	
	@Test
	@Order(7)
	public void test07EliminarVenta() {
		VentaVO v = svi.findAll().get(1);
		svi.delete(v);
		assertEquals(1, svi.findAll().size());
	}
	
	@Test
	@Order(8)
	public void test08AccederAVentasDeUnConcierto() {
		ConciertoVO c = sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 13), sgi.findByNombre("Karizma").get()).get();
		assertEquals(1,c.getVentas().size());
	}
	

}
