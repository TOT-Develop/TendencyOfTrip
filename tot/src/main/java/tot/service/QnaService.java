package tot.service;

import java.util.List;
import java.util.Map;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.Qna;
import tot.domain.QnaComment;

public interface QnaService {
	
	List<Qna> qnaList();
	
	public abstract Qna getQnaDetail(int QNAID);
	
	public abstract int insertQna(Qna qna);
	
	public abstract Qna getQna(int QNAID);
	
	public String getMemNickByMemId(String memId);
	
	PageResDTO<Qna> findQnaListWithPaging(PageReqDTO pageReqDTO) throws Exception;
	
	List<Qna> findQnaListWithPagingByMemId(PageReqDTO dto, String memId);  // 추가된 메소드
	
	List<Qna> findMyQnaList(String memId);
	
	public abstract int insertQnaComment(QnaComment qnaComment);
	
	List<QnaComment> getCommentsByQnaId(int qnaId);
	
//	public abstract Member getMemberId(int MEMID);

}