package com.example.younghanapi1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Child {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;

	public void addParent(Parent parent) {
		parent.getChildren().add(this);
		this.setParent(parent);
	}
}
