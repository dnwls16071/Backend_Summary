package com.jwj.mailServer.controller;

import com.jwj.mailServer.controller.dto.EmailVerificationResponse;
import com.jwj.mailServer.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
		private final MemberService memberService;

		// 메일 전송 컨트롤러
		@PostMapping("/emails/verifications-requests")
		public ResponseEntity sendMessage(@RequestParam(name = "email") @Valid String email) {
			memberService.sendCodeToEmail(email);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		// 메일 검증 컨트롤러
		@GetMapping("/emails/verifications")
		public ResponseEntity<EmailVerificationResponse> verificationEmail(@RequestParam(name = "email") String email,
																		   @RequestParam(name = "code") String code) {
			EmailVerificationResponse emailVerificationResponse = memberService.verifiedCode(email, code);
			return ResponseEntity.ok(emailVerificationResponse);
		}
	}
