package com.prasad.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpForm {
	
	@NotBlank(message="Name is mandatory")
	private String name;
	
	@NotBlank(message="Email is mandatory")
	private String email;
	
	@NotNull(message="MobNo is mandatory")
	private Long phno; 

}
