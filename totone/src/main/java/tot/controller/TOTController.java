package tot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TOTController {

	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
}
