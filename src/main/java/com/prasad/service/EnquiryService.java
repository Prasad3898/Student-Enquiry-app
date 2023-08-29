package com.prasad.service;

import java.util.List;

import com.prasad.binding.DashboardResponse;
import com.prasad.binding.EnquiryForm;
import com.prasad.binding.EnquirySearchCriteria;
import com.prasad.entity.StudentEnqEntity;

public interface EnquiryService {
	
	public DashboardResponse getDashboardData(Integer userId);

	public List<String> getCourse();
	
	public List<String> getEnqStatus();

	public boolean saveEnquiry(EnquiryForm form);

	List<StudentEnqEntity> getEnquries();
	
	public List<StudentEnqEntity> getFilteredEnq(EnquirySearchCriteria criteria,Integer userId );

	public StudentEnqEntity getEnq(Integer id);
	
	public String updateEnq(Integer enqid,EnquiryForm formObj);


}