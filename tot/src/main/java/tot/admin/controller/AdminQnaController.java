package tot.admin.controller;

import static tot.common.Constants.PAGE_ADMIN_QNA;
import static tot.common.Constants.PAGE_ADMIN_QNA_DETAIL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tot.admin.service.AdminQnaService;
import tot.common.enums.SearchType;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemberVO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;
import tot.service.EmailService;
import tot.service.MemberService;

@Controller
@RequestMapping("/admin/qna/{boardId}")
public class AdminQnaController {

	private final AdminQnaService adminQnaService;
	private final MemberService memberService;
	private final EmailService emailService;

	public AdminQnaController(AdminQnaService adminQnaService, MemberService memberService, EmailService emailService) {
		this.adminQnaService = adminQnaService;
		this.memberService = memberService;
		this.emailService = emailService;
	}

	@GetMapping("/{page}")
	public Object getQnaListWithPaging(@PathVariable int page, @RequestParam(defaultValue = "ALL") String searchType,
			@RequestParam(defaultValue = "1") Integer boardId, @ModelAttribute PageReqDTO pageReqDTO, Model model,
			HttpServletRequest request) {
		pageReqDTO.setSearchType(SearchType.valueOf(searchType));
		pageReqDTO.setPage(page);

		PageResDTO<QnaDTO> pagination = adminQnaService.findQnaListWithPaging(pageReqDTO, boardId);

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			return ResponseEntity.ok(pagination);
		} else {
			model.addAttribute("boardId", boardId);
			model.addAttribute("pagination", pagination);
			return PAGE_ADMIN_QNA;
		}
	}

	@GetMapping("/detail/{qnaId}")
	public String getQnaDetail(@PathVariable("qnaId") int qnaId, Model model) {
		QnaDTO qnaDetail = adminQnaService.getQnaDetail(qnaId);
		List<QnaCommentVO> comments = adminQnaService.getCommentsByQnaId(qnaId);

		model.addAttribute("qnaDetail", qnaDetail);
		model.addAttribute("comments", comments);

		return PAGE_ADMIN_QNA_DETAIL;
	}

	@PostMapping(value = "/addComment", produces = "application/json;  charset=UTF-8")
	public ResponseEntity<String> addComment(@RequestParam("qnaId") Integer qnaId,
			@RequestParam("commentText") String commentText, HttpSession session) {
		QnaCommentVO comment = new QnaCommentVO(qnaId, commentText, null, null);
		adminQnaService.insertQnaComment(comment);

		return ResponseEntity.ok("댓글이 정상적으로 추가되었습니다.");
	}

	@GetMapping("/comments")
	public ResponseEntity<List<QnaCommentVO>> getQnaComments(@RequestParam("QNAID") int qnaId) {
		List<QnaCommentVO> comments = adminQnaService.getCommentsByQnaId(qnaId);

		return ResponseEntity.ok(comments);
	}

	@PostMapping("/changeStatus")
	public ResponseEntity<Map<String, Object>> changeQnaStatus(@RequestBody Map<String, Object> requestData) {
		int qnaId = (int) requestData.get("qnaId");
		String newStatus = (String) requestData.get("qna_002");

		int result = adminQnaService.updateQnaStatus(qnaId, newStatus);

		Map<String, Object> response = new HashMap<>();
		response.put("success", result > 0);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/sendQnaEmail")
	public ResponseEntity<String> sendQnaEmail(@RequestParam("memId") String memId,
			@RequestParam("qnaTitle") String qnaTitle, @RequestParam("qnaText") String qnaText,
			@RequestParam("comment") String comment) {
		MemberVO member = memberService.findMemberByMemId(memId);
		String email = member.getMemEmail();
		String memNick = member.getMemNick();

		// 이메일 전송 로직
		emailService.sendQnaEmail(memNick, email, qnaTitle, qnaText, comment);

		return ResponseEntity.ok("이메일 전송 성공");
	}

}