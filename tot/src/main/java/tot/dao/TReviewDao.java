package tot.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.ReportVO;
import tot.domain.TReviewImageVO;
import tot.domain.TReviewResDTO;
import tot.domain.TReviewVO;

public interface TReviewDao {

	void insertTReview(TReviewVO treviewVO);

	void insertTReviewImage(TReviewImageVO treviewImageVO);

	int selectTotalTReviewCount(PageDTO pageDTO);

	List<TReviewResDTO> selectTReviewListWithPaging(PageDTO pageDTO);

	void incrementTReviewCount(int trevId);

	TReviewResDTO getTReviewById(int trevId);

	void editTReview(TReviewVO treviewVO);

	void deleteTReviewImages(int trevId);

	void deleteTReview(int trevId);

	void insertReportTReview(ReportVO reportVO);

	void reportTReview(int trevId);

}
