package com.example.younghanapi2.api;

import com.example.younghanapi2.domain.Member;
import com.example.younghanapi2.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

	private final MemberService memberService;

	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		Member member = new Member();
		member.setName(request.getName());

		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(@PathVariable(name = "id") Long id,
											   @RequestBody @Valid UpdateMemberRequest request) {
		memberService.update(id, request.getName());

		Member findMember = memberService.findOne(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName());
	}

	@GetMapping("/api/v1/members")
	public List<Member> membersV1() {
		return memberService.findMembers();
	}

	@GetMapping("/api/v2/members")
	public Result membersV2() {
		List<Member> members = memberService.findMembers();
		List<MemberDto> list = members.stream().map(member -> new MemberDto(member.getName())).toList();
		return new Result(list);
	}

	@Data
	@AllArgsConstructor
	static class MemberDto {

		private String name;
	}

	@Data
	@AllArgsConstructor
	static class Result<T> {

		private T data;
	}

	@Data
	static class UpdateMemberRequest {

		private String name;
	}

	@Data
	@AllArgsConstructor
	static class UpdateMemberResponse {

		private Long id;
		private String name;
	}

	@Data
	static class CreateMemberRequest {

		@NotEmpty
		private String name;
	}

	@Data
	@AllArgsConstructor
	static class CreateMemberResponse {

		private Long id;
	}
}
