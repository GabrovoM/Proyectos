package com.dawes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dawes.modelo.SalaVO;
import com.dawes.serviciosImpl.ServicioSalaImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestSala {
	@Autowired
	ServicioSalaImpl ssi;

	@Test
	@Order(1)
	public void test01InsertarSala() {
		ssi.save(new SalaVO(0,"Universiada",1000, new ArrayList<>()));
		assertEquals("NDK-1",ssi.save(new SalaVO(0,"NDK-1",1200, new ArrayList<>())).getNombre());
	}
	
	// descomentar el try/catch en el método save en ServicioSalaImpl
//	@Test
//	@Order(2)
//	public void test02InsertarSalaClaveUnicaDuplicada() {		
//		assertNull(ssi.save(new SalaVO(0, "Universiada",1300, new ArrayList())));
//	}
	
	@Test
	@Order(3)
	public void test03FindByNombre() {
		assertEquals("Universiada", ssi.findByNombre("Universiada").get().getNombre());
	}
	
	@Test
	@Order(4)
	public void test04FindById() {
		assertEquals(1, ssi.findById(1).get().getIdsala());
	}
	
	@Test
	@Order(5)
	public void test05FindByNombreInexistente() {
		 assertFalse(ssi.findByNombre("NDK-2").isPresent());
	}
	
	@Test
	@Order(6)
	public void test06FindAll() {
		assertEquals(2, ssi.findAll().size());
	}
	
	@Test
	@Order(7)
	public void test07ModificarSala() {
		SalaVO sala = ssi.findByNombre("NDK-1").get();
		sala.setAforo(1300);
		assertEquals(1300, ssi.save(sala).getAforo());
	}
	
	@Test
	@Order(8)
	public void test08EliminarSala() {
		Optional<SalaVO> s = ssi.findByNombre("NDK-1");
		if (s.isPresent()) {
			SalaVO sala = s.get();
			ssi.delete(sala);
		}
		assertFalse(ssi.findByNombre("NDK-1").isPresent());
	}
	

}
