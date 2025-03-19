package com.dawes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawes.modelo.SalaVO;

public interface SalaRepository extends JpaRepository<SalaVO, Integer> {
	Optional<SalaVO> findByNombre(String nombre);
}
