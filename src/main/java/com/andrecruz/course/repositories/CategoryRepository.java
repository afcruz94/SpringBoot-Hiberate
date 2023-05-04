package com.andrecruz.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrecruz.course.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
