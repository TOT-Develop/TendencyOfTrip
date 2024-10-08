package tot.admin.controller;

import static tot.common.Constants.PAGE_ADMIN_BANUSER;
import static tot.common.Constants.PAGE_ADMIN_LOGIN;
import static tot.common.Constants.PAGE_ADMIN_USER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tot.common.enums.Flag;
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

	private final MemberService memberService;
	private final EmailService emailService;
	private final MemBanHistoryService memBanHistoryService;

	public AdminContoller(MemberService memberService, EmailService emailService,
			MemBanHistoryService memBanHistoryService) {
		this.memberService = memberService;
		this.emailService = emailService;
		this.memBanHistoryService = memBanHistoryService;
	}

	// 여행 목록 화면 호출
	@GetMapping("/adminLogin")
	public String getTripList(HttpSession session) {
		return PAGE_ADMIN_LOGIN;
	}

	@GetMapping("/adminUser")
	public String showAllMembers(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String search, @RequestParam(required = false) String status, Model model) {

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

		return PAGE_ADMIN_USER; // adminUser.jsp로 연결
	}

	// 제재 페이지 호출
	@GetMapping("/banUser")
	public String getBanHistory(@RequestParam String id, Model model) {
		System.out.println(id);

		// 회원 기본 정보 가져오기
		MemberVO member = memberService.findMemberByMemId(id);
		if (member == null) {
			model.addAttribute("error", "해당 ID의 회원을 찾을 수 없습니다.");
			return "/tot/"; // 오류 페이지로 리다이렉트하거나 적절한 페이지 반환
		}

		model.addAttribute("member", member);

		List<MemBanHistoryDTO> banHistoryList = memBanHistoryService.getBanHistoryByMemId(id);
		if (banHistoryList == null || banHistoryList.isEmpty()) {
			model.addAttribute("banHistoryList", new ArrayList<>()); // 빈 리스트로 설정
		} else {
			model.addAttribute("banHistoryList", banHistoryList);
		}

		return PAGE_ADMIN_BANUSER; // JSP 경로
	}

	@PostMapping("/banUserProc")
	public ResponseEntity<String> banUser(@RequestParam String id, @RequestParam String email,
			@RequestParam String reason) {
		// 회원 제재 상태 업데이트 및 MEMBAN_HISTORY에 기록 추가
		memberService.applyBan(id, "M02", reason); // 상태 업데이트와 히스토리 기록

		// 이메일 발송
		if (email != null) {
			emailService.sendBanEmail(email, reason);
		}

		return ResponseEntity.ok("제재 완료");
	}

	@PostMapping("/liftUserProc")
	public ResponseEntity<String> liftUser(@RequestParam String id, @RequestParam String reason) {
		// 회원 상태 업데이트 (M01로 변경)
		Map<String, Object> params = new HashMap<>();
		params.put("memId", id);
		params.put("memberStatus", "M01");
		params.put("banStart", null); // 시작 시간 제거
		params.put("banEnd", null); // 종료 시간 제거

		memberService.updateMemberStatus(params);

		// 제재 해제 사유와 시간 기록
		memBanHistoryService.updateLiftHistory(id, reason);

		return ResponseEntity.ok("제재 해제 완료");
	}

	// 로그인 처리
	@PostMapping(value = "/adminLoginProc", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> login(@RequestParam("id") String id, @RequestParam("pass") String password,
			HttpSession session) {

		// 예시 계정 정보
		String correctId = "admin";
		String correctPassword = "1234";

		if (id.equals(correctId) && password.equals(correctPassword)) {
			session.setAttribute("admin", id);
			return ResponseEntity.ok("로그인 성공"); // 로그인 성공 메시지
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 잘못된 ID 또는 비밀번호입니다."); // 실패 메시지
		}
	}

	// 로그아웃 처리
	@GetMapping("/adminLogout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		return "redirect:/admin/adminLogin"; // 로그인 페이지로 리다이렉트
	}

}