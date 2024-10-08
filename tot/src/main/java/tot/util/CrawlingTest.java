package tot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import tot.domain.Accommodation;
import tot.service.HotelService;
import tot.service.HotelServiceImpl;

public class CrawlingTest {

	private ApplicationContext context;
	private HotelService hotelService;

	public void init() {
		context = new GenericXmlApplicationContext("tot/conf/spring/beans.xml");
		hotelService = (HotelServiceImpl) context.getBean("HotelService");
	}

	public static void main(String[] args) {

		// 1. WebDriver와 ChromeDriver 설정
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// 페이지 번호 범위 설정
		int startPage = 1;
		int endPage = 1; // 원하는 페이지 수만큼 반복

		// 3. 데이터 추출
		ArrayList<Accommodation> accommodation_data = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		for (int page = startPage; page <= endPage; page++) {
			// 2. 웹 페이지 접속 (page 파라미터 동적 변경)
			String baseUrl = String.format(
					"https://www.yeogi.com/domestic-accommodations?searchType=KEYWORD&sortType=RECOMMEND&keyword=%%EC%%84%%B8%%EC%%A2%%85%%ED%%8A%%B9%%EB%%B3%%84%%EC%%9E%%90%%EC%%B9%%98%%EC%%8B%%9C&autoKeyword=%%EC%%84%%B8%%EC%%A2%%85%%ED%%8A%%B9%%EB%%B3%%84%%EC%%9E%%90%%EC%%B9%%98%%EC%%8B%%9C&page=%d&personal=2&checkIn=2024-09-05&checkOut=2024-09-06",
					page);
			driver.get(baseUrl);

			// 암시적 대기 (최대 10초 대기)
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// WebDriverWait 설정
			WebDriverWait wait = new WebDriverWait(driver, 10);

			try {
				// 숙소 컨테이너 찾기
				WebElement accommodationContainer = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".css-1qumol3")));

				// 각 숙소 요소를 찾기
				List<WebElement> accommodationElements = accommodationContainer
						.findElements(By.cssSelector(".css-nl3cnv"));

				// 숙소 수 만큼 반복
				for (int i = 1; i <= accommodationElements.size(); i++) {
					String nameXPath = String.format(
							"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[1]/div/div/div[1]/h3",
							i);
					String locationXPath = String.format(
							"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[1]/div/div/div[2]", i);
					String priceXPath = String
							.format("//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[2]/div/div", i);
					String linkXPath = String.format("//*[@id='__next']/div[1]/main/section/div[2]/a[%d]", i);
					String detailnameXPath = String.format(
							"//*[@id=\"__next\"]/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[1]/div/div/div[1]/ul",
							i);

					// 숙소 정보 추출
					String name = "";
					String location = "";
					String price = "";
					String detailPageLink = "";
					String link = "";
					String detailname = "";

					try {
						name = driver.findElement(By.xpath(nameXPath)).getText();
						location = driver.findElement(By.xpath(locationXPath)).getText();
						String priceText = getPrice(driver, priceXPath, i);
						price = extractLowestPrice(priceText);
						link = driver.findElement(By.xpath(linkXPath)).getAttribute("href");
						detailname = driver.findElement(By.xpath(detailnameXPath)).getText();

						int priceLeng = price.length();
						if (priceLeng < 2) {
							price = "매진";
						}
						// 상세 페이지 링크 추출
						detailPageLink = driver.findElement(By.xpath(linkXPath)).getAttribute("href");

						// 상세 페이지로 이동
						driver.navigate().to(detailPageLink);

						// 주소 및 이미지 추출
						String addressXPath = "//*[@id='__next']/div[1]/main/div/section[2]/div[2]/div[1]/div[2]/p";
						String imageXPath = "//*[@id='overview']/article/div[1]/div/div[1]/div/img";
						String address = "";
						String image = "";

						try {
							image = driver.findElement(By.xpath(imageXPath)).getAttribute("src");
							address = driver.findElement(By.xpath(addressXPath)).getText();
						} catch (Exception e) {
							System.out.println("주소를 가져오는 데 실패했습니다 (숙소 번호: " + i + "): " + e.getMessage());
						}

						// 데이터 저장
						String areacode = "8";
						Accommodation acc = new Accommodation(name, address, link, price, image, detailname, areacode);
						System.out.println("페이지 " + page + " 번호 " + i + " 이름 " + name + " 가격  = " + price + " 주소  = "
								+ address + " 이미지 - " + image + " 경로 = " + link + " 숙소 유형 = " + detailname);
						accommodation_data.add(acc);

						long endTime = System.currentTimeMillis();

						// 총 소요 시간 출력
						System.out.println("총 소요 시간: " + (endTime - startTime) + " ms");

						// 메인 페이지로 다시 이동
						driver.navigate().back();
					} catch (Exception e) {
						System.out.println(
								"숙소 정보를 가져오는 데 실패했습니다 (Page: " + page + ", 숙소 번호: " + i + "): " + e.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println("페이지 로드 또는 숙소 컨테이너를 찾는 데 실패했습니다 (Page: " + page + "): " + e.getMessage());
			}
		}

		// 데이터 삽입
		try {
			CrawlingTest test = new CrawlingTest();
			test.init(); // ApplicationContext 초기화
			for (Accommodation acc : accommodation_data) {
				test.hotelService.insertHotel(acc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4. WebDriver 종료
		driver.quit();
	}

	// 메소드: 가격을 가져오는 방법을 동적으로 처리 컨텐츠마다 xpath 경로 값이 달라서 배열에다 담음
	private static String getPrice(WebDriver driver, String baseXPath, int index) {
		String[] priceXPaths = { baseXPath,
				String.format("//*[@id=\"__next\"]/div[1]/main/section/div[2]/a[%d]/div[1]/div[2]/div[2]/div[2]/div",
						index),
				String.format(
						"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[2]/div/div/div/div[2]/span[2]",
						index),
				String.format(
						"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[2]/div/div/div/div/span[1]",
						index),
				String.format(
						"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[2]/div/div/div/div[2]/span[1]",
						index),
				String.format(
						"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div[1]/div[2]/div[2]/div[2]/div/div[2]/div/div[1]/span[2]",
						index),
				String.format(
						"//*[@id='__next']/div[1]/main/section/div[2]/a[%d]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/span[2]",
						index) };

		for (String xpath : priceXPaths) {
			try {
				return driver.findElement(By.xpath(xpath)).getText();
			} catch (Exception e) {
			}
		}
		return "없음";
	}

	// 메소드: 가격 텍스트에서 가장 높은 가격을 추출 낮은거로하면 남은 수 나옴
	private static String extractLowestPrice(String priceText) {
		List<Integer> prices = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\d{1,3}(,\\d{3})*");
		Matcher matcher = pattern.matcher(priceText);

		while (matcher.find()) {
			String priceString = matcher.group().replace(",", "");
			try {
				int price = Integer.parseInt(priceString);
				prices.add(price);
			} catch (NumberFormatException e) {
			}
		}

		if (prices.isEmpty()) {
			return "매진";
		} else {
			int maxPrice = prices.stream().mapToInt(v -> v).max().orElseThrow();
			return String.format("%,d", maxPrice);
		}
	}
}
