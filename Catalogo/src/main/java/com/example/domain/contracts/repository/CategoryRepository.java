package com.example.domain.contracts.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domain.core.contracts.repositories.RepositoryWithProjections;
import com.example.domain.entities.Actor;
import com.example.domain.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Date fecha);
}
