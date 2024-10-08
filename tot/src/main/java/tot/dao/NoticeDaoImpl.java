package tot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.NoticeVO;

@Repository
public class NoticeDaoImpl implements NoticeDao {

	private static final String NAMESPACE = "tot.dao.NoticeDao";
	private final SqlSession sqlSession;

	public NoticeDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int selectNoticeTotalCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectNoticeTotalCount", pageDTO);
	}

	@Override
	public List<NoticeVO> noticeListWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".noticeListWithPaging", pageDTO);
	}

	@Override
	public NoticeVO getNoticeDetail(int noid) {
		return sqlSession.selectOne(NAMESPACE + ".getNoticeDetail", noid);
	}

}
