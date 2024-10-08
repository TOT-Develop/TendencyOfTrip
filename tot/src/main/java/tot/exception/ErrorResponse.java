package tot.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private String code;
	private String message;
	private HttpStatus status;

	public ErrorResponse(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ErrorResponse [code=" + code + ", message=" + message + ", status=" + status + "]";
	}

}
