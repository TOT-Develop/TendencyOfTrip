package tot.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;

@Repository
public class AdminQnaDaoImpl implements AdminQnaDao {

	private static final String NAMESPACE = "tot.admin.dao.AdminQnaDao";
	private final SqlSession sqlSession;

	public AdminQnaDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public QnaDTO getQnaDetail(int QNAID) {
		return sqlSession.selectOne(NAMESPACE + ".getQnaDetail", QNAID);
	}

	@Override
	public List<QnaDTO> qnaListWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".qnaListWithPaging", pageDTO);
	}

	@Override
	public int selectQnaTotalCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectQnaTotalCount", pageDTO);
	}

	@Override
	public List<QnaCommentVO> getCommentsByQnaId(int qnaId) {
		return sqlSession.selectList(NAMESPACE + ".getCommentsByQnaId", qnaId);
	}

	@Override
	public int insertQnaComment(QnaCommentVO qnaComment) {
		return sqlSession.insert(NAMESPACE + ".insertQnaComment", qnaComment);
	}

	@Override
	public int updateQnaStatus(int qnaId, String qna_002) {
		Map<String, Object> params = new HashMap<>();
		params.put("qnaId", qnaId);
		params.put("qna_002", qna_002); // MyBatis XML에서 사용하는 변수 이름에 맞추어 수정

		return sqlSession.update(NAMESPACE + ".updateQnaStatus", params);
	}

}