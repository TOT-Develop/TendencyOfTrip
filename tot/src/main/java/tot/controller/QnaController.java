package tot.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tot.common.enums.SearchType;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.Member;
import tot.domain.Qna;
import tot.service.QnaService;

@RestController
@RequestMapping(value = "/qna", produces = "application/json; charset=UTF-8")
public class QnaController {
    
    @Autowired
    private QnaService qnaService;
    
    public String getQnaListWithPaging(@ModelAttribute PageReqDTO pageReqDTO, Model model) throws Exception {
        if (pageReqDTO.getPage() == 0) {
            pageReqDTO.setPage(1);
        }

        PageResDTO<Qna> pagination = qnaService.findQnaListWithPaging(pageReqDTO);

        model.addAttribute("pagination", pagination);
        model.addAttribute("pageReqDTO", pageReqDTO);

        return "qna";  // qna.jsp로 이동
    }
    
    
    // List를 세션에 저장하는 메소드
    @GetMapping("/store")
    public ResponseEntity<String> storeQnaList(HttpSession session) {
        if (qnaService == null) {
            throw new IllegalStateException("QnaService is not initialized.");
        }

        List<Qna> qnaList = qnaService.qnaList();
        session.setAttribute("qnaList", qnaList);
        
        return ResponseEntity.ok("Qna list has been stored in session.");
    }
    
    
    
    @GetMapping("/qnaDetail")
    public ResponseEntity<Qna> getQnaDetail(@RequestParam("QNAID") int qnaid) {
    	Qna qna = qnaService.getQnaDetail(qnaid);
    	return ResponseEntity.ok(qna);
    }
    
    // List 가져오는 메소드
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Qna>> getQnaListWithPaging(
    	    @RequestParam(value = "searchType", defaultValue = "ALL") String searchType,
    	    @RequestParam(value = "page", defaultValue = "1") int page) throws Exception {

    	    PageReqDTO pageReqDTO = new PageReqDTO();
    	    pageReqDTO.setSearchType(SearchType.valueOf(searchType)); // 전달된 searchType을 설정
    	    pageReqDTO.setPage(page); // 페이지 번호 설정
    	    
    	    PageResDTO<Qna> pagination = qnaService.findQnaListWithPaging(pageReqDTO);

    	    return ResponseEntity.ok(pagination.getPostList());
    }

	        
    
    // post 글쓰기
    @RequestMapping(value = "insertQna", method = RequestMethod.POST)
	public String insertQna(@RequestBody Qna qna, HttpSession session) {
    	// 세션에서 member 객체 가져오기
    	Member member = (Member) session.getAttribute("member");
    	
        if (member == null) {
            throw new IllegalArgumentException("Member must be logged in.");
        }
        
        String memId = member.getMemId();
        
        String memNick = member.getMemNick();
        
        qna.setMemid(memId);
        qna.setMemNick(memNick);
        
        qnaService.insertQna(qna);
		return "/qna";
	}
	

	
	
	@GetMapping("/qna/{QNAID}")
	public ResponseEntity<Qna> getQna(@PathVariable("QNAID") int QNAID) {
		Qna qna = qnaService.getQna(QNAID);
		
        if (qna != null) {
            return ResponseEntity.ok(qna);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
	}
	
	
	@RequestMapping("/qna")
	public String getMemberId(@RequestBody int MEMID) {
		return "/qna";
	}
	
	@GetMapping("/getNickname")
	public ResponseEntity<String> getMemNickByMemId(@RequestParam("memid") String memid) {
	    String memNick = qnaService.getMemNickByMemId(memid);
	    return ResponseEntity.ok(memNick);
	}
	
	// 사용자가 작성한 QnA 목록 조회
	@GetMapping("/myQna")
	public ResponseEntity<?> getMyQnaList(HttpSession session) {
	    // 세션에서 memId 가져오기
	    Member member = (Member) session.getAttribute("member");
	    if (member == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	            .body("로그인 후 이용할 수 있습니다.");
	    }

	    String memId = member.getMemId();
	    if (memId == null || memId.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body("잘못된 요청입니다.");
	    }

	    List<Qna> myQnaList = qnaService.findMyQnaList(memId);
	    return ResponseEntity.ok(myQnaList);
	}
	
	
}