package com.jwj.querydsl_2nd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	private String username;
	private int age;

	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		this.team = team;
	}

	public Member(String username, int age) {
		this.username = username;
		this.age = age;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
}
