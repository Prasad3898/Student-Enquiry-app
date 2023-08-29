package com.prasad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>  {

}
