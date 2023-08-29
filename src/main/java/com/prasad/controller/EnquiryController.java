package com.prasad.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prasad.binding.DashboardResponse;
import com.prasad.binding.EnquiryForm;
import com.prasad.binding.EnquirySearchCriteria;
import com.prasad.entity.StudentEnqEntity;
import com.prasad.repository.StudentEnqRepo;
import com.prasad.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private StudentEnqRepo stuRepo;

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");
		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		System.out.println(formObj);
		
		if(session.getAttribute("enqid")!=null)
		{
		   enqService.updateEnq((Integer)session.getAttribute("enqid"), formObj);
		    session.removeAttribute("enqid");
		    model.addAttribute("succMsg", "Enquery Updated");
		}
		else {
			boolean status = enqService.saveEnquiry(formObj);
			if (status) {
				model.addAttribute("succMsg", "Enquery Added");
			} else {
				model.addAttribute("errMsg", "Problem Occured");
			}
		}
		return "add-enquiry";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		List<String> courses = enqService.getCourse();
		List<String> enqStatus = enqService.getEnqStatus();

		EnquiryForm formObj = new EnquiryForm();
		model.addAttribute("course", courses);
		model.addAttribute("status", enqStatus);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}

	private void initForm(Model model) {
		EnquiryForm formObj = new EnquiryForm();
		model.addAttribute("course", enqService.getCourse());
		model.addAttribute("status", enqService.getEnqStatus());
		model.addAttribute("formObj", formObj);
	}

	@GetMapping("/enquires")
	public String viewsEnquriesPage(Model model) {
		initForm(model);
		List<StudentEnqEntity> enquries = enqService.getEnquries();
		model.addAttribute("enquries", enquries);
		return "view-enquiries";
	}

	@GetMapping("/filter-enquiries")
	public String getFilterEnq(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourse(cname);
		criteria.setClassMode(mode);
		criteria.setStatus(status);
		System.out.println(criteria);

		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnqEntity> filteredEnq = enqService.getFilteredEnq(criteria, userId);
		model.addAttribute("enquiries", filteredEnq);
		return "view-enquiries-page";
	}
	
	@GetMapping("/enqu")
	public String enquiry(Model model)
	{
		initForm(model);
		EnquiryForm enqForm=new EnquiryForm();
		if(session.getAttribute("enq")!=null)
		{
			StudentEnqEntity enq = (StudentEnqEntity)session.getAttribute("enq");
		   BeanUtils.copyProperties(enq, enqForm);
		   session.removeAttribute("enq");
		}
		model.addAttribute("formObj", enqForm);
		return "add-enquiry";
	}

	@GetMapping("/edit/{id}")
	public String editEnquiry(@PathVariable("id") Integer id) {
		 StudentEnqEntity enq= enqService.getEnq(id);
		session.setAttribute("enq", enq);
		session.setAttribute("enqid", id);
		return "redirect:/enqu";
	}

	@GetMapping("/delete/{id}")
	public String deleteData(@PathVariable("id")Integer id) {
		stuRepo.deleteById(id);
		return "redirect:/enquires";
	}
}
