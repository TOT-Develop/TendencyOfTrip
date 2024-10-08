package tot.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.NoticeVO;

@Repository
public class AdminNoticeDaoImpl implements AdminNoticeDao {

	private static final String NAMESPACE = "tot.admin.dao.AdminNoticeDao";

	private final SqlSession sqlSession;

	public AdminNoticeDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public NoticeVO getNoticeById(int noId) {
		return sqlSession.selectOne(NAMESPACE + ".getNoticeById", noId);
	}

	@Override
	public void insertNotice(NoticeVO notice) {
		sqlSession.insert(NAMESPACE + ".insertNotice", notice);
	}

	@Override
	public int deleteNotice(int noId) {
		return sqlSession.delete(NAMESPACE + ".deleteNotice", noId);
	}

	@Override
	public void updateNotice(NoticeVO notice) {
		sqlSession.update(NAMESPACE + ".updateNotice", notice);
	}

	@Override
	public List<NoticeVO> noticeListWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".noticeListWithPaging", pageDTO);
	}

	@Override
	public int selectNoticeTotalCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectNoticeTotalCount", pageDTO);
	}

}
