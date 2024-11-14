package com.jwj.querydsl.repository;

import com.jwj.querydsl.entity.Member;
import com.jwj.querydsl.entity.dto.MemberDTO;
import com.jwj.querydsl.entity.dto.MemberSearchCondition;
import com.jwj.querydsl.entity.dto.MemberTeamDTO;
import com.jwj.querydsl.entity.dto.QMemberTeamDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Optional;

import static com.jwj.querydsl.entity.QMember.member;
import static com.jwj.querydsl.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public void save(Member member) {
		em.persist(member);
	}

	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);
		return Optional.ofNullable(member);
	}

	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	public List<Member> findAll_Querydsl() {
		return queryFactory
				.selectFrom(member)
				.fetch();
	}

	public List<Member> findByUsername(String username) {
		return em.createQuery("select m from Member m where m.username = :username", Member.class)
			.setParameter("username", username)
			.getResultList();
	}

	public List<Member> findByUsername_Querydsl(String username) {
		return queryFactory
				.selectFrom(member)
				.where(member.username.eq(username))
				.fetch();
	}

	// Builder를 사용한 동적 쿼리 작성 1
	public List<MemberTeamDTO> searchByBuilder(MemberSearchCondition condition) {

		BooleanBuilder booleanBuilder = new BooleanBuilder();
		if (StringUtils.hasText(condition.getUsername())) {
			booleanBuilder.and(member.username.eq(condition.getUsername()));
		}

		if (StringUtils.hasText(condition.getTeamName())) {
			booleanBuilder.and(team.name.eq(condition.getTeamName()));
		}

		if (condition.getAgeGoe() != null) {
			booleanBuilder.and(member.age.goe(condition.getAgeGoe()));
		}

		if (condition.getAgeLoe() != null) {
			booleanBuilder.and(member.age.loe(condition.getAgeLoe()));
		}

		return queryFactory
				.select(new QMemberTeamDTO(
						member.id.as("memberId"),
						member.username,
						member.age,
						team.id.as("teamId"),
						team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(booleanBuilder)
				.fetch();
	}

	// Where 파라미터를 사용한 동적 쿼리 작성 2
	public List<MemberTeamDTO> searchByWhere(MemberSearchCondition condition) {
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
