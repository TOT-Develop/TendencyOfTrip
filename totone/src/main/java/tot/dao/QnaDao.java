package tot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tot.domain.Member;
import tot.domain.Qna;

public interface QnaDao {
	public abstract List<Qna> qnaList() ;
	public abstract Qna getQnaDetail(int QNAID);
	public abstract int insertQna(Qna qna) ;
	public abstract Qna getQna(int QNAID);
//	public abstract Member getMemberId(int MEMID);
}
