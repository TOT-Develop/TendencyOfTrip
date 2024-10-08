package tot.controller;

import static tot.common.Constants.PAGE_MAIN;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@GetMapping
	public String logout(HttpSession session) {
		// 세션 무효화
		session.invalidate();

		// 로그아웃 후 메인페이지로 이동
		return PAGE_MAIN;
	}
}
