package tot.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemberVO;
import tot.domain.TReviewReqDTO;
import tot.domain.TReviewResDTO;

public interface TReviewService {

	void insertTReview(TReviewReqDTO treviewReqDTO, MultipartFile[] imageFiles);

	PageResDTO<TReviewResDTO> findTReviewListWithPaging(PageReqDTO dto, int boardId);

	TReviewResDTO getTReviewById(int trevId);

	void incrementTReviewCount(int trevId);

	void editTReview(TReviewReqDTO treviewReqDTO, MultipartFile[] imageFiles, List<String> existingImages);

	void deleteTReview(int trevId);

	void reportTReview(int commentId, MemberVO member, String reportedContentType, String reportReason);
}
