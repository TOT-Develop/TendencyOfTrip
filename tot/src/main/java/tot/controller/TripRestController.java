package tot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tot.domain.FestivalDTO;
import tot.domain.FestivalVO;
import tot.domain.TripVO;
import tot.service.TripService;
import tot.service.CourseService;
import tot.service.FestivalService;

@RestController
@RequestMapping("/triplist")
public class TripRestController {

    @Autowired
    private TripService tripService;

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private FestivalService festivalService;

    @GetMapping("/trips")
    public ResponseEntity<List<TripVO>> getTripsByMemId(HttpSession session) {
        String memId = (String) session.getAttribute("memId");
        if (memId != null) {
            List<TripVO> trips = tripService.getTripsByMemId(memId);
            return ResponseEntity.ok(trips);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/locations/{tripId}")
    public ResponseEntity<Map<Integer, List<Object>>> getCourseLocations(@PathVariable int tripId) {
        Map<Integer, List<Object>> dailyCourses = courseService.getDailyCourseByTripId(tripId);
        System.out.println(dailyCourses);
        return ResponseEntity.ok(dailyCourses);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<TripVO> getTripById(@PathVariable int tripId) {
        TripVO trip = tripService.getTripById(tripId);
        if (trip != null) {
            return ResponseEntity.ok(trip);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/festivals")
    public ResponseEntity<List<FestivalVO>> getFestivals(
            @RequestParam(required = false) String areacode,
            @RequestParam(required = false) String tripStartDate,
            @RequestParam(required = false) String tripEndDate) {
        
        Map<String, Object> params = new HashMap<>();
        params.put("areacode", areacode);

        // 날짜 범위가 주어졌을 때
        if (tripStartDate != null && tripEndDate != null) {
            params.put("tripStartDate", tripStartDate);
            params.put("tripEndDate", tripEndDate);
            List<FestivalDTO> festivalDTOs = festivalService.findFestivalsByDateRange(params);
            List<FestivalVO> festivalVOs = convertDTOsToVOs(festivalDTOs);
            return ResponseEntity.ok(festivalVOs);
        } else if (tripStartDate != null) { // 월 단위 조회
            params.put("tripStartDate", tripStartDate);
            List<FestivalDTO> festivalDTOs = festivalService.findFestivalsByMonth(params);
            List<FestivalVO> festivalVOs = convertDTOsToVOs(festivalDTOs);
            return ResponseEntity.ok(festivalVOs);
        } else {
            return ResponseEntity.badRequest().build(); // 필수 파라미터 부족
        }
    }

    // DTO를 VO로 변환하는 메서드
    private List<FestivalVO> convertDTOsToVOs(List<FestivalDTO> dtoList) {
        return dtoList.stream()
                .map(dto -> new FestivalVO(
                        dto.getContentid(),
                        dto.getContenttypeid(),
                        dto.getAreacode(),
                        dto.getTitle(),
                        dto.getAddr1(),
                        dto.getAddr2(),
                        dto.getMapx(),
                        dto.getMapy(),
                        dto.getFirstimage(),
                        dto.getFirstimage2(),
                        dto.getEventstartdate(),
                        dto.getEventenddate(),
                        dto.getTel(),
                        dto.getOverviewYN(),
                        dto.getPlaytime(),
                        dto.getUsetimefestival()
                ))
                .collect(Collectors.toList());
    }
}

