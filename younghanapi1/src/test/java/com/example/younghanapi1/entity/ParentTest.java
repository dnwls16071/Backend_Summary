package com.example.younghanapi1.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ParentTest {

	@PersistenceContext
	EntityManager em;

	@Test
	@Commit
	void test() {
		Parent parent = new Parent();

		Child child1 = new Child();
		Child child2 = new Child();

		parent.addChild(child1);
		parent.addChild(child2);

		em.persist(parent);
		em.flush();
		em.clear();
	}
}