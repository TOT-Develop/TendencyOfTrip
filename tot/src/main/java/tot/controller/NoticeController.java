package tot.controller;

import static tot.common.Constants.PAGE_NOTICE;
import static tot.common.Constants.PAGE_NOTICE_DETAIL;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.NoticeVO;
import tot.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	private final NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	// 공지사항 목록 가져오기
	@GetMapping("/{page}")
	public String getNoticeList(@ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PageResDTO<NoticeVO> pagination = noticeService.findNoticeListWithPaging(pageReqDTO);

		model.addAttribute("pagination", pagination);
		return PAGE_NOTICE;
	}

	// 공지사항 상세보기
	@GetMapping("/detail/{noId}")
	public String getNoticeDetail(@PathVariable int noId, Model model) {
		NoticeVO notice = noticeService.getNoticeDetail(noId);

		model.addAttribute("notice", notice);
		return PAGE_NOTICE_DETAIL;
	}

}
