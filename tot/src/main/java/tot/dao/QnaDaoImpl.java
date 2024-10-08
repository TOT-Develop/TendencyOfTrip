package tot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;
import tot.domain.QnaVO;

@Repository
public class QnaDaoImpl implements QnaDao {

	private static final String NAMESPACE = "tot.dao.QnaDao";
	private SqlSession sqlSession;

	public QnaDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int selectQnaTotalCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectQnaTotalCount", pageDTO);
	}

	@Override
	public List<QnaDTO> qnaListWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".qnaListWithPaging", pageDTO);
	}

	@Override
	public int insertQna(QnaVO qna) {
		return sqlSession.insert(NAMESPACE + ".insertQna", qna);
	}

	@Override
	public QnaDTO getQnaDetail(int QNAID) {
		return sqlSession.selectOne(NAMESPACE + ".getQnaDetail", QNAID);
	}

	@Override
	public List<QnaCommentVO> getCommentsByQnaId(int qnaId) {
		return sqlSession.selectList(NAMESPACE + ".getCommentsByQnaId", qnaId);
	}

}