package com.prasad.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prasad.binding.LoginForm;
import com.prasad.binding.SignUpForm;
import com.prasad.binding.UnlockForm;
import com.prasad.service.impl.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userImpl;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String Login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

		String status = userImpl.login(loginForm);
		if (status.contains("SUCCESS")) {
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@Validated @ModelAttribute("user") SignUpForm user,BindingResult result, Model model) {
		
		if(result.hasErrors())
			return "signup";
		
		boolean status = userImpl.signUp(user);

		if (status) {
			model.addAttribute("succMsg", "Account Created,Check your email");
		} else {
			model.addAttribute("errMsg", "Invalid Credentials");
		}
		
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlock", unlockForm);
		return "unlock";
	}

	@PostMapping("/unlock")
	public String UnlockAcc(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userImpl.unlockAccount(unlock);
			if (status) {
				model.addAttribute("succMsg", "Account Unlocked Successful");
			} else {
				model.addAttribute("errMsg", "TempPwd is incorrect,Check your email");
			}
		} else {
			model.addAttribute("errMsg", "New pwd and Confirm pwd or not same");
		}
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}

	@PostMapping("/forgot")
	public String forgotPwd(@RequestParam("email") String email, Model model) {

		boolean status = userImpl.forgotPwd(email);
		if (status) {
			model.addAttribute("succMsg", "password send your email,check email");
		} else {
			model.addAttribute("errMsg", "Invalid email");
		}

		return "forgotPwd";
	}
}