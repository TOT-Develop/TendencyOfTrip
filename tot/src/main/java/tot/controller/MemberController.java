package tot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

import tot.domain.MemberVO;
import tot.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/changeNickname")
    public ResponseEntity<String> changeNickname(@RequestBody Map<String, String> request, HttpSession session) {
        String newNickname = request.get("newNickname");
        System.out.println("Received newNickname: " + newNickname);
        String memId = (String) session.getAttribute("memId");
        System.out.println("Received memId: " + memId);
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
}
