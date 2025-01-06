package com.jwj.querydsl_2nd.repository;

import com.jwj.querydsl_2nd.dto.MemberTeamDto;
import com.jwj.querydsl_2nd.util.MemberSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

	Page<MemberTeamDto> searchPageSimple(MemberSearchCond memberSearchCond, Pageable pageable);
	Page<MemberTeamDto> searchPageComplex(MemberSearchCond memberSearchCond, Pageable pageable);
}
