package tot.controller;

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

        // 로그아웃 후 리다이렉트할 페이지
        return "redirect:/jsp/main.jsp";  // 로그아웃 후 login 페이지로 이동
    }
}
 