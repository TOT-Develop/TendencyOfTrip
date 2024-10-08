package tot.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tot.admin.dao.AdminNoticeDao;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.NoticeVO;

@Service
public class AdminNoticeServiceImpl implements AdminNoticeService {

	@Autowired
	private AdminNoticeDao adminNoticeDao;

	public AdminNoticeServiceImpl(AdminNoticeDao adminNoticeDao) {
		this.adminNoticeDao = adminNoticeDao;
	}

	@Override
	public PageResDTO<NoticeVO> findNoticeListWithPaging(PageReqDTO pageReqDTO) {
		PageDTO pageDTO = new PageDTO(pageReqDTO);

		int totalNoticeCount = adminNoticeDao.selectNoticeTotalCount(pageDTO);

		List<NoticeVO> postList = adminNoticeDao.noticeListWithPaging(pageDTO);
		return new PageResDTO<>(totalNoticeCount, pageReqDTO.getPage(), postList);
	}

	@Override
	public NoticeVO getNoticeById(int noId) {
		return adminNoticeDao.getNoticeById(noId);
	}

	@Override
	public void insertNotice(NoticeVO notice) {
		adminNoticeDao.insertNotice(notice);
	}

	@Override
	public void deleteNotice(int noId) {
		adminNoticeDao.deleteNotice(noId);
	}

	@Override
	public void updateNotice(NoticeVO notice) {
		adminNoticeDao.updateNotice(notice);
	}

}
