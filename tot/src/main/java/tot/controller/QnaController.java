package tot.controller;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tot.domain.Qna;
import tot.service.QnaService;

@RestController
@RequestMapping(value = "/qna", produces = "application/json; charset=UTF-8")
public class QnaController {
    
    @Autowired
    private QnaService qnaService;
    
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

    

    
    
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Qna>> getQnaList() {
        @SuppressWarnings("unchecked")
        List<Qna> qnaList = qnaService.qnaList();
        
        if (qnaList == null) {
            qnaList = new ArrayList<>(); // 빈 리스트를 반환
        }
        
        return ResponseEntity.ok(qnaList);
    }
	

    
    // post 글쓰기
    @RequestMapping(value = "insertQna", method = RequestMethod.POST)
	public String insertQna(@RequestBody Qna qna) {
		System.out.println("memid ===> " + qna.getMemid());
        if (qna.getMemid() == null) {
            throw new IllegalArgumentException("MEMID cannot be null");
        }
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
	
	
}
