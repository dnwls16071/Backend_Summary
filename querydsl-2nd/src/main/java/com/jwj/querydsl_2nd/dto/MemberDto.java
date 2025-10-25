package com.jwj.querydsl_2nd.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {

	private String username;
	private int age;

	@QueryProjection
	public MemberDto(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public MemberDto() {
	}
}
