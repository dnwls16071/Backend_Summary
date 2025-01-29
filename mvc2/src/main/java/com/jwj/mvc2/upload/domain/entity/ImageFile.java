package com.jwj.mvc2.upload.domain.entity;

import com.jwj.mvc2.upload.domain.dto.UploadFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMAGEFILE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item item;

	private String uploadFileName;
	private String storeFileName;

	public ImageFile(UploadFile uploadFile) {
		this.uploadFileName = uploadFile.getUploadFileName();
		this.storeFileName = uploadFile.getStoreFileName();
	}
}
