package com.jwj.springdatajpa.controller;

import com.jwj.springdatajpa.entity.Member;
import com.jwj.springdatajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;

	// 식별자를 통한 코드
	@GetMapping("/member1/{id}")
	public String findMember1(@PathVariable(name = "id") Long id) {
		Member member = memberRepository.findById(id).get();
		return member.getName();
	}

	// 도메인 클래스 컨버터 적용 코드
	@GetMapping("/member2/{id}")
	public String findMember2(@PathVariable(name = "id") Member member) {
		return member.getName();
    }
}
