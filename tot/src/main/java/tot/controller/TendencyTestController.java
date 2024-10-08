package tot.controller;

import static tot.common.Constants.PAGE_TOT_TENDENCY_TEST;
import static tot.common.Constants.PAGE_TOT_TENDENCY_TEST_RESULT;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tot.domain.MemberVO;
import tot.service.TendencyTestService;
import tot.util.MemberUtil;

@Controller
public class TendencyTestController {

	private final TendencyTestService tendencyTestService;

	public TendencyTestController(TendencyTestService tendencyTestService) {
		this.tendencyTestService = tendencyTestService;
	}

	@GetMapping("/tendency")
	public String showTendencyTestPage() {
		return PAGE_TOT_TENDENCY_TEST;
	}

	@GetMapping("/tendencyTestResult")
	public String showResultPage(Model model) {
		MemberVO member = MemberUtil.getAuthenticatedMember();
		
		model.addAttribute("member", member);
		return PAGE_TOT_TENDENCY_TEST_RESULT;
	}

	@PostMapping("/updateTendency")
	@ResponseBody
	public Map<String, Object> updateMemberTendency(@RequestParam String memId, @RequestParam String resultType) {
		try {
			tendencyTestService.updateMemberTendency(memId, resultType);
			return Map.of("success", true);
		} catch (Exception e) {
			return Map.of("success", false, "error", e.getMessage());
		}
	}

	@PostMapping("/saveTendencyTestResult")
	@ResponseBody
	public Map<String, Object> saveTendencyTestResult(@RequestBody Map<String, String> requestBody) {
		String memId = requestBody.get("memId");
		String resultType = requestBody.get("resultType");
		System.out.println("memId: " + memId + ", resultType: " + resultType);
		try {
			tendencyTestService.updateMemberTendency(memId, resultType);
			return Map.of("success", true);
		} catch (Exception e) {
			return Map.of("success", false, "error", e.getMessage());
		}
	}
}
