package tot.common.page;

import java.util.List;

import tot.common.Constants;

public class PageResDTO<T> {

	private final long totalPostCount; // 총 게시물 개수
	private final int currentPage; // 현재 페이지
	private final List<T> postList; // 페이지에 보여줄 게시물 리스트
	private int startBlockPage = 1; // 페이지 블럭의 시작 페이지
	private int endBlockPage; // 페이지 블럭의 마지막 페이지
	private int totalPageCount; // 총 페이지 개수
	private boolean isPrev = false; // 다음 페이지 버튼 유무
	private boolean isNext = false; // 이전 페이지 버튼 유무

	public PageResDTO(int totalPostCount, int currentPage, List<T> postList) {
		this.totalPostCount = totalPostCount == 0 ? 1 : totalPostCount;
		this.currentPage = currentPage;
		this.postList = postList;
		setPage();
	}

	/**
	 * 페이지 세팅 총 페이지, 시작/끝 블록 페이지 계산, 이전/다음 버튼 유무 계산
	 */
	private void setPage() {
		// 총 페이지 개수 계산
		totalPageCount = (int) Math.ceil(totalPostCount * 1.0 / Constants.PAGE_ROW_COUNT);

		// 시작 블록 페이지 계산
		startBlockPage = startBlockPage
				+ (((currentPage - startBlockPage) / Constants.PAGE_BLOCK_COUNT) * Constants.PAGE_BLOCK_COUNT);

		// 마지막 블록 페이지 계산
		endBlockPage = (Math.min((startBlockPage - 1) + Constants.PAGE_BLOCK_COUNT, totalPageCount));

		// 이전 버튼 유무
		isPrev = ((currentPage * 1.0) / Constants.PAGE_BLOCK_COUNT) > 1;

		// 다음 버튼 유무
		isNext = endBlockPage < totalPageCount;
	}

	public long getTotalPostCount() {
		return totalPostCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<T> getPostList() {
		return postList;
	}

	public int getStartBlockPage() {
		return startBlockPage;
	}

	public int getEndBlockPage() {
		return endBlockPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public boolean getIsPrev() {
		return isPrev;
	}

	public boolean getIsNext() {
		return isNext;
	}

	@Override
	public String toString() {
		return "PageResDTO [totalPostCount=" + totalPostCount + ", currentPage=" + currentPage + ", postList="
				+ postList + ", startBlockPage=" + startBlockPage + ", endBlockPage=" + endBlockPage
				+ ", totalPageCount=" + totalPageCount + ", isPrev=" + isPrev + ", isNext=" + isNext + "]";
	}

}
