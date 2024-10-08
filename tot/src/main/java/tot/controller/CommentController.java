package tot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tot.domain.CommentReqDTO;
import tot.exception.ErrorCode;
import tot.service.CommentService;
import tot.util.MemberUtil;
import tot.util.ResponseUtil;
import tot.util.ValidationUtil;

@Controller
@RequestMapping("{boardId}/{postId}/comment")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/add")
	public String addComment(@PathVariable("boardId") String boardId, @PathVariable("postId") int postId,
			@ModelAttribute CommentReqDTO commentReqDTO, Model model) {
		MemberUtil.isAuthenticatedMember();

		commentService.insertComment(boardId, postId, commentReqDTO);

		return "redirect:/review/" + boardId + "/detail/" + postId;
	}

	@PostMapping(value = "/edit/{commentId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Map<String, String>> editComment(@PathVariable("commentId") int commentId,
			@RequestParam("content") String content) {
		MemberUtil.isAuthenticatedMember();

		commentService.editComment(commentId, content);
		String updatedDate = commentService.getUpdateDate(commentId);

		return ResponseUtil.createCommentResponse("댓글 수정이 완료되었습니다.", updatedDate);
	}

	@GetMapping(value = "/delete/{commentId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("commentId") int commentId) {
		MemberUtil.isAuthenticatedMember();

		commentService.deleteComment(commentId);
		String updatedDate = commentService.getUpdateDate(commentId);

		return ResponseUtil.createCommentResponse("댓글이 삭제되었습니다.", updatedDate);
	}

	@PostMapping(value = "/report/{commentId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Map<String, String>> reportComment(@PathVariable("commentId") int commentId,
			@RequestParam("reportedContentType") String reportedContentType,
			@RequestParam("reportReason") String reportReason) {
		MemberUtil.isAuthenticatedMember();
		ValidationUtil.validateNotEmpty(reportReason, ErrorCode.NOT_FOUND_REPORTREASON);

		commentService.reportComment(commentId, reportedContentType, reportReason);

		Map<String, String> response = new HashMap<>();
		response.put("message", "신고가 접수되었습니다.");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
