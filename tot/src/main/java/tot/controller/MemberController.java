package tot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

import tot.domain.Member;
import tot.domain.MemberVO;
import tot.service.MemberService;
import tot.util.MemberUtil;

@RestController
@RequestMapping("/api/members") // member or api/member
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/changeNickname")
    public ResponseEntity<String> changeNickname(@RequestBody Map<String, String> request, HttpSession session) {
        String newNickname = request.get("newNickname");
        String memId = (String) session.getAttribute("memId");

        if (memId == null) {
            return ResponseEntity.status(403).body("로그인 상태가 아닙니다.");
        }

        try {
            memberService.updateNickname(memId, newNickname);  // 닉네임만 업데이트
            MemberVO updatedMember = memberService.findMemberByMemId(memId);  // 변경된 회원 정보 조회
            session.setAttribute("member", updatedMember);  // 세션에 저장
            
            return ResponseEntity.ok("닉네임이 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("닉네임 변경 실패: " + e.getMessage());
        }
    }
    
	@GetMapping("/checkLogin")
	public ResponseEntity<Map<String, Boolean>> checkLoginStatus() {
		boolean loggedIn = MemberUtil.isMemberLoggedIn();

		Map<String, Boolean> response = new HashMap<>();
		response.put("loggedIn", loggedIn);

		return ResponseEntity.ok(response);
	}
    
    
}
