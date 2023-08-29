package com.prasad.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prasad.binding.DashboardResponse;
import com.prasad.binding.EnquiryForm;
import com.prasad.binding.EnquirySearchCriteria;
import com.prasad.entity.CourseEntity;
import com.prasad.entity.EnqStatusEntity;
import com.prasad.entity.StudentEnqEntity;
import com.prasad.entity.UserDtlsEntity;
import com.prasad.repository.CourseRepo;
import com.prasad.repository.EnqStatusRepo;
import com.prasad.repository.StudentEnqRepo;
import com.prasad.repository.UserDtlsRepo;
import com.prasad.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private HttpSession session;

	@Override
	public List<String> getCourse() {

		List<CourseEntity> findAll = courseRepo.findAll();
		List<String> names = new ArrayList<>();
		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {

		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList<>();
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());
		}
		return statusList;
	}

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

			Integer total = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getStatus().equals("Lost")).collect(Collectors.toList())
					.size();

			response.setTotalEnquriesCnt(total);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
		}
		return response;
	}

	@Override
	public List<StudentEnqEntity> getEnquries() {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}

		return null;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, entity);

		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlsEntity userEntity = userRepo.findById(userId).get();
		
		entity.setUser(userEntity);
		enqRepo.save(entity);
		return true;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnq(EnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

			// filter logic
			if (null != criteria.getCourse() && !"".equals(criteria.getCourse())) {
				enquiries = enquiries.stream().filter(e -> e.getCourse().equals(criteria.getCourse()))
						.collect(Collectors.toList());
			}
			
			if (null != criteria.getStatus() && !"".equals(criteria.getStatus())) {
				enquiries = enquiries.stream().filter(e -> e.getStatus().equals(criteria.getStatus()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}
			return enquiries;
		}

		return null;
	}
	
	public StudentEnqEntity getEnq(Integer enqId)
	{
		Optional<StudentEnqEntity> enq = enqRepo.findById(enqId);
	     return enq.get();
	}

	@Override
	public String updateEnq(Integer enqid, EnquiryForm formObj) {
		
		Optional<StudentEnqEntity> enq = enqRepo.findById(enqid);
		if(enq.isPresent())
		{
			StudentEnqEntity enqEntity = enq.get();
			enqEntity.setName(formObj.getName());
			enqEntity.setPhno(formObj.getPhno());
			enqEntity.setClassMode(formObj.getClassMode());
			enqEntity.setCourse(formObj.getCourse());
			enqEntity.setStatus(formObj.getStatus());
			
			enqRepo.save(enqEntity);
			return "Updated";
		}
		
		return "Faild";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}