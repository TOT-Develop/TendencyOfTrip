package tot.admin.controller;

import static tot.common.Constants.PAGE_ADMIN_NOTICE;
import static tot.common.Constants.PAGE_ADMIN_NOTICE_DETAIL;
import static tot.common.Constants.PAGE_ADMIN_NOTICE_REGIST;
import static tot.common.Constants.PAGE_ADMIN_NOTICE_UPDATE;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tot.admin.service.AdminNoticeService;
import tot.common.enums.SearchType;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.NoticeVO;

@Controller
@RequestMapping("admin/notice")
public class AdminNoticeController {

	private final AdminNoticeService adminNoticeService;

	public AdminNoticeController(AdminNoticeService adminNoticeService) {
		this.adminNoticeService = adminNoticeService;
	}

	// 공지사항 목록 가져오기
	@GetMapping
	public String getNoticeList(@ModelAttribute PageReqDTO pageReqDTO, Model model) {
		if (pageReqDTO.getPage() == 0) {
			pageReqDTO.setPage(1);
		}

		PageResDTO<NoticeVO> pagination = adminNoticeService.findNoticeListWithPaging(pageReqDTO);

		model.addAttribute("pagination", pagination);
		model.addAttribute("pageReqDTO", pageReqDTO);

		return PAGE_ADMIN_NOTICE; // JSP 뷰로 이동
	}

	// 공지사항 상세보기
	@GetMapping("/detail/{noId}")
	public String getNoticeDetailPage(@PathVariable int noId, Model model) {
		NoticeVO notice = adminNoticeService.getNoticeById(noId);
		if (notice == null) {
			// 공지사항이 없을 경우 처리 (예: 에러 페이지로 이동)
			return "errorPage"; // 오류 페이지로 리다이렉트 또는 다른 적절한 처리
		}
		model.addAttribute("notice", notice); // 모델에 notice 객체 추가

		return PAGE_ADMIN_NOTICE_DETAIL; // JSP 파일의 경로
	}

	@GetMapping("/{noId}")
	@ResponseBody
	public NoticeVO getNoticeDetailJson(@PathVariable int noId) {
		NoticeVO notice = adminNoticeService.getNoticeById(noId);

		return notice;
	}

	@GetMapping("/regist")
	public String getNoticeregist(HttpSession session) {
		return PAGE_ADMIN_NOTICE_REGIST;
	}

	// 공지사항 작성
	@PostMapping("/registProc")
	public String insertNotice(@RequestBody NoticeVO notice) {
		adminNoticeService.insertNotice(notice);

		return "notice";
	}

	// 공지사항 수정 페이지로 이동
	@GetMapping("/update/{noId}")
	public String updateNoticeForm(@PathVariable int noId, Model model) {
		NoticeVO notice = adminNoticeService.getNoticeById(noId); // noId로 공지사항을 조회
		model.addAttribute("notice", notice); // 조회한 공지사항 데이터를 모델에 추가

		return PAGE_ADMIN_NOTICE_UPDATE;
	}

	// 공지사항 수정
	@PutMapping("/{noId}")
	public ResponseEntity<String> updateNotice(@PathVariable int noId, @RequestBody NoticeVO notice) {
		adminNoticeService.updateNotice(notice);

		return ResponseEntity.ok("공지사항 수정 완료");
	}

	// 공지사항 삭제 처리
	@DeleteMapping("/{noId}")
	public ResponseEntity<String> deleteNotice(@PathVariable int noId) {
		adminNoticeService.deleteNotice(noId);

		return ResponseEntity.ok("삭제되었습니다.");
	}

	// 공지사항 검색
	@GetMapping("/search")
	@ResponseBody
	public List<NoticeVO> searchNotice(@RequestParam("searchType") String searchType,
			@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "1") int page) {
		PageReqDTO pageReqDTO = new PageReqDTO();
		pageReqDTO.setSearchType(SearchType.valueOf(searchType));
		pageReqDTO.setSearch(keyword);
		pageReqDTO.setPage(page);

		// 페이징 처리된 공지사항 목록 가져오기
		PageResDTO<NoticeVO> pagination = adminNoticeService.findNoticeListWithPaging(pageReqDTO);

		return pagination.getPostList();
	}
}
