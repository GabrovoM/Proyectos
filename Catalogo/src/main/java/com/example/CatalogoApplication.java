package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domain.contracts.repository.ActorRepository;
import com.example.domain.contracts.service.ActorService;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	private ActorRepository dao;	
	
	@Autowired
	private ActorService srv;	
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Applicación arrancada");
		
		var item = dao.findById(206);
		System.err.println("Actor: "+item);
	}

}
