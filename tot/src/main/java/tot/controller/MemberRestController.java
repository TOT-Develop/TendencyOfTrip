package tot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tot.domain.MemberVO;
import tot.service.MemberService;
import tot.util.MemberUtil;
import tot.util.ResponseUtil;

@RestController
@RequestMapping("/member")
public class MemberRestController {

	private final MemberService memberService;

	public MemberRestController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/getId") 
	public String getMemberId() {
		MemberVO member = MemberUtil.isAuthenticatedMember();
		return member.getMemId();
	}
	
	@GetMapping("/checkLogin")
	public ResponseEntity<Map<String, Boolean>> checkLoginStatus() {
		boolean loggedIn = MemberUtil.isAutenticatedMemberLoggedIn();

		Map<String, Boolean> response = new HashMap<>();
		response.put("loggedIn", loggedIn);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/changeNickname")
	public ResponseEntity<Map<String, String>> changeNickname(@RequestBody Map<String, String> request,
			HttpSession session) {
		String newNickname = request.get("newNickname");
		MemberVO member = MemberUtil.validateDuplicateNickname(newNickname);

		memberService.updateNickname(member.getMemId(), newNickname);
		MemberVO updatedMember = memberService.findMemberByMemId(member.getMemId());
		MemberUtil.updateMemberInfo(updatedMember);

		return ResponseUtil.createTReviewResponse("닉네임이 변경되었습니다.");
	}
}
