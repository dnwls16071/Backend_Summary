package com.jwj.querydsl_2nd.util;

import lombok.Data;

@Data
public class MemberSearchCond {

	private String username;
	private String teamName;
	private Integer ageGoe;
	private Integer ageLoe;
}
