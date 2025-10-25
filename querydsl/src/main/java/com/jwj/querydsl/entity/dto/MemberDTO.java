package com.jwj.querydsl.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDTO {
	private String username;
	private int age;

	public MemberDTO() {

	}

	@QueryProjection
	public MemberDTO(String username, int age) {
		this.username = username;
		this.age = age;
	}
}
