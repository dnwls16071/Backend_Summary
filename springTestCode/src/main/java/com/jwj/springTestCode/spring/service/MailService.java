package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.client.MailClient;
import com.jwj.springTestCode.spring.history.MailSendHistory;
import com.jwj.springTestCode.spring.repository.MailSendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

	private final MailClient mailClient;
	private final MailSendRepository mailSendRepository;

	public boolean sendEmail(String fromEmail, String toEmail, String subject, String content) {

		boolean b = mailClient.sendEmail(fromEmail, toEmail, subject, content);
		if (b) {
			mailSendRepository.save(MailSendHistory.builder()
					.fromEmail(fromEmail)
					.toEmail(toEmail)
					.subject(subject)
					.content(content)
					.build());
			return true;
		}
		return false;
	}
}
