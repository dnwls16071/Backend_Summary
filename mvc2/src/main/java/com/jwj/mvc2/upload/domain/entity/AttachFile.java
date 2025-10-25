package com.jwj.mvc2.upload.domain.entity;

import com.jwj.mvc2.upload.domain.dto.UploadFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class AttachFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATTACHFILE_ID")
	private Long id;

	private String uploadFileName;
	private String storeFileName;

	public AttachFile(UploadFile uploadFile) {
		this.uploadFileName = uploadFile.getUploadFileName();
		this.storeFileName = uploadFile.getStoreFileName();
	}
}
