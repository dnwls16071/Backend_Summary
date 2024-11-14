package com.jwj.querydsl.repository;

import com.jwj.querydsl.entity.Member;
import com.jwj.querydsl.entity.dto.MemberSearchCondition;
import com.jwj.querydsl.entity.dto.MemberTeamDTO;
import com.jwj.querydsl.entity.dto.QMemberTeamDTO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jwj.querydsl.entity.QMember.member;
import static com.jwj.querydsl.entity.QTeam.team;

@Repository
public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {

	public MemberRepositoryImpl() {
		super(Member.class);
	}

	@Override
	public List<MemberTeamDTO> search(MemberSearchCondition condition) {

		return queryFactory
				.select(new QMemberTeamDTO(
						member.id.as("memberId"),
						member.username,
						member.age,
						team.id.as("teamId"),
						team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()))
				.fetch();
	}

	@Override
	public Page<MemberTeamDTO> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
		QueryResults<MemberTeamDTO> results = queryFactory
				.select(new QMemberTeamDTO(
						member.id.as("memberId"),
						member.username,
						member.age,
						team.id.as("teamId"),
						team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		List<MemberTeamDTO> contents = results.getResults();

		long total = results.getTotal();
		return new PageImpl<>(contents, pageable, total);
	}

	// 데이터와 전체 데이터 수를 분리해서 가져오는 방법
	/*
	 * 분리하는 이유
	   * 스프링 데이터 페이징 활용 : CountQuery 최적화
	 */
	@Override
	public Page<MemberTeamDTO> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
		List<MemberTeamDTO> results = queryFactory
				.select(new QMemberTeamDTO(
						member.id.as("memberId"),
						member.username,
						member.age,
						team.id.as("teamId"),
						team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Member> countQuery = queryFactory
				.select(member)
				.from(member)
				.leftJoin(member.team, team)
				.where(
						usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe())
				);

		// 함수로 작성
		// results와 pageable의 전체 사이즈를 보고 카운트 쿼리 호출 여부를 결정
		return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetchCount());
	}

	private BooleanExpression usernameEq(String username) {
		return StringUtils.hasText(username) ? member.username.eq(username) : null;
	}

	private BooleanExpression teamNameEq(String teamName) {
		return StringUtils.hasText(teamName) ? team.name.eq(teamName) : null;
	}

	private BooleanExpression ageGoe(Integer ageGoe) {
		return ageGoe != null ? member.age.goe(ageGoe) : null;
	}

	private BooleanExpression ageLoe(Integer ageLoe) {
		return ageLoe != null ? member.age.loe(ageLoe) : null;
	}
}
