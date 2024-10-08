package tot.dao;

import java.util.List;

import tot.domain.HistoryVO;

public interface HistoryDao {

	void insertTReviewHistory(HistoryVO historyVO);

	void insertCommentHistory(HistoryVO historyVO);

	List<HistoryVO> getTReviewHistorysById(int trevId);

}
