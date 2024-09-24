package tot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.domain.Member;
import tot.domain.Qna;

@Repository
public class QnaDaoImpl implements QnaDao {

	
	private SqlSession sqlSession;

    @Autowired
    public QnaDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public List<Qna> qnaList() {
		return sqlSession.selectList("tot.dao.QnaDao.qnaList");
	}

	@Override
	public Qna getQnaDetail(int QNAID) {
		return sqlSession.selectOne("tot.dao.QnaDao.getQnaDetail", QNAID);
	}
	
	@Override
	public int insertQna(Qna qna) {
		return sqlSession.insert("tot.dao.QnaDao.insertQna", qna);
	}

	@Override
	public Qna getQna(int QNAID) {
		return sqlSession.selectOne("tot.dao.QnaDao.getQnaList", QNAID); 
	}

//	@Override
//	public Member getMemberId(int MEMID) {
//		return sqlSession.selectOne("tot.dao.QnaDao.getMemberId", MEMID);
//	}

}
