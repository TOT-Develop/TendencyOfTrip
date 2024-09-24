package tot.controller;

import static tot.common.Constants.PAGE_DETAIL_TREVIEW;
import static tot.common.Constants.PAGE_EDIT_TREVIEW;
import static tot.common.Constants.PAGE_TREVIEW;
import static tot.common.Constants.PAGE_WRITE_TREVIEW;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.CommentVO;
import tot.domain.CourseDTO;
import tot.domain.MemberVO;
import tot.domain.TReviewReqDTO;
import tot.domain.TReviewResDTO;
import tot.domain.TripVO;
import tot.service.CommentService;
import tot.service.CourseService;
import tot.service.TReviewService;
import tot.service.TripService;
import tot.util.MemberUtil;
import tot.util.ResponseUtil;

@Controller
@RequestMapping("/review/{boardId}")
public class TReviewController {

	@Autowired
	private TReviewService treviewService;

	@Autowired
	private TripService tripService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CommentService commentService;

	// 여행 후기 화면 이동
	@GetMapping("/{page}")
	public String showTourReview(@PathVariable String boardId, @ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PageResDTO<TReviewResDTO> pagination = treviewService.findTReviewListWithPaging(pageReqDTO, boardId);

		model.addAttribute("boardId", boardId);
		model.addAttribute("pagination", pagination);

		return PAGE_TREVIEW;
	}

	// 여행 후기 작성 화면 이동
	@GetMapping("/add")
	public String showTourReviewWrite(Model model) {
		MemberVO member = MemberUtil.getAuthenticatedMember();
		String MemId = member.getMemId();
		List<TripVO> trips = tripService.getTripsByMemId(MemId);

		model.addAttribute("trips", trips);
		model.addAttribute("member", member);

		return PAGE_WRITE_TREVIEW;
	}

	// 해당 여행의 코스 정보 처리
	@ResponseBody
	@GetMapping("/add/{tripId}")
	public List<CourseDTO> getCourseDetailById(@RequestParam("tripId") int tripId) {
		List<CourseDTO> courses = courseService.getCourseDetailsByTripId(tripId);

		return courses;
	}

	// 여행 후기 작성 처리
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Map<String, String>> submitTourReviewWrite(@ModelAttribute TReviewReqDTO tReviewReqDTO,
			@RequestParam(value = "reviewImage", required = false) MultipartFile[] imageFiles) {
		MemberVO member = MemberUtil.getAuthenticatedMember();
		tReviewReqDTO.setMemId(member.getMemId());

		treviewService.insertTReview(tReviewReqDTO, imageFiles);

		return ResponseUtil.createTReviewResponse("여행 후기 글이 등록되었습니다.");
	}

	// 여행 후기 수정 화면 이동
	@GetMapping("/edit/{trevId}")
	public String showTourReviewEdit(@PathVariable int trevId, Model model) {
		MemberVO member = MemberUtil.getAuthenticatedMember();

		List<TripVO> trips = tripService.getTripsByMemId(member.getMemId());
		TReviewResDTO review = treviewService.getTReviewById(trevId);
		List<CourseDTO> courses = courseService.getCourseDetailsByTripId(review.getTripId());

		model.addAttribute("trips", trips);
		model.addAttribute("member", member);
		model.addAttribute("review", review);
		model.addAttribute("courses", courses);

		return PAGE_EDIT_TREVIEW;
	}

	// 여행 후기 수정 처리
	@PostMapping("/edit/{trevId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> editTourReview(@PathVariable int trevId,
			@ModelAttribute TReviewReqDTO tReviewReqDTO,
			@RequestParam(value = "reviewImage", required = false) MultipartFile[] imageFiles,
			@RequestParam(value = "existingImages", required = false) List<String> existingImages) {
		MemberVO member = MemberUtil.getAuthenticatedMember();
		tReviewReqDTO.setMemId(member.getMemId());

		treviewService.editTReview(tReviewReqDTO, imageFiles, existingImages);

		return ResponseUtil.createTReviewResponse("여행 후기 글 수정이 완료되었습니다.");
	}

	// 여행 후기 삭제 처리
	@GetMapping("/delete/{trevId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteTourReview(@PathVariable int trevId) {
		treviewService.deleteTReview(trevId);

		return ResponseUtil.createTReviewResponse("여행 후기 글 삭제가 완료되었습니다.");
	}

	// 여행 후기 신고 처리
	@PostMapping("/report/{trevId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> reportTourReview(@PathVariable int trevId,
			@RequestParam("reportedContentType") String reportedContentType,
			@RequestParam("reportReason") String reportReason) {
		treviewService.reportTReview(trevId, reportedContentType, reportReason);

		return ResponseUtil.createTReviewResponse("여행 후기 글 신고가 접수되었습니다.");
	}

	// 여행 후기 상세 화면 이동
	@GetMapping("/detail/{trevId}")
	public String showTourReviewDetail(@PathVariable("boardId") String boardId, @PathVariable("trevId") int trevId,
			Model model) {
		MemberVO member = MemberUtil.getAuthenticatedMember();

		TReviewResDTO review = treviewService.getTReviewById(trevId);
		treviewService.incrementTReviewCount(trevId);
		List<CourseDTO> courses = courseService.getCourseDetailsByTripId(review.getTripId());
		List<CommentVO> comments = commentService.getCommentsByReviewId(trevId);

		model.addAttribute("boardId", boardId);
		model.addAttribute("member", member);
		model.addAttribute("review", review);
		model.addAttribute("courses", courses);
		model.addAttribute("comments", comments);

		return PAGE_DETAIL_TREVIEW;
	}

}
