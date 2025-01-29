package com.jwj.mvc2.upload.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
public class Item {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ITEM_ID")
	private Long id;
	private String itemName;

	@OneToOne(cascade = CascadeType.PERSIST)
	private AttachFile attachFile;

	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
	private List<ImageFile> imageFiles = new ArrayList<>();
}