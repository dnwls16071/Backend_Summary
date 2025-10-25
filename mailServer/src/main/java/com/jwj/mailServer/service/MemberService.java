package com.jwj.mailServer.service;

import com.jwj.mailServer.controller.dto.EmailVerificationResponse;
import com.jwj.mailServer.domain.Member;
import com.jwj.mailServer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private static final String AUTH_CODE_PREFIX = "AuthCode ";
	private final MemberRepository memberRepository;
	private final EmailService emailService;
	private final RedisService redisService;

	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	public void sendCodeToEmail(String toEmail) {
		this.checkDuplicatedEmail(toEmail);
		String title = "Aniwhere 이메일 인증 번호";
		String code = this.createCode();
		emailService.sendEmail(toEmail, title, code);
		redisService.setValue(AUTH_CODE_PREFIX + toEmail, code, Duration.ofMillis(this.authCodeExpirationMillis));
	}

	// 회원가입 시 해당 메일이 존재하는지를 확인하는 메서드
	private void checkDuplicatedEmail(String email) {
		Optional<Member> member = memberRepository.findByEmail(email);
		if (member.isPresent()) {
			log.debug("해당 메일은 이미 사용 중인 메일입니다!");
			throw new RuntimeException();
		}
	}

	// 회원가입 시 인증 메일을 보낼 때 인증 코드를 발송할 수 있도록 하는 메서드
	private String createCode() {
		int length = 6;
		try {
			Random random = SecureRandom.getInstanceStrong();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < length; i++) {
				builder.append(random.nextInt(10));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			log.debug("MemberService.createCode() exception occur");
			throw new RuntimeException();
		}
	}

	// 인증 코드 검증
	public EmailVerificationResponse verifiedCode(String email, String code) {
		this.checkDuplicatedEmail(email);
		String key = AUTH_CODE_PREFIX + email;

		// 키가 존재하는지 확인
		if (!redisService.hasKey(key)) {
			return new EmailVerificationResponse("인증 시간이 만료되었거나 인증번호가 존재하지 않습니다.", false);
		}

		// Redis에서 저장된 인증 코드 가져오기
		String savedCode = redisService.getValue(key);

		// 입력받은 코드와 저장된 코드 비교
		if (savedCode.equals(code)) {
			redisService.deleteValue(key);  // 인증 성공 시 코드 삭제
			return new EmailVerificationResponse("인증 성공!", true);
		}

		return new EmailVerificationResponse("인증번호가 일치하지 않습니다.", false);
	}
}
