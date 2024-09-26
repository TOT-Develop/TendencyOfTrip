package tot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.dao.QnaDao;
import tot.domain.Member;
import tot.domain.Qna;
import tot.domain.QnaComment;
import tot.exception.ServerException;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaDao qnaDao;
	
	@Override
	public List<Qna> qnaList() {
        return qnaDao.qnaList();
	}
	
	@Override
	public Qna getQnaDetail(int QNAID) {
		return qnaDao.getQnaDetail(QNAID);
	}

	@Override
	public int insertQna(Qna qna) {
		return qnaDao.insertQna(qna);
	}

	@Override
	public Qna getQna(int QNAID) {
		return qnaDao.getQna(QNAID);
	}
	
	@Override
    public String getMemNickByMemId(String memId) {
        return qnaDao.getMemNickByMemId(memId);
    } 
	
	@Override
	public PageResDTO<Qna> findQnaListWithPaging(PageReqDTO pageReqDTO) throws Exception {
		try {		
			PageDTO pageDTO = new PageDTO(pageReqDTO);
			
		    int totalPostCount = qnaDao.selectQnaTotalCount(pageDTO);
		    
		    List<Qna> qnaList = qnaDao.qnaListWithPaging(pageDTO);
		    
		    return new PageResDTO<>(totalPostCount, pageReqDTO.getPage(), qnaList);
		} catch (DataAccessException e) {
            throw new ServerException("문의사항 목록을 가져오는 중 데이터베이스 오류 발생", e);
        } catch (Exception e) {
            // Exception 처리 (예외를 로깅하거나 다른 처리를 할 수 있음)
            throw new ServerException("알 수 없는 오류 발생", e); // 필요에 따라 구체적인 예외로 감싸서 던짐
        }
		
	}
	
	public List<Qna> findMyQnaList(String memId) {
	    return qnaDao.getMyQnaList(memId);  // 
	    
    }
		
    @Override
    public List<Qna> findQnaListWithPagingByMemId(PageReqDTO dto, String memId) {
        return qnaDao.qnaListWithPagingByMemId(dto, memId);  // memId가 있는 경우
    }
    
    @Override
    public int insertQnaComment(QnaComment qnaComment) {
        return qnaDao.insertQnaComment(qnaComment);
    }
    
    @Override
    public List<QnaComment> getCommentsByQnaId(int qnaId) {
        return qnaDao.getCommentsByQnaId(qnaId);
    }
	

//	@Override
//	public Member getMemberId(int MEMID) {
//		return qnaDao.getMemberId(MEMID);
//	}

}