package com.jwj.querydsl_2nd.repository;

import com.jwj.querydsl_2nd.dto.MemberTeamDto;
import com.jwj.querydsl_2nd.dto.QMemberTeamDto;
import com.jwj.querydsl_2nd.entity.Member;
import com.jwj.querydsl_2nd.util.MemberSearchCond;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jwj.querydsl_2nd.entity.QMember.member;
import static com.jwj.querydsl_2nd.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<MemberTeamDto> searchPageSimple(MemberSearchCond memberSearchCond, Pageable pageable) {
		QueryResults<MemberTeamDto> results = queryFactory
				.select(new QMemberTeamDto(member.id.as("member_id"),
						member.username,
						member.age,
						member.team.id.as("team_id"),
						member.team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(
						usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<MemberTeamDto> result = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(result, pageable, total);
	}

	@Override
	public Page<MemberTeamDto> searchPageComplex(MemberSearchCond memberSearchCond, Pageable pageable) {
		List<MemberTeamDto> results = queryFactory
				.select(new QMemberTeamDto(member.id.as("member_id"),
						member.username,
						member.age,
						member.team.id.as("team_id"),
						member.team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(
						usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		// Count Query 최적화
		JPAQuery<Member> countQuery = queryFactory
				.selectFrom(member)
				.leftJoin(member.team, team)
				.where(
						usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe())
				);

		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
	}

	private Predicate ageLoe(Integer ageLoe) {
		return ageLoe != null ? member.age.loe(ageLoe) : null;
	}

	private Predicate ageGoe(Integer ageGoe) {
		return ageGoe != null ? member.age.goe(ageGoe) : null;
	}

	private Predicate teamNameEq(String teamName) {
		return hasText(teamName) ? team.name.eq(teamName) : null;
	}

	private Predicate usernameEq(String username) {
		return hasText(username) ? member.username.eq(username) : null;
	}
}
