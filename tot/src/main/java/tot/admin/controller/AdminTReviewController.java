package tot.admin.controller;

import static tot.common.Constants.PAGE_ADMIN_DETAIL_TREVIEW;
import static tot.common.Constants.PAGE_ADMIN_TREVIEW;

import java.util.ArrayList;
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

import tot.admin.enums.BanReason;
import tot.admin.service.AdminCommentService;
import tot.admin.service.AdminTReviewService;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.CommentVO;
import tot.domain.CourseDTO;
import tot.domain.HistoryVO;
import tot.domain.TReviewResDTO;
import tot.service.CourseService;
import tot.service.HistoryService;
import tot.util.MemberUtil;
import tot.util.ResponseUtil;

@Controller
@RequestMapping("/admin/review/{boardId}")
public class AdminTReviewController {

	private final AdminTReviewService adminTreviewService;
	private final CourseService courseService;
	private final AdminCommentService adminCommentService;
	private final HistoryService historyService;

	public AdminTReviewController(AdminTReviewService adminTreviewService, CourseService courseService,
			AdminCommentService adminCommentService, HistoryService historyService) {
		this.adminTreviewService = adminTreviewService;
		this.courseService = courseService;
		this.adminCommentService = adminCommentService;
		this.historyService = historyService;
	}

	// 게시물 관리 화면 이동
	@GetMapping("/{page}")
	public String showAdminTReview(@PathVariable int boardId, @ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PageResDTO<TReviewResDTO> pagination = adminTreviewService.findTReviewListWithPaging(pageReqDTO, boardId);

		model.addAttribute("boardId", boardId);
		model.addAttribute("pagination", pagination);

		return PAGE_ADMIN_TREVIEW;
	}

	// 게시물 상태 업데이트 처리
	@PostMapping("/{status}")
	public ResponseEntity<Map<String, String>> updateTReviewStatus(@PathVariable String status,
			@RequestBody Map<String, Object> requestData) {
		List<Integer> trevIds = extractTrevIds(requestData.get("trevIds"));
		String reasonValue = (String) requestData.get("reason");
		BanReason reason = BanReason.valueOf(reasonValue);

		adminTreviewService.updateTReviewStatus(status, trevIds, reason);

		return ResponseUtil.createTReviewResponse("게시물 상태가 업데이트되었습니다.");
	}

	// 게시물 상세 화면 이동
	@GetMapping("/detail/{trevId}/{page}")
	public String showTourReviewDetail(@PathVariable("boardId") int boardId, @ModelAttribute PageReqDTO pageReqDTO,
			@PathVariable("trevId") int trevId, Model model) {
		String admin = MemberUtil.getAdmin();

		TReviewResDTO review = adminTreviewService.getTReviewById(trevId);
		List<CourseDTO> courses = courseService.getCourseDetailsByTripId(review.getTripId());
		PageResDTO<CommentVO> comments = adminCommentService.findCommentListWithPaging(pageReqDTO, boardId, trevId);
		List<HistoryVO> historys = historyService.getTReviewHistorysById(trevId);

		model.addAttribute("boardId", boardId);
		model.addAttribute("member", admin);
		model.addAttribute("review", review);
		model.addAttribute("courses", courses);
		model.addAttribute("comments", comments);
		model.addAttribute("historys", historys);
		model.addAttribute("historyCount", historys.size());

		return PAGE_ADMIN_DETAIL_TREVIEW;
	}

	private List<Integer> extractTrevIds(Object trevIdsRaw) {
		List<Integer> trevIds = new ArrayList<>();

		// trevIdsRaw가 리스트가 아닐 경우 빈 리스트 반환
		if (!(trevIdsRaw instanceof List<?>)) {
			return trevIds;
		}

		List<?> list = (List<?>) trevIdsRaw;

		// 중첩 리스트인지 확인
		if (!list.isEmpty() && list.get(0) instanceof List<?>) {
			// 중첩 리스트 처리
			processNestedList(list, trevIds);
		} else {
			// 단일 리스트 처리
			processSingleList(list, trevIds);
		}

		return trevIds;
	}

	// 주어진 리스트가 중첩된 리스트일 경우, 각 ID를 추출하여 trevIds에 추가하는 메서드.
	private void processNestedList(List<?> list, List<Integer> trevIds) {
		for (Object obj : list) {
			if (obj instanceof List<?>) {
				List<?> nestedList = (List<?>) obj;
				for (Object id : nestedList) {
					addIdToList(trevIds, id);
				}
			}
		}
	}

	// 주어진 리스트가 단일 리스트일 경우, 각 ID를 추출하여 trevIds에 추가하는 메서드.
	private void processSingleList(List<?> list, List<Integer> trevIds) {
		for (Object id : list) {
			addIdToList(trevIds, id);
		}
	}

	// 주어진 ID를 trevIds 리스트에 추가하는 메서드.
	private void addIdToList(List<Integer> trevIds, Object id) {
		if (id instanceof Number) {
			trevIds.add(((Number) id).intValue());
		} else if (id instanceof String) {
			try {
				trevIds.add(Integer.parseInt((String) id));
			} catch (NumberFormatException e) {
				System.out.println("Invalid ID format: " + id);
			}
		}
	}

}
