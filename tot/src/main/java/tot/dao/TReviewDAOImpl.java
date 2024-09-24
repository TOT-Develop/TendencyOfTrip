package tot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.ReportVO;
import tot.domain.TReviewImageVO;
import tot.domain.TReviewResDTO;
import tot.domain.TReviewVO;

@Repository
public class TReviewDAOImpl implements TReviewDAO {

	private static final String NAMESPACE = "tot.mapper.TReviewMapper";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertTReview(TReviewVO treviewVO) {
		sqlSession.insert(NAMESPACE + ".insertTReview", treviewVO);
	}

	@Override
	public void insertTReviewImage(TReviewImageVO treviewImageVO) {
		sqlSession.insert(NAMESPACE + ".insertTReviewImage", treviewImageVO);
	}

	@Override
	public int selectTotalTReviewCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectTotalTReviewCount", pageDTO);
	}

	@Override
	public List<TReviewResDTO> selectTReviewListWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".selectTReviewListWithPaging", pageDTO);
	}

	@Override
	public void incrementTReviewCount(int trevId) {
		sqlSession.update(NAMESPACE + ".incrementTReviewCount", trevId);
	}

	@Override
	public TReviewResDTO getTReviewById(int trevId) {
		return sqlSession.selectOne(NAMESPACE + ".getTReviewById", trevId);
	}

	@Override
	public void editTReview(TReviewVO treviewVO) {
		sqlSession.update(NAMESPACE + ".editTReview", treviewVO);
	}

	@Override
	public void deleteTReviewImages(int trevId) {
		sqlSession.delete(NAMESPACE + ".deleteTReviewImages", trevId);
	}

	@Override
	public void deleteTReview(int trevId) {
		sqlSession.update(NAMESPACE + ".deleteTReview", trevId);
	}

	@Override
	public void insertReportTReview(ReportVO reportVO) {
		sqlSession.insert(NAMESPACE + ".insertReportTReview", reportVO);
	}

	@Override
	public void reportTReview(int trevId) {
		sqlSession.update(NAMESPACE + ".reportTReview", trevId);
	}

}
