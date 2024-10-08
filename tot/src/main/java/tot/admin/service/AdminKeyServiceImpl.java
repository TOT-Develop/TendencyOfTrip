package tot.admin.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class AdminKeyServiceImpl implements AdminKeyService {

	// Google OAuth credentials
	private static final String GOOGLE_CLIENT_ID = "GOOGLE_CLIENT_ID";
	private static final String GOOGLE_CLIENT_SECRET = "GOOGLE_CLIENT_SECRET";
	private static final String GOOGLE_REDIRECT_URI = "GOOGLE_REDIRECT_URI";

	// Naver OAuth credentials
	private static final String NAVER_CLIENT_ID = "NAVER_CLIENT_ID";
	private static final String NAVER_CLIENT_SECRET = "NAVER_CLIENT_SECRET";
	private static final String NAVER_REDIRECT_URI = "NAVER_REDIRECT_URI";
	private static final String NAVER_SEARCH_API_URL = "NAVER_SEARCH_API_URL";

	// Kakao OAuth credentials
	private static final String KAKAO_CLIENT_ID = "KAKAO_CLIENT_ID";
	private static final String KAKAO_REDIRECT_URI = "KAKAO_REDIRECT_URI";
	private static final String KAKAO_API_URL = "KAKAO_API_URL";

	@Override
	public String checkGoogleApiKey() {
		try {
			// Google OAuth typically requires user interaction.
			// Here, we perform a basic check by accessing a public Google API endpoint.
			URL url = new URL("https://www.googleapis.com/oauth2/v3/certs");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");

			int responseCode = connection.getResponseCode();
			connection.disconnect();

			if (responseCode == 200) {
				return "정상";
			} else {
				return "오류 (응답 코드: " + responseCode + ")";
			}

		} catch (IOException e) {
			return "오류 (" + e.getMessage() + ")";
		}
	}

	@Override
	public String checkNaverApiKey() {
		try {
			// Make a search request to Naver API to verify the client ID and secret
			URL url = new URL(NAVER_SEARCH_API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
			connection.setRequestProperty("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
			connection.setRequestProperty("Content-Type", "application/json");

			int responseCode = connection.getResponseCode();
			connection.disconnect();

			if (responseCode == 200) {
				return "정상";
			} else {
				return "오류 (응답 코드: " + responseCode + ")";
			}

		} catch (IOException e) {
			return "오류 (" + e.getMessage() + ")";
		}
	}

	@Override
	public String checkKakaoApiKey() {
		try {
			// Make a request to Kakao Maps API to verify the API key
			URL url = new URL(KAKAO_API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "KakaoAK " + KAKAO_CLIENT_ID);

			int responseCode = connection.getResponseCode();
			connection.disconnect();

			if (responseCode == 200) {
				return "정상";
			} else {
				return "오류 (응답 코드: " + responseCode + ")";
			}

		} catch (IOException e) {
			return "오류 (" + e.getMessage() + ")";
		}
	}

}
