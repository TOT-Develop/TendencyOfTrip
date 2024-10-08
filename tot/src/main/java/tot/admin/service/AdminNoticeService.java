package tot.admin.service;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.NoticeVO;

public interface AdminNoticeService {

	// 페이징 처리된 공지사항 목록 가져오기
	PageResDTO<NoticeVO> findNoticeListWithPaging(PageReqDTO pageReqDTO);

	// 하나 가져오기_notice의 상세페이지
	NoticeVO getNoticeById(int noId);

	// 글 쓰기
	void insertNotice(NoticeVO notice);

	// 글 삭제
	void deleteNotice(int noId);

	// 글 수정
	void updateNotice(NoticeVO notice);
}
