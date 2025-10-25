package com.jwj.querydsl.init;

import com.jwj.querydsl.entity.Member;
import com.jwj.querydsl.entity.Team;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

	private final InitMemberService initMemberService;

	@PostConstruct
	public void init() {
		initMemberService.init();
	}

	@Component
	static class InitMemberService {

		@PersistenceContext
		private EntityManager em;

		@Transactional
		public void init() {
			Team teamA = new Team("teamA");
			Team teamB = new Team("teamB");

			em.persist(teamA);
			em.persist(teamB);

			for (int i = 0; i < 100; i++) {
				Team findTeam = i % 2 == 0 ? teamA : teamB;
				em.persist(new Member("member" + i, i, findTeam));
			}
		}
	}
}
