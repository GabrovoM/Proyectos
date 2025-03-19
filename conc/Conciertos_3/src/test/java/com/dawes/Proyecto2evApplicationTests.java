package com.dawes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import com.dawes.serviciosImpl.ServicioSalaImpl;
import com.dawes.serviciosImpl.ServicioVentaImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class Proyecto2evApplicationTests {	
	

}
