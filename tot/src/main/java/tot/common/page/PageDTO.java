package tot.common.page;

import tot.common.Constants;
import tot.common.enums.SearchType;
import tot.util.MemberUtil;

public class PageDTO {

	private int offset; // 데이터베이스 쿼리의 시작 위치
	private int pageRowCount; // 페이지당 데이터 수
	private PageReqDTO dto; // PageReqDTO 인스턴스를 가진 페이지 요청 파라미터
	private String boardId; // 게시판 아이디
	private int postId;
	private String memId;
	private SearchType searchType;

	public PageDTO(PageReqDTO dto) {
		this.pageRowCount = Constants.PAGE_ROW_COUNT;
		this.dto = dto;
		offset = (dto.getPage() - 1) * pageRowCount;
	}

	public PageDTO(PageReqDTO dto, String boardId) {
		this.pageRowCount = Constants.PAGE_ROW_COUNT;
		this.boardId = boardId;
		this.dto = dto;
		this.memId = MemberUtil.getAuthenticatedMember().getMemId();
		offset = (dto.getPage() - 1) * pageRowCount;
	}

	public PageDTO(PageReqDTO dto, String boardId, int postId) {
		this.pageRowCount = Constants.PAGE_ROW_COUNT;
		this.boardId = boardId;
		this.postId = postId;
		this.dto = dto;
		this.memId = MemberUtil.getAuthenticatedMember().getMemId();
		offset = (dto.getPage() - 1) * pageRowCount;
	}

	public PageDTO(int offset, int pageRowCount, PageReqDTO dto, String boardId, int postId, String memId) {
		this.offset = offset;
		this.pageRowCount = pageRowCount;
		this.dto = dto;
		this.boardId = boardId;
		this.postId = postId;
		this.memId = memId;
	}

	public int getOffset() {
		return offset;
	}

	public int getPageRowCount() {
		return pageRowCount;
	}

	public PageReqDTO getDto() {
		return dto;
	}

	public String getBoardId() {
		return boardId;
	}

	public int getPostId() {
		return postId;
	}

	public String getMemId() {
		return memId;
	}

	@Override
	public String toString() {
		return "PageDTO [offset=" + offset + ", pageRowCount=" + pageRowCount + ", dto=" + dto + ", boardId=" + boardId
				+ ", postId=" + postId + ", memId=" + memId + "]";
	}

}
