package com.jwj.mailServer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;

	public void sendEmail(String toEmail, String title, String text) {
		SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
		try {
			emailSender.send(emailForm);
		} catch (RuntimeException e) {
			log.error("MailService.sendEmail exception occur toEmail: {}, " +
							"title: {}, text: {}, error: {}",
					toEmail, title, text, e.getMessage(), e);  // 원본 예외 정보를 포함
			throw new RuntimeException("메일 전송 실패함", e);  // 원본 예외를 cause로 포함
		}
	}

	private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setSubject(title);
		simpleMailMessage.setText(text);
		return simpleMailMessage;
	}
}
