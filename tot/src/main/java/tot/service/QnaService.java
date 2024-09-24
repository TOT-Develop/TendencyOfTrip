package tot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.dao.QnaDao;
import tot.domain.Member;
import tot.domain.Qna;

public interface QnaService {
	
	public abstract List<Qna> qnaList() ;
	public abstract Qna getQnaDetail(int QNAID);
	public abstract int insertQna(Qna qna);
	public abstract Qna getQna(int QNAID);
//	public abstract Member getMemberId(int MEMID);

}
