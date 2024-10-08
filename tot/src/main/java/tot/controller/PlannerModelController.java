package tot.controller;

import static tot.common.Constants.PAGE_PLANNER;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/planner")
public class PlannerModelController {

	@GetMapping
	public String showRecommendationForm() {
		return PAGE_PLANNER;
	}

	@GetMapping("/data")
	@ResponseBody
	public Map<String, Object> getRecommendationData(HttpSession session) {
		return (Map<String, Object>) session.getAttribute("recommendationData");
	}

	@GetMapping("/chatdata")
	@ResponseBody
	public Map<String, Object> getRecommendationChatData(HttpSession session) {
		Object recommendationContent = session.getAttribute("recommendationContent");
		if (recommendationContent != null) {
			// 이미 JSON 객체로 저장된 경우, 그대로 반환
			return Map.of("content", recommendationContent);
		} else {
			return Map.of("content", "No data found");
		}
	}

}
