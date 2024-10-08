package tot.admin.controller;

import static tot.common.Constants.PAGE_ADMIN_KEY;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tot.admin.service.AdminKeyService;

@Controller
@RequestMapping("/admin/key")
public class AdminKeyController {

	private static final String OPENAI_API_URL = "OPENAI_API_URL"; // OpenAI API URL
	private static final String OPENAI_API_KEY = "OPENAI_API_KEY"; // OpenAI

	private AdminKeyService adminKeyService;

	public AdminKeyController(AdminKeyService adminKeyService) {
		this.adminKeyService = adminKeyService;
	}

	@GetMapping
	public String checkOpenAiApiKeyStatus(Model model) {
		String apiStatus = checkOpenAiApiKey();
		model.addAttribute("apiStatus", apiStatus);
		return PAGE_ADMIN_KEY;
	}

	@GetMapping("/status/google")
	@ResponseBody
	public ResponseEntity<Map<String, String>> checkGoogleApiKeyStatus() {
		String status = adminKeyService.checkGoogleApiKey();
		Map<String, String> response = new HashMap<>();
		response.put("api", "Google OAuth");
		response.put("status", status);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/status/naver")
	@ResponseBody
	public ResponseEntity<Map<String, String>> checkNaverApiKeyStatus() {
		String status = adminKeyService.checkNaverApiKey();
		Map<String, String> response = new HashMap<>();
		response.put("api", "Naver OAuth");
		response.put("status", status);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/status/kakao")
	@ResponseBody
	public ResponseEntity<Map<String, String>> checkKakaoApiKeyStatus() {
		String status = adminKeyService.checkKakaoApiKey();
		Map<String, String> response = new HashMap<>();
		response.put("api", "Kakao OAuth");
		response.put("status", status);
		return ResponseEntity.ok(response);
	}

	private String checkOpenAiApiKey() {
		try {
			URL url = new URL(OPENAI_API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			String jsonBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"Hello\"}]}";
			connection.getOutputStream().write(jsonBody.getBytes("UTF-8"));

			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				return "OpenAI 키 상태: 정상";
			} else {
				return "OpenAI 키 상태: 오류";
			}

		} catch (IOException e) {
			return "OpenAI 키 상태: 오류";
		}
	}
}
