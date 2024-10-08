package tot.admin.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.NoticeVO;

public interface AdminNoticeDao {

	NoticeVO getNoticeById(int noId);

	void insertNotice(NoticeVO notice);

	int deleteNotice(int noId);

	void updateNotice(NoticeVO notice);

	List<NoticeVO> noticeListWithPaging(PageDTO pageDTO);

	int selectNoticeTotalCount(PageDTO pageDTO);

}
