package tot.controller;

import static tot.common.Constants.PAGE_MAIN;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tot.domain.MemberVO;
import tot.util.MemberUtil;

@Controller
@RequestMapping("/")
public class MainController {

	@GetMapping
	public String showMain(HttpSession session, Model model) {
		MemberVO member = MemberUtil.getMember();

		model.addAttribute("member", member);
		return PAGE_MAIN;
	}

}
