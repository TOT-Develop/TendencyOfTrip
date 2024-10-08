package tot.controller;

import static tot.common.Constants.PAGE_QNA;
import static tot.common.Constants.PAGE_QNA_DETAIL;
import static tot.common.Constants.PAGE_QNA_WRITE;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemberVO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;
import tot.domain.QnaVO;
import tot.service.QnaService;
import tot.util.MemberUtil;
import tot.util.ResponseUtil;

@Controller
@RequestMapping("/qna/{boardId}")
public class QnaController {

	private final QnaService qnaService;

	public QnaController(QnaService qnaService) {
		this.qnaService = qnaService;
	}

	// 문의글 화면 이동
	@GetMapping("/{page}")
	public String getQnaListWithPaging(@PathVariable int boardId, @ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PageResDTO<QnaDTO> pagination = qnaService.findQnaListWithPaging(pageReqDTO, boardId);

		model.addAttribute("boardId", boardId);
		model.addAttribute("pagination", pagination);
		return PAGE_QNA;
	}

	// 문의글 작성 화면 이동
	@GetMapping("/add")
	public String showQnaWrite(Model model) {
		MemberVO member = MemberUtil.isMember();

		model.addAttribute("member", member);
		return PAGE_QNA_WRITE;
	}

	// 문의글 작성 처리
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> insertQna(@ModelAttribute QnaDTO qnaDTO) {
		MemberVO member = MemberUtil.isMember();
		qnaDTO.validate();
		QnaVO qnaVO = QnaVO.fromDTO(qnaDTO, member);
		qnaService.insertQna(qnaVO);

		return ResponseUtil.createTReviewResponse("문의글이 등록되었습니다.");
	}

	// 문의글 상세 페이지 이동
	@GetMapping("/detail/{qnaId}")
	public String getQnaDetail(@PathVariable("qnaId") int qnaId, Model model) {
		QnaDTO qnaDetail = qnaService.getQnaDetail(qnaId);
		List<QnaCommentVO> comments = qnaService.getCommentsByQnaId(qnaId);

		model.addAttribute("qnaDetail", qnaDetail);
		model.addAttribute("comments", comments);
		return PAGE_QNA_DETAIL;
	}

}