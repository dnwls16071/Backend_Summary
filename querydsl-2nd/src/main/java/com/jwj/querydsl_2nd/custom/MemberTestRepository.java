package com.jwj.querydsl_2nd.custom;

import com.jwj.querydsl_2nd.entity.Member;
import com.jwj.querydsl_2nd.util.MemberSearchCond;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jwj.querydsl_2nd.entity.QMember.member;
import static com.jwj.querydsl_2nd.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;
QuerydslRepositorySupport
@Repository
public class MemberTestRepository extends Querydsl4RepositorySupport {

	// Member 클래스 타입
	public MemberTestRepository() {
		super(Member.class);
	}

	public List<Member> basicSelect() {
		return select(member)
				.from(member)
				.fetch();
	}

	public List<Member> basicSelectFrom() {
		return selectFrom(member)
				.fetch();
	}

	public Page<Member> searchPageByApplyPage(MemberSearchCond memberSearchCond, Pageable pageable) {
		JPAQuery<Member> query = selectFrom(member)
				.where(usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe())
				);

		List<Member> content = getQuerydsl().applyPagination(pageable, query)
				.fetch();

		return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
	}

	public Page<Member> applyPagination(MemberSearchCond memberSearchCond, Pageable pageable) {
		return applyPagination(pageable, query -> query
				.selectFrom(member)
				.where(usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe()))
				);
	}

	public Page<Member> applyPagination2(MemberSearchCond memberSearchCond, Pageable pageable) {
		return applyPagination(pageable, query -> query
				.selectFrom(member)
				.leftJoin(member.team, team)
				.where(usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe())
				), countQuery -> countQuery
				.select(member.id)
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe()))
		);
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
