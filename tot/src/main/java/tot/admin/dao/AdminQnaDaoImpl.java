package tot.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.dao.QnaDao;
import tot.domain.Qna;
import tot.domain.QnaComment;

@Repository
public class AdminQnaDaoImpl implements AdminQnaDao {

	
	private SqlSession sqlSession;
	private AdminQnaDao adminQnaDao;
	private QnaDao qnaDao;

    @Autowired
    public AdminQnaDaoImpl(SqlSession sqlSession) {
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
	
    // memId로 memNick을 가져오는 메서드
    @Override
    public String getMemNickByMemId(String memId) {
        return qnaDao.getMemNickByMemId(memId);
    }
    
    @Override
    public List<Qna> qnaListWithPaging(PageDTO pageDTO) {
        return sqlSession.selectList("tot.dao.QnaDao.qnaListWithPaging", pageDTO);
    }
    
    @Override
    public List<Qna> qnaListWithPagingByMemId(PageReqDTO dto, String memId) {
        Map<String, Object> params = new HashMap<>();
        params.put("dto", dto);
        params.put("memId", memId);
        return sqlSession.selectList("tot.dao.QnaDao.qnaListWithPagingByMemId", params);  // memId와 함께 처리
    }

    @Override
    public int selectQnaTotalCount(PageDTO pageDTO) {
        return sqlSession.selectOne("tot.dao.QnaDao.selectQnaTotalCount", pageDTO);
    }
    
    @Override
    public List<Qna> getMyQnaList(String memId) {
        return sqlSession.selectList("tot.dao.QnaDao.getMyQnaList", memId);
    }
    
    @Override
    public int insertQnaComment(QnaComment qnaComment) {
        return sqlSession.insert("tot.admin.dao.AdminQnaDao.insertQnaComment", qnaComment);
    }
    
    @Override
    public List<QnaComment> getCommentsByQnaId(int qnaId) {
        return sqlSession.selectList("tot.admin.dao.AdminQnaDao.getCommentsByQnaId", qnaId);
    }
    
    @Override
    public int updateQnaStatus(int qnaId, String newStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("qnaId", qnaId);
        params.put("newStatus", newStatus);

        return sqlSession.update("tot.admin.dao.AdminQnaDao.updateQnaStatus", params);
    }
    

//	@Override
//	public Member getMemberId(int MEMID) {
//		return sqlSession.selectOne("tot.dao.QnaDao.getMemberId", MEMID);
//	}

}