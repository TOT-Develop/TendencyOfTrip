package tot.admin.controller;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tot.admin.service.AdminQnaService;
import tot.common.enums.SearchType;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemberVO;
import tot.domain.Qna;
import tot.domain.QnaComment;
import tot.service.QnaService;

@RestController
@RequestMapping(value = "/admin/qna", produces = "application/json; charset=UTF-8")
public class AdminQnaController {
    
    @Autowired
    private QnaService qnaService;
    
    @Autowired
    private AdminQnaService adminQnaService;
    
    
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
    
    
    @GetMapping("/QnaDetail")
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
    	MemberVO member = (MemberVO) session.getAttribute("member");
    	
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
		MemberVO member = (MemberVO) session.getAttribute("member");
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
	
	@PostMapping("/addComment")
	public ResponseEntity<String> addComment(@RequestParam("qnaId") Integer qnaId, 
	                         @RequestParam("commentText") String commentText, 
	                         HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("member");
	    if (member != null) {
	        QnaComment comment = new QnaComment();
	        comment.setQnaid(qnaId);
	        comment.setQnactext(commentText);
	        comment.setQnacregdate(new Timestamp(System.currentTimeMillis()));
	        comment.setQnacupdate(new Timestamp(System.currentTimeMillis()));

	        adminQnaService.insertQnaComment(comment);
	        return ResponseEntity.ok("Comment added successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to add a comment.");
	    }
	}
	
	@GetMapping("/comments")
	public ResponseEntity<List<QnaComment>> getQnaComments(@RequestParam("QNAID") int qnaId) {
	    List<QnaComment> comments = adminQnaService.getCommentsByQnaId(qnaId);
	    return ResponseEntity.ok(comments);
	}
	
	@PostMapping("/changeStatus")
	public ResponseEntity<Map<String, Object>> changeQnaStatus(@RequestBody Map<String, Object> requestData) {
	    int qnaId = (int) requestData.get("qnaId");
	    String newStatus = (String) requestData.get("qna_002");

	    // 상태 변경을 서비스에 요청
	    int result = adminQnaService.updateQnaStatus(qnaId, newStatus);

	    Map<String, Object> response = new HashMap<>();
	    if (result > 0) {
	        response.put("success", true);
	    } else {
	        response.put("success", false);
	    }

	    return ResponseEntity.ok(response);
	}
	
	
}