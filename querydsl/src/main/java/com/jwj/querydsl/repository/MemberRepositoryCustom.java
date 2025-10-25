package com.jwj.querydsl.repository;

import com.jwj.querydsl.entity.dto.MemberSearchCondition;
import com.jwj.querydsl.entity.dto.MemberTeamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepositoryCustom {

	List<MemberTeamDTO> search(MemberSearchCondition condition);
	Page<MemberTeamDTO> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
	Page<MemberTeamDTO> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
