package com.prasad.service;

import com.prasad.binding.LoginForm;
import com.prasad.binding.SignUpForm;
import com.prasad.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(String  email);
}
