package com.prasad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {
	
	public UserDtlsEntity findByEmail(String email);
	
	public UserDtlsEntity findByEmailAndPwd(String email, String pwd);
	
}