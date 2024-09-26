package tot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tot.service.TendencyTestServiceImpl;

@Controller
public class TendencyTestController {

    @Autowired
    private TendencyTestServiceImpl tendencyTestService;

    @GetMapping("/tendency")
    public String showTendencyTestPage() {
        return "tendencyTest"; 
    }

    @GetMapping("/tendencyTestResult")
	public String showResultPage() {
	    return "tendencyTestResult"; 
	}

    
    @PostMapping("/updateTendency")
    @ResponseBody
    public Map<String, Object> updateMemberTendency(@RequestParam String memId, @RequestParam String resultType) {
        try {
            tendencyTestService.updateMemberTendency(memId, resultType);
            return Map.of("success", true);
        } catch (Exception e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}
