package com.jwj.springdatajpa.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public class JpaBaseEntity {

	@Column(updatable = false)
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	@PrePersist
	public void prePersist() {
        this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
    }

	@PreUpdate
	public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
