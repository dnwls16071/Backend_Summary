package com.example.younghanapi1.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

	@NotEmpty(message = "이름은 필수 입력 값입니다.")
	private String name;

	private String city;
	private String street;
	private String zipcode;
}
