package com.jwj.springdatajpa.repository;

import com.jwj.springdatajpa.dto.MemberDto;
import com.jwj.springdatajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("select m from Member m where m.name = :name and m.age = :age")
	List<Member> findUser(@Param(value = "username") String username, @Param(value = "age") int age);

	@Query("select new com.jwj.springdatajpa.dto.MemberDto(m.id, m.name, m.age) from Member m join m.team t")
	List<MemberDto> findUserDto(String name, String age);

	Page<Member> findByAge(int age, Pageable pageable);

	@Modifying(clearAutomatically = true)	// 영속성 컨텍스트 초기화 자동 설정
	@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
	int bulkAgePlus(@Param(value = "age") int age);

	@Query("select m from Member m join fetch m.team")
	List<Member> findMemberFetchJoin();

	@EntityGraph(attributePaths = "team")
	@Query("select m from Member m")
	List<Member> findMemberEntityGraph();
}
