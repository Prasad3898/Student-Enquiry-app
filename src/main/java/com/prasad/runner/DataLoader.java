package com.prasad.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.prasad.entity.CourseEntity;
import com.prasad.entity.EnqStatusEntity;
import com.prasad.repository.CourseRepo;
import com.prasad.repository.EnqStatusRepo;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Component
public class DataLoader  implements ApplicationRunner{

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		courseRepo.deleteAll();
		CourseEntity c1=new CourseEntity();
		c1.setCourseName("Java");
		
		CourseEntity c2=new CourseEntity();
		c2.setCourseName("Python");
		
		CourseEntity c3=new CourseEntity();
		c3.setCourseName("Devops");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		statusRepo.deleteAll();
		EnqStatusEntity e1=new EnqStatusEntity();
		e1.setStatusName("New");
		
		EnqStatusEntity e2=new EnqStatusEntity();
		e2.setStatusName("Enrolled");
		
		EnqStatusEntity e3=new EnqStatusEntity();
		e3.setStatusName("Lost");
		
		statusRepo.saveAll(Arrays.asList(e1,e2,e3));
	}
}
