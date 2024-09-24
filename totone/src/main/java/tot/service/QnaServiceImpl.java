package tot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tot.dao.QnaDao;
import tot.domain.Member;
import tot.domain.Qna;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaDao qnaDao;
	
	@Override
	public List<Qna> qnaList() {
		if(qnaDao == null) {
			throw new IllegalStateException("QnaDao is not initialized.");
		}
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

//	@Override
//	public Member getMemberId(int MEMID) {
//		return qnaDao.getMemberId(MEMID);
//	}

}
