package tot.common;

public class Constants {

	public static final int PAGE_ROW_COUNT = 8; // 페이지당 리뷰 개수
	public static final int PAGE_BLOCK_COUNT = 5; // 블록당 페이지 개수

	// 여행 코스 페이지
	public static final String PAGE_TRIP = "trip";

	// 여행 후기 페이지 URL
	public static final String BASE_URL = "/review";
	public static final String URL_ALL_TREVIEW = BASE_URL + "/all/1";
	public static final String URL_MY_TREVIEW = BASE_URL + "/my/1";

	// 여행 후기 페이지
	public static final String PAGE_TREVIEW = "treview";
	public static final String PAGE_WRITE_TREVIEW = "treviewWrite";
	

	// 생성자를 private으로 설정하여 인스턴스화 방지
	private Constants() {
	}

}
