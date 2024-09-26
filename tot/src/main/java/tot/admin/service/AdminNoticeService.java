package tot.admin.service;

import java.util.List;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.Notice;

public interface AdminNoticeService {
	
	// 전체 가져오기_notice의 메인페이지
	public List<Notice> noticeList() throws Exception;
	
	// 페이징 처리된 공지사항 목록 가져오기
	PageResDTO<Notice> findNoticeListWithPaging(PageReqDTO pageReqDTO) throws Exception;
	
    // 하나 가져오기_notice의 상세페이지
    public Notice getNoticeById(int noid) throws Exception;
    
    // 글 쓰기
    public void insertNotice(Notice notice) throws Exception; 
    
    // 글 삭제
    public void deleteNotice(int noid) throws Exception;
    
    
    // 글 수정
    public void updateNotice(Notice notice) throws Exception;
}	
