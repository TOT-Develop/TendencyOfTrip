package tot.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.domain.Member;
import tot.domain.Qna;
import tot.domain.QnaComment;

public interface QnaDao {
	List<Qna> qnaList();
	public abstract Qna getQnaDetail(int QNAID);
	public abstract int insertQna(Qna qna) ;
	public abstract Qna getQna(int QNAID);
	
    // memId를 통해 memNick을 가져오는 메서드
    String getMemNickByMemId(String memId);
    
    List<Qna> qnaListWithPaging(PageDTO pageDTO);
    
    List<Qna> qnaListWithPagingByMemId(PageReqDTO dto, String memId);
    
    int selectQnaTotalCount(PageDTO pageDTO);
    
    public List<Qna> getMyQnaList(String memId);
    
    public int insertQnaComment(QnaComment qnaComment);
    
    List<QnaComment> getCommentsByQnaId(int qnaId);    
	
//	public abstract Member getMemberId(int MEMID);
}