package com.example.younghanapi1.service;

import com.example.younghanapi1.entity.Member;
import com.example.younghanapi1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor	// final 키워드가 붙은 것으로 생성자 주입
public class MemberService {

	private final MemberRepository memberRepository;

	// 회원가입
	// EntityManager.persist() 시점에 영속성 컨텍스트에 올라가면서 PK가 부여됨
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> members = memberRepository.findByName(member.getName());
		if (!members.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다");
		}
	}

	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	// 회원 단일 건 조회
	public Member findOne(Long memberId) {
		return memberRepository.findByOne(memberId);
	}
}
