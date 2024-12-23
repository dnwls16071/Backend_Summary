package com.jwj.mysql_performance_optimization.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;
}
