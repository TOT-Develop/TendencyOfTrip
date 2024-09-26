package tot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tot.domain.FestivalDTO;
import tot.service.FestivalService;

@RestController
public class FestivalRestController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping("/festivals")
    public ResponseEntity<List<FestivalDTO>> getFestivals(
            @RequestParam(required = false) String month,
            @RequestParam String areacode,
            @RequestParam(required = false) String tripStartDate,
            @RequestParam(required = false) String tripEndDate) {

        List<FestivalDTO> festivals;
        Map<String, Object> params = new HashMap<>();
        params.put("areacode", areacode);

        if (tripStartDate != null && tripEndDate != null) {
            // 여행 날짜가 주어졌을 때
            params.put("tripStartDate", tripStartDate);
            params.put("tripEndDate", tripEndDate);
            festivals = festivalService.findFestivalsByDateRange(params);
        } else {
            // 여행 날짜가 주어지지 않았을 때
            if (month == null) {
                // 월 정보가 없으면 현재 월을 사용
                month = new java.text.SimpleDateFormat("MM").format(new java.util.Date());
            }
            params.put("tripStartDate", month + "-01"); // 임의의 시작일로 설정
            festivals = festivalService.findFestivalsByMonth(params);
        }

        return ResponseEntity.ok(festivals);
    }
}
