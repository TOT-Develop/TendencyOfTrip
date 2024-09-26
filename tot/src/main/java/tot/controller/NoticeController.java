package tot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import tot.common.enums.SearchType;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.Notice;
import tot.service.NoticeService;

import javax.inject.Inject;

import java.util.List;

@CrossOrigin(origins="*")
@Controller  
@RequestMapping("/api/notices")
public class NoticeController {

    @Inject
    NoticeService noticeService;

    // 공지사항 목록 가져오기 
    @GetMapping
    public String getNoticeList(@ModelAttribute PageReqDTO pageReqDTO, Model model) throws Exception {
        if (pageReqDTO.getPage() == 0) {
            pageReqDTO.setPage(1);
        }
        
        // 페이징 처리된 공지사항 목록 가져오기
        PageResDTO<Notice> pagination = noticeService.findNoticeListWithPaging(pageReqDTO);
        
        System.out.println("검색 결과: " + pagination);
        
        // 모델에 공지사항 목록과 페이징 정보를 담아서 JSP로 전달
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageReqDTO", pageReqDTO);

        return "notice";  // JSP 뷰로 이동
    }

    // 공지사항 상세보기 (GET)
    @GetMapping("/detail/{noid}")
    public String getNoticeDetail(@PathVariable int noid, Model model) throws Exception {
        Notice notice = noticeService.getNoticeById(noid);
        model.addAttribute("notice", notice);  // 모델에 notice 객체 추가
        return "noticeDetail";  // JSP 파일의 경로 
    }

    // 공지사항 작성 (POST)
    @PostMapping
    public String insertNotice(@RequestBody Notice notice) throws Exception {
        noticeService.insertNotice(notice);
        return "redirect:/api/notices";  // JSP로 직접 가지 않고 공지사항 리스트를 로드하는 경로로 리다이렉트
    }

    // 공지사항 수정 페이지로 이동 (GET)
    @GetMapping("/update/{noid}")
    public String updateNoticeForm(@PathVariable int noid, Model model) throws Exception {
        Notice notice = noticeService.getNoticeById(noid);  // noid로 공지사항을 조회
        model.addAttribute("notice", notice);  // 조회한 공지사항 데이터를 모델에 추가
        return "noticeUpdate";  // noticeUpdate.jsp로 이동
    }

    // 공지사항 수정 (POST 혹은 PUT)
    @PutMapping("/{noid}")
    public ResponseEntity<String> updateNotice(@PathVariable int noid, @RequestBody Notice notice) throws Exception {
        notice.setNoid(noid);
        noticeService.updateNotice(notice);
        return ResponseEntity.ok("공지사항 수정 완료");
    }

    // 공지사항 삭제 (DELETE)
    @DeleteMapping("/{noid}")
    public ResponseEntity<String> deleteNotice(@PathVariable int noid) {
        try {
            noticeService.deleteNotice(noid);
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 공지사항 검색 (GET)
    @GetMapping("/search")
    @ResponseBody
    public List<Notice> searchNotice(@RequestParam("searchType") String searchType,
                                     @RequestParam("keyword") String keyword,
                                     @RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
        PageReqDTO pageReqDTO = new PageReqDTO();
        pageReqDTO.setSearchType(SearchType.valueOf(searchType));  // 검색 유형 설정
        pageReqDTO.setSearch(keyword);  // 검색어 설정
        pageReqDTO.setPage(page);  // 페이지 설정

        // 페이징 처리된 공지사항 목록 가져오기
        PageResDTO<Notice> pagination = noticeService.findNoticeListWithPaging(pageReqDTO);

        return pagination.getPostList();  // 공지사항 목록 반환
    }
}
