package com.jwj.querydsl.controller;

import com.jwj.querydsl.entity.dto.MemberSearchCondition;
import com.jwj.querydsl.entity.dto.MemberTeamDTO;
import com.jwj.querydsl.repository.MemberJpaRepository;
import com.jwj.querydsl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberJpaRepository memberJpaRepository;
	private final MemberRepository memberRepository;

	@GetMapping("/v1/members")
	public List<MemberTeamDTO> searchMemberV1(MemberSearchCondition condition) {
		return memberJpaRepository.searchByWhere(condition);
	}

	@GetMapping("/v2/members")
	public Page<MemberTeamDTO> searchMemberV2(MemberSearchCondition condition, Pageable pageable) {
		return memberRepository.searchPageSimple(condition, pageable);
	}

	@GetMapping("/v3/members")
	public Page<MemberTeamDTO> searchMemberV3(MemberSearchCondition condition, Pageable pageable) {
		return memberRepository.searchPageComplex(condition, pageable);
	}
}
