package com.prasad.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="AIT_STUDENT_ENQURIES")
public class StudentEnqEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;  
	private String name;
	private Long phno;
	private String classMode;
	private String course;
	private String status;
	
	@CreationTimestamp
	private LocalDate creadtedDate;
	
	@UpdateTimestamp
	private LocalDate startedDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDtlsEntity user;

	public Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
