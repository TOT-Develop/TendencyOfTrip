package tot.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import tot.domain.GPTMessage;
import tot.domain.Hotel;
import tot.domain.Restaurant;
import tot.domain.Tour;
import tot.service.PlannerService;

@RestController
@RequestMapping(value = "/plannerTest", produces = "application/json; charset=UTF-8")
public class PlannerController {

	private static final String API_URL = "API_URL";
	private static final String API_KEY = "API_KEY";
	private PlannerService plannerService;

	public PlannerController(PlannerService plannerService) {
		this.plannerService = plannerService;
	}

	ObjectMapper objectMapper = new ObjectMapper();

	// 호텔, 레스토랑, 투어 데이터를 모두 JSON으로 묶어서 외부 API로 전송
	@GetMapping("/all/{areacode}")
	public String getAllData(@PathVariable String areacode) {
		try {
			// 호텔, 레스토랑, 투어 데이터를 가져옴
			List<Hotel> hotels = plannerService.selectHotel(areacode);
			List<Restaurant> restaurants = plannerService.selectRestaurant(areacode);
			List<Tour> tours = plannerService.selectTour(areacode);

			// 데이터를 하나의 Map 객체로 묶음
			Map<String, Object> allData = new HashMap<>();
			allData.put("hotels", hotels);
			allData.put("restaurants", restaurants);
			allData.put("tours", tours);

			// JSON으로 변환
			String allDataJson = objectMapper.writeValueAsString(allData);

			// 외부 API로 데이터를 전송
			sendToExternalApi(allDataJson);

			return allDataJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\"Failed to retrieve data.\"}";
		}
	}

	// 외부 API로 데이터를 보내는 메서드
	private void sendToExternalApi(String jsonData) throws Exception {
		HttpClient client = HttpClient.newHttpClient();

		// 요청 본문 생성
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", "gpt-3.5-turbo");
		requestBody.put("messages",
				new GPTMessage[] { new GPTMessage("system", "Sending all data (hotels, restaurants, and tours)."),
						new GPTMessage("user", jsonData) });

		String requestBodyJson = objectMapper.writeValueAsString(requestBody);

		// API 요청 생성
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(API_URL)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + API_KEY).POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
				.build();

		// API 요청 실행 및 응답 처리
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println("Response Status Code: " + response.statusCode());
		System.out.println("Response Body: " + response.body());
	}
}
