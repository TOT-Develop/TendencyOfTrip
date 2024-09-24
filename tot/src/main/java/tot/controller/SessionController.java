package tot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

	 @GetMapping("/id")
	    public Map<String, String> getSessionId(HttpSession session) {
	        // 세션에서 memId를 가져옵니다.
	        String memId = (String) session.getAttribute("memId");
	        // memId가 없다면 'guest'로 기본값을 설정합니다.
	        Map<String, String> response = new HashMap<>();
	        response.put("memId", memId != null ? memId : "guest");
	        return response;
	    }
	
}
