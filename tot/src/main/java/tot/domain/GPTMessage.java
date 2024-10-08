package tot.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GPTMessage {

	private String role;
	private String content;

	public GPTMessage() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public GPTMessage(String role, String content) {
		this.role = role;
		this.content = content;
	}

	@Override
	public String toString() {
		return "GptMessage [role=" + role + ", content=" + content + "]";
	}

}
