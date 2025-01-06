package com.jwj.querydsl_2nd.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private Long id;

	private String name;

	public Team(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();
}
