package tot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.dao.NoticeDao;
import tot.domain.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {

	private final NoticeDao noticeDao;

	public NoticeServiceImpl(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public PageResDTO<NoticeVO> findNoticeListWithPaging(PageReqDTO pageReqDTO) {
		PageDTO pageDTO = new PageDTO(pageReqDTO);

		int totalNoticeCount = noticeDao.selectNoticeTotalCount(pageDTO);

		List<NoticeVO> noticeList = noticeDao.noticeListWithPaging(pageDTO);
		return new PageResDTO<>(totalNoticeCount, pageReqDTO.getPage(), noticeList);
	}

	@Override
	public NoticeVO getNoticeDetail(int noId) {
		return noticeDao.getNoticeDetail(noId);
	}

}
