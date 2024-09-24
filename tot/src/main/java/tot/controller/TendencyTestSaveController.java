package tot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tot.service.TendencyTestServiceImpl;

@RestController
public class TendencyTestSaveController {

    @Autowired
    private TendencyTestServiceImpl tendencyTestService;

    @PostMapping("/saveTendencyTestResult")
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

