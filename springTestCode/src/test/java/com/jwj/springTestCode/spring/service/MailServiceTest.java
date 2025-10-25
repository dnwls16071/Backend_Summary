package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.client.MailClient;
import com.jwj.springTestCode.spring.history.MailSendHistory;
import com.jwj.springTestCode.spring.repository.MailSendRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class MailServiceTest {

	@Mock private MailClient mailClient;					// @Mock : 가짜 객체 생성
	@Mock private MailSendRepository mailSendRepository; 	// @Mock : 가짜 객체 생성
	@InjectMocks private MailService mailService;			// @InjectMocks : 가짜 Mock 객체들을 자동으로 주입해주는 역할

	@DisplayName("메일 전송 테스트")
	@Test
	void sendMail() {
		// given
		when(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
				.thenReturn(true);

		// when
		boolean result = mailService.sendEmail("", "", "", "");
		Mockito.verify(mailSendRepository, times(1)).save(any(MailSendHistory.class));

		// when & then
		assertThat(result).isTrue();
	}

	@DisplayName("메일 전송 실패 테스트")
	@Test
	void sendMailFail() {
		// given
		when(mailClient.sendEmail(any(), any(), any(), any()))
				.thenThrow(new IllegalArgumentException("메일 전송"));

		// when & then
		assertThatThrownBy(() -> mailService.sendEmail("", "", "", ""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("메일 전송");
	}
}