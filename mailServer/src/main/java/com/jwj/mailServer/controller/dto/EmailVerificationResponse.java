package com.jwj.mailServer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailVerificationResponse {
	private String message;
	private boolean isVeirified;
}
