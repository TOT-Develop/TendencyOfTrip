package tot.domain;

public class CommentReqDTO {

	private int parentId; // 부모 댓글 아이디
	private String parentNickname; // 부모 댓글 닉네임
	private String content; // 내용

	public CommentReqDTO(int parentId, String parentNickname, String content) {
		this.parentId = parentId;
		this.parentNickname = parentNickname;
		this.content = content;
	}

	public CommentReqDTO() {
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentNickname() {
		return parentNickname;
	}

	public void setParentNickname(String commentNickname) {
		this.parentNickname = commentNickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentReqDTO [parentId=" + parentId + ", parentNickname=" + parentNickname + ", content=" + content
				+ "]";
	}

}
