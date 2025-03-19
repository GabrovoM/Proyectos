package com.dawes;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dawes.modelo.ConciertoVO;
import com.dawes.modelo.GrupoVO;
import com.dawes.modelo.SalaVO;
import com.dawes.serviciosImpl.ServicioConciertoImpl;
import com.dawes.serviciosImpl.ServicioGrupoImpl;
import com.dawes.serviciosImpl.ServicioSalaImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestConcierto {
	@Autowired 
	ServicioConciertoImpl sci;
	@Autowired
	ServicioGrupoImpl sgi;
	@Autowired
	ServicioSalaImpl ssi;

	@Test
	@Order(1)
	public void test01InsertarConcierto() {
		sci.save(new ConciertoVO(0,LocalDate.of(2024, 05, 03), Time.valueOf(LocalTime.of(20,0,0, 0)),
				35,40f,42f,sgi.findByNombre("Karizma").get(),
				ssi.findByNombre("Universiada").get(),new ArrayList<>()));
		sci.save(new ConciertoVO(0,LocalDate.of(2024, 06, 11), Time.valueOf(LocalTime.of(19,45,0, 0)),
				100,40f,42f,sgi.findByNombre("BTR").get(),
				ssi.findByNombre("Universiada").get(),new ArrayList<>()));
		sci.save(new ConciertoVO(0,LocalDate.of(2024, 06, 10), Time.valueOf(LocalTime.of(20,0,0, 0)),
				96,41f,42f,sgi.findByNombre("BTR").get(),
				ssi.findByNombre("Universiada").get(),new ArrayList<>()));
		assertEquals("Karizma", sci.save(new ConciertoVO(0,LocalDate.of(2024, 06, 13), Time.valueOf(LocalTime.of(20,30,0, 0)),
				109,35f,38f,sgi.findByNombre("Karizma").get(),
				ssi.findByNombre("Universiada").get(),new ArrayList<>())).getGrupo().getNombre());		
	}
	
	// descomentar el try/catch en el método save en ServicioConciertoImpl
//	@Test
//	@Order(2)
//	public void test02InsertarConciertoClaveUnicaDuplicada() {
//		assertNull(sci.save(new ConciertoVO(0,LocalDate.of(2024, 06, 10), Time.valueOf(LocalTime.of(20,30,0, 0)),
//				180,41f,42f,sgi.findByNombre("BTR").get(),
//				ssi.findByNombre("Universiada").get(),new ArrayList<>())));
//	}
	
	@Test
	@Order(3)
	public void test03FindById() {
		assertEquals("Karizma", sci.findById(1).get().getGrupo().getNombre());
	}
	
	@Test
	@Order(4)
	public void test04findByFechaAndGrupo() {
		assertEquals(96, sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 10), sgi.findByNombre("BTR").get()).get().getPlazasdisponibles());
	}
	
	@Test
	@Order(5)
	public void test05BuscarConciertoDeGrupoEntreFechas() {	
		assertEquals(1, sci.buscarConciertoDeGrupoEntreFechas(sgi.findByNombre("Karizma").get(), LocalDate.of(2024, 06, 01), LocalDate.of(2024, 06, 15)).get().size());
	}
	
	@Test
	@Order(6)
	public void test06findByFechaBetween() {
		assertEquals(3, sci.findByFechaBetween(LocalDate.of(2024, 06, 01), LocalDate.of(2024, 06, 15)).get().size());
	}
	
	@Test
	@Order(7)
	public void test07findByFechaBetweenVacio() {
		assertEquals(0, sci.findByFechaBetween(LocalDate.of(2024, 07, 01), LocalDate.of(2024, 07, 05)).get().size());
	}
	
	@Test
	@Order(8)
	public void test08buscarFechasPorGrupo() {
		assertEquals(2, sci.buscarFechasPorGrupo(sgi.findByNombre("BTR").get()).size());
	}
	
	@Test
	@Order(9)
	public void test09findByGrupo() {
		assertEquals(2, sci.findByGrupo(sgi.findByNombre("BTR").get()).get().size());
	}

	@Test
	@Order(10)
	public void test10FindBySalaAndFechaBetween() {
		assertEquals(2, sci.findBySalaAndFechaBetween(ssi.findByNombre("Universiada").get(), LocalDate.of(2024, 06, 05), LocalDate.of(2024, 06, 11)).get().size());
	}
	
	@Test
	@Order(11)
	public void test11FindAll() {
		assertEquals(4, sci.findAll().size());
	}
	
	@Test
	@Order(12)
	public void test12ModificarConcierto() {
		ConciertoVO c = sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 13), sgi.findByNombre("Karizma").get()).get();
		c.setPrecioanticipado(32f);
		assertEquals(32f, sci.save(c).getPrecioanticipado());
	}
	
	@Test
	@Order(13)
	public void test13EliminarConcierto() {
		Optional<ConciertoVO> c= sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 10), sgi.findByNombre("BTR").get());
		if (c.isPresent()) {
			ConciertoVO concierto = c.get();
			sci.delete(concierto);
		}
		assertFalse(sci.findByFechaAndGrupo(LocalDate.of(2024, 06, 10), sgi.findByNombre("BTR").get()).isPresent());
	}
	
	@Test
	@Order(14)
	public void test14AccederAConciertosDeUnGrupo() {
		GrupoVO g = sgi.findByNombre("BTR").get();
		assertEquals(1,g.getConciertos().size());
	}	


}
