package tot.admin.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.Notice;

public interface AdminNoticeDao {
	
	public List<Notice> noticeList() throws Exception;
	
	public Notice getNoticeById(int noid) throws Exception;
	
	public void insertNotice(Notice notice) throws Exception;
	 
	public int deleteNotice(int noid) throws Exception;
	
	public void updateNotice(Notice notice) throws Exception;
    
    // 페이징 처리된 공지사항 목록 조회
    List<Notice> noticeListWithPaging(PageDTO pageDTO) throws Exception;

    // 총 공지사항 수 조회
    int selectNoticeTotalCount(PageDTO pageDTO) throws Exception;
    
    

}
