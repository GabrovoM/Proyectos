package com.dawes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dawes.modelo.GrupoVO;
import com.dawes.serviciosImpl.ServicioGrupoImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestGrupo {
	@Autowired
	ServicioGrupoImpl sgi;

	@Test
	@Order(1)
	public void test01InsertarGrupo() {
		sgi.save(new GrupoVO(0,"FSB","Hard rock",LocalDate.of(1989, 05, 29), new ArrayList()));
		sgi.save(new GrupoVO(0,"BTR","Hard rock",LocalDate.of(1999, 05, 29), new ArrayList()));
		assertEquals("Karizma", sgi.save(new GrupoVO(0,"Karizma","Pop music",LocalDate.of(2002, 05, 29), new ArrayList())).getNombre());
	}

	// descomentar el try/catch en el método save en ServicioGrupoImpl
//	@Test
//	@Order(2)
//	public void test02InsertarGrupoClaveUnicaDuplicada() {		
//		assertNull(sgi.save(new GrupoVO(0,"Karizma","Pop music",LocalDate.of(2002, 05, 29), new ArrayList())));
//	}

	@Test
	@Order(3)
	public void test03FindByNombre() {
		assertEquals("BTR", sgi.findByNombre("BTR").get().getNombre());
	}

	@Test
	@Order(4)
	public void test04FindById() {
		assertEquals(2, sgi.findById(2).get().getIdgrupo());
	}

	@Test
	@Order(5)
	public void test05FindByNombreInexistente() {
		 assertFalse(sgi.findByNombre("Tangra").isPresent());
	}

	@Test
	@Order(6)
	public void test06FindAll() {
		assertEquals(3, sgi.findAll().size());
	}

	@Test
	@Order(7)
	public void test07ModificarGrupo() {
		GrupoVO grupo = sgi.findByNombre("Karizma").get();
		grupo.setDescripcion("Pop & jazz");
		assertEquals("Pop & jazz", sgi.save(grupo).getDescripcion());
	}
	
	@Test
	@Order(8)
	public void test08EliminarGrupo() {
		Optional<GrupoVO> g = sgi.findByNombre("FSB");
		if (g.isPresent()) {
			GrupoVO grupo = g.get();
			sgi.delete(grupo);
		}
		assertFalse(sgi.findByNombre("FSB").isPresent());
	}

}
