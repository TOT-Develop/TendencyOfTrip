package tot.admin.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.mail.Flags;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tot.common.enums.Flag;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemBanHistoryDTO;
import tot.domain.MemberVO;
import tot.service.EmailService;
import tot.service.MemBanHistoryService;
import tot.service.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminContoller {
	
	 @Autowired
	 private MemberService memberService;
	 
	 @Autowired
	 private EmailService emailService;
	 
	 @Autowired
	 private MemBanHistoryService memBanHistoryService;
	 // 여행 목록 화면 호출
    @GetMapping("/adminLogin")
    public String getTripList(HttpSession session) {
        return "adminLogin";  // tripList.jsp를 반환
    }
    
    @GetMapping("/adminUser")
    public String showAllMembers(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) String status,
                                  Model model) {
        System.out.println("페이지: " + page);
        System.out.println("검색어: " + search);
        System.out.println("상태: " + status);

        PageReqDTO pageReqDTO = new PageReqDTO();
        pageReqDTO.setPage(page);
        pageReqDTO.setSearch(search); // 검색어 설정

        // 상태가 주어지면 Flag로 변환
        if (status != null && !status.isEmpty()) {
            pageReqDTO.setActivateFlag(Flag.valueOf(status));
        }

        PageResDTO<MemberVO> pageResDTO = memberService.getAllMembers(pageReqDTO); // 수정된 메서드 호출
        model.addAttribute("members", pageResDTO.getPostList()); // postList 전달
        model.addAttribute("pageResDTO", pageResDTO); // 페이지 정보 전달
        model.addAttribute("search", search); // 검색어 전달
        model.addAttribute("status", status); // 상태 전달
        System.out.println(pageResDTO);
        return "adminUser"; // adminUser.jsp로 연결
    }




 // 제재 페이지 호출
    @GetMapping("/banUser")
    public String getBanHistory(@RequestParam String id, Model model) {
        // 회원 기본 정보 가져오기
    	MemberVO member = memberService.findMemberByMemId(id);
        
        if (member == null) {
            // 로그에 에러 메시지 추가
            System.out.println("해당 ID의 회원을 찾을 수 없습니다: " + id);
            model.addAttribute("error", "해당 ID의 회원을 찾을 수 없습니다.");
            return "error"; // 오류 페이지로 리다이렉트하거나 적절한 페이지 반환
        }

        model.addAttribute("member", member);

        // 제재 히스토리 가져오기
        List<MemBanHistoryDTO> banHistoryList = memBanHistoryService.getBanHistoryByMemId(id);
        model.addAttribute("banHistoryList", banHistoryList);

        return "admin/banUser"; // JSP 경로
    }



    @PostMapping("/banUserProc")
    public ResponseEntity<String> banUser(@RequestParam String id, @RequestParam String email, @RequestParam String reason) {
        // 회원 제재 상태 업데이트 및 MEMBAN_HISTORY에 기록 추가
        memberService.applyBan(id, "M02", reason); // 상태 업데이트와 히스토리 기록

        // 이메일 발송
        if (email != null) {
            emailService.sendBanEmail(email, reason);
        }

        return ResponseEntity.ok("제재 완료");
    }




    // 로그인 처리
    @PostMapping("/adminLoginProc")
    public String login(@RequestParam("id") String id, 
                        @RequestParam("pass") String password, 
                        HttpSession session) {
        System.out.println("Received ID: " + id);
        System.out.println("Received Password: " + password);

        // 예시 계정 정보
        String correctId = "admin"; 
        String correctPassword = "1234";

        if (id.equals(correctId) && password.equals(correctPassword)) {
            System.out.println("Login successful for user: " + id);
            session.setAttribute("user", id);
            return "redirect:/admin/adminUser"; // 로그인 성공 후 리다이렉트 확인
        } else {
            System.out.println("Login failed for user: " + id + " with password: " + password);
            return "redirect:/admin/adminLogin?error=true"; // 실패 시 리다이렉트 확인
        }

    }

    // 로그아웃 처리
    @GetMapping("/adminLogout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/admin/adminLogin"; // 로그인 페이지로 리다이렉트
    }
    
}
