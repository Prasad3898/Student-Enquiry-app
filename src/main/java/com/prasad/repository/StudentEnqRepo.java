package com.prasad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
