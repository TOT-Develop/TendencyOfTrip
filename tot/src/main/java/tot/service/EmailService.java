package tot.service;

public interface EmailService {

	void sendBanEmail(String email, String reason);

	void sendSanctionEmail(String email, String postTitle, String reason);

	void sendQnaEmail(String memNick, String email, String qnaTitle, String qnaText, String comment);

}
