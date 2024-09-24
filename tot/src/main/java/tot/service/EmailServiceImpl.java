package tot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendBanEmail(String email, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원 제재 안내");
        
        // 줄바꿈을 위해 \n 사용
        String text = "회원님, 안녕하세요. TendencyOfTrip입니다.\n\n" +
                      "다음과 같은 사유로 제재가 발생했습니다:\n" + reason + "\n\n" +
                      "해당 메일 발신일로 부터 3일간 이용이 정지 됩니다."+ "\n\n" +
                      "제재는 3일째 날이 끝난 후, 즉 4일째 자정부터 해제되며, 그 이후부터 이용이 가능합니다."+ "\n\n" +
                      "해당 내용과 관련된 문의사항은 홈페이지 내 Q&A로 문의 부탁드립니다.\n\n" +
                      "감사합니다.";
        
        message.setText(text);
        mailSender.send(message);
    }

}

