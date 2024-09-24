package tot.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tot.domain.RecommandCourseInputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/recommendCourse")
public class RecommendCourseRestController {

    @PostMapping
    public ResponseEntity<Map<String, Object>> recommendCourse(@RequestBody RecommandCourseInputDTO requestData) {
        try {
            // JSON 데이터 콘솔에 출력
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(requestData);
            System.out.println("받은 데이터: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 전송받은 데이터를 콘솔에 출력
        String mbti = requestData.getMbti();
        String tramt = requestData.getTramt();
        String trpeople = requestData.getTrpeople();
        String trstadate = requestData.getTrstadate();
        String trenddate = requestData.getTrenddate();
        String trperiod = requestData.getTrperiod();
        String areacode = requestData.getAreacode();
        String restaurant_001 = requestData.getRestaurant_001();
        String resultType = requestData.getResultType();

        // 콘솔에 데이터 출력
        System.out.println("MBTI: " + mbti);
        System.out.println("tramt: " + tramt);
        System.out.println("trpeople: " + trpeople);
        System.out.println("trstadate: " + trstadate);
        System.out.println("trenddate: " + trenddate);
        System.out.println("trperiod: " + trperiod);
        System.out.println("areacode: " + areacode);
        System.out.println("restaurant_001: " + restaurant_001);
        System.out.println("Result Type: " + resultType);

        // 실제 GPT와의 통신 로직은 생략하고, 성공적인 응답 반환
        return ResponseEntity.ok(Map.of("success", true));
    }
}
