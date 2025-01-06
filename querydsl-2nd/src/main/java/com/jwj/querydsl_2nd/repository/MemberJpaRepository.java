package com.jwj.querydsl_2nd.repository;

import com.jwj.querydsl_2nd.dto.MemberTeamDto;
import com.jwj.querydsl_2nd.dto.QMemberTeamDto;
import com.jwj.querydsl_2nd.entity.Member;
import com.jwj.querydsl_2nd.util.MemberSearchCond;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.jwj.querydsl_2nd.entity.QMember.member;
import static com.jwj.querydsl_2nd.entity.QTeam.team;
import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.springframework.util.StringUtils.hasText;

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
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
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

	public List<MemberTeamDto> searchByBuilder(MemberSearchCond memberSearchCond) {

		BooleanBuilder builder = new BooleanBuilder();
		if (hasText(memberSearchCond.getUsername())) {
			builder.and(member.username.eq(memberSearchCond.getUsername()));
		}

		if (hasText(memberSearchCond.getTeamName())) {
			builder.and(team.name.eq(memberSearchCond.getTeamName()));
		}

		if (memberSearchCond.getAgeGoe() != null) {
			builder.and(member.age.goe(memberSearchCond.getAgeGoe()));
		}

		if (memberSearchCond.getAgeLoe()!= null) {
            builder.and(member.age.loe(memberSearchCond.getAgeLoe()));
        }

		return queryFactory
				.select(new QMemberTeamDto(
						member.id,
						member.username,
						member.age,
						team.id.as("team_id"),
						team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.fetch();
	}

	public List<MemberTeamDto> search(MemberSearchCond memberSearchCond) {
		return queryFactory
				.select(new QMemberTeamDto(
						member.id,
						member.username,
						member.age,
						team.id.as("team_id"),
						team.name.as("teamName")))
				.from(member)
				.leftJoin(member.team, team)
				.where(
						usernameEq(memberSearchCond.getUsername()),
						teamNameEq(memberSearchCond.getTeamName()),
						ageGoe(memberSearchCond.getAgeGoe()),
						ageLoe(memberSearchCond.getAgeLoe())
				)
                .fetch();
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
