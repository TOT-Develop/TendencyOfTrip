package tot.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GPT3Test {

	private static final String API_KEY = "API_KEY";
	private static final String API_URL = "API_URL";

	public static void main(String[] args) {
		try {
			// HttpClient 생성
			HttpClient client = HttpClient.newHttpClient();

			// 요청 헤더와 본문 설정
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("model", "gpt-3.5-turbo");
			requestBody.put("messages", new Message[] { new Message("system", "You are a helpful assistant."),
					new Message("user", "Write a short poem about the ocean.") });
			requestBody.put("max_tokens", 400);

			ObjectMapper objectMapper = new ObjectMapper();
			String requestBodyJson = objectMapper.writeValueAsString(requestBody);

			HttpRequest request = HttpRequest.newBuilder().uri(new URI(API_URL))
					.header("Content-Type", "application/json").header("Authorization", "Bearer " + API_KEY)
					.POST(HttpRequest.BodyPublishers.ofString(requestBodyJson)).build();

			// API 호출
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// 응답 출력
			System.out.println("Response Status Code: " + response.statusCode());
			System.out.println("Response Body: " + response.body());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Inner class for message format
	static class Message {
		private String role;
		private String content;

		public Message(String role, String content) {
			this.role = role;
			this.content = content;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}
