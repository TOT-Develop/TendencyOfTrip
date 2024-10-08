package tot.admin.service;

import java.util.List;

import tot.admin.enums.BanReason;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.TReviewResDTO;

public interface AdminTReviewService {

	PageResDTO<TReviewResDTO> findTReviewListWithPaging(PageReqDTO dto, int boardId);

	void updateTReviewStatus(String status, List<Integer> trevIds, BanReason reason);

	TReviewResDTO getTReviewById(int trevId);

}
