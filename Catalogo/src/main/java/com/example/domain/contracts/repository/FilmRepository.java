package com.example.domain.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {

}
