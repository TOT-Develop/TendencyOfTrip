package tot.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trip")
public class TripController {

	@GetMapping
	public String getTripMap(@RequestParam(value = "tripId", required = false) String tripId, HttpSession session, Model model) {
	    if (tripId != null) {
	        // tripId를 모델에 추가
	        model.addAttribute("tripId", tripId);
	    } else {
	        // tripId가 없는 경우 처리 로직
	        model.addAttribute("tripId", "undefined");
	    }

	    // 반환할 뷰 이름
	    return "trip"; // trip.jsp와 일치해야 합니다.
	}
	
    // 여행 목록 화면 호출
    @GetMapping("/list")
    public String getTripList(HttpSession session) {
        // memid를 세션에 저장
        return "tripList";  // tripList.jsp를 반환
    }
}
