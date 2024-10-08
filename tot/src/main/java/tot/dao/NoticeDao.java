package tot.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.NoticeVO;

public interface NoticeDao {

	int selectNoticeTotalCount(PageDTO pageDTO);

	List<NoticeVO> noticeListWithPaging(PageDTO pageDTO);

	NoticeVO getNoticeDetail(int noId);

}
