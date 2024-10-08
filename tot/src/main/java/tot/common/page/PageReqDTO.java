package tot.common.page;

import tot.common.enums.Flag;
import tot.common.enums.SearchType;
import tot.common.enums.SortType;

public class PageReqDTO {

	private int page; // 현재 페이지 번호
	private String search; // 검색어
	private SearchType searchType; // 검색 유형 (전체, 제목, 내용)
	private SortType sortType; // 정렬 방식 (최신순, 오래된순, 조회순, 평점순)
	private Flag activateFlag; // 활성화 상태 플래그
	private String status; // 접수 상태(접수대기, 접수완료)
	private String category; // QnA 카테고리

	public PageReqDTO() {
		this.page = 1;
		this.searchType = SearchType.ALL;
		this.sortType = SortType.LATEST;
		this.activateFlag = Flag.CMT001;
	}

	public PageReqDTO(int page, String search, SearchType searchType, SortType sortType, Flag activateFlag) {
		this.page = page;
		this.search = search;
		this.searchType = searchType;
		this.sortType = sortType;
		this.activateFlag = activateFlag;
	}

	public int getPage() {
		return page;
	}

	public String getSearch() {
		return search;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public SortType getSortType() {
		return sortType;
	}

	public Flag getActivateFlag() {
		return activateFlag;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	public void setActivateFlag(Flag activateFlag) {
		this.activateFlag = activateFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "PageReqDTO [page=" + page + ", search=" + search + ", searchType=" + searchType + ", sortType="
				+ sortType + ", activateFlag=" + activateFlag.name() + "]";
	}

}
