package com.dawes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawes.modelo.GrupoVO;

public interface GrupoRepository extends JpaRepository<GrupoVO, Integer> {
	Optional<GrupoVO> findById(int id);
	Optional<GrupoVO> findByNombre(String nombre);
}
