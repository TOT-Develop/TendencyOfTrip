package tot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendBanEmail(String email, String reason) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("회원 제재 안내");

		String text = "회원님, 안녕하세요. TendencyOfTrip입니다.\n\n" + "다음과 같은 사유로 제재가 발생했습니다:\n" + reason + "\n\n"
				+ "해당 메일 발신일로 부터 3일간 이용이 정지 됩니다." + "\n\n" + "제재는 3일째 날이 끝난 후, 즉 4일째 자정부터 해제되며, 그 이후부터 이용이 가능합니다."
				+ "\n\n" + "해당 내용과 관련된 문의사항은 홈페이지 내 Q&A로 문의 부탁드립니다.\n\n" + "감사합니다.";

		message.setText(text);
		mailSender.send(message);
	}

	@Override
	public void sendSanctionEmail(String email, String postTitle, String reason) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("게시물 제재 안내");

		String text = "회원님, 안녕하세요. TendencyOfTrip입니다.\n\n" + "회원님께서 작성하신 게시물 '" + postTitle
				+ "' 이(가) 다음과 같은 사유로 제재되었습니다:\n" + reason + "\n\n"
				+ "제재된 게시물은 즉시 삭제되었으며, 동일한 사유로 반복될 경우 추가적인 제재가 발생할 수 있습니다." + "\n\n"
				+ "해당 내용과 관련된 문의사항은 홈페이지 내 Q&A로 문의 부탁드립니다.\n\n" + "감사합니다.";

		message.setText(text);
		mailSender.send(message);
	}

	@Override
	public void sendQnaEmail(String memNick, String email, String qnaTitle, String qnaText, String comment) {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(email);
	    message.setSubject("문의글 답변 안내");

	    // 이메일 본문 구성
	    StringBuilder text = new StringBuilder();
	    text.append("안녕하세요, ").append(memNick).append("님,\n\n");
	    text.append("고객님의 문의글에 대한 답변이 등록되었습니다.\n\n");
	    text.append("문의 제목: ").append(qnaTitle).append("\n\n");
	    text.append("문의 내용: ").append(qnaText).append("\n\n");
	    text.append("답변 내용: ").append(comment).append("\n\n");
	    text.append("감사합니다.");

	    // 이메일 본문 설정
	    message.setText(text.toString());
	    
	    // 이메일 전송
	    mailSender.send(message);
	}


}
