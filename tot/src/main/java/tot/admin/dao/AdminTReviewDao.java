package tot.admin.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.TReviewResDTO;

public interface AdminTReviewDao {

	int selectTotalTReviewCount(PageDTO pageDTO);

	List<TReviewResDTO> selectTReviewListWithPaging(PageDTO pageDTO);

	void updateTReviewStatus(String status, List<Integer> trevIds);

	TReviewResDTO getTReviewById(int trevId);

	String findMemberEmailByTrevId(int trevId);

}
