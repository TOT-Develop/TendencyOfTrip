package tot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.dao.HistoryDao;
import tot.domain.HistoryVO;

@Service
public class HistoryServiceImpl implements HistoryService {

	private final HistoryDao historyDao;

	public HistoryServiceImpl(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}

	/**
	 * 여행 후기의 히스토리를 데이터베이스에 추가합니다.
	 *
	 * @param historyVO 추가할 히스토리 정보
	 */
	@Override
	public void insertTReviewHistory(HistoryVO historyVO) {
		historyDao.insertTReviewHistory(historyVO);
	}

	/**
	 * 댓글의 히스토리를 데이터베이스에 추가합니다.
	 *
	 * @param historyVO 추가할 히스토리 정보
	 */
	@Override
	public void insertCommentHistory(HistoryVO historyVO) {
		historyDao.insertCommentHistory(historyVO);
	}

	/**
	 * 특정 후기 ID에 대한 히스토리 목록을 조회합니다.
	 *
	 * @param trevId 후기 ID
	 * @return 해당 후기의 히스토리 목록
	 */
	@Override
	public List<HistoryVO> getTReviewHistorysById(int trevId) {
		return historyDao.getTReviewHistorysById(trevId);
	}

}
