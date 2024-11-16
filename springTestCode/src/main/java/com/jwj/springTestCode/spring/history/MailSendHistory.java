package com.jwj.springTestCode.spring.history;

import com.jwj.springTestCode.spring.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MailSendHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fromEmail;
	private String toEmail;
	private String subject;
	private String content;

	@Builder
	private MailSendHistory(String fromEmail, String toEmail, String subject, String content) {
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.subject = subject;
		this.content = content;
	}
}
