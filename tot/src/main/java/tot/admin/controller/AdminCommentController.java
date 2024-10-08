package tot.admin.controller;

import static tot.common.Constants.PAGE_ADMIN_TREVIEW_COMMENT;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tot.admin.service.AdminCommentService;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.CommentVO;
import tot.util.ResponseUtil;

@Controller
@RequestMapping("/admin/comment/{boardId}")
public class AdminCommentController {

	private final AdminCommentService adminCommentService;

	public AdminCommentController(AdminCommentService adminCommentService) {
		this.adminCommentService = adminCommentService;
	}

	// 게시물 관리 화면 이동
	@GetMapping("/{page}")
	public String showAdminComment(@PathVariable int boardId, @ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PageResDTO<CommentVO> pagination = adminCommentService.findCommentListWithPaging(pageReqDTO, boardId);

		model.addAttribute("boardId", boardId);
		model.addAttribute("pagination", pagination);

		return PAGE_ADMIN_TREVIEW_COMMENT;
	}

	// 댓글 상태 업데이트 처리
	@PostMapping("/{status}")
	public ResponseEntity<Map<String, String>> updateCommentStatus(@PathVariable String status,
			@RequestBody List<Integer> trevcIds) {
		adminCommentService.updateCommentStatus(status, trevcIds);

		return ResponseUtil.createTReviewResponse("댓글 상태가 업데이트되었습니다.");
	}

}
