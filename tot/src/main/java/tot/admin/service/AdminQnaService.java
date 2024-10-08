package tot.admin.service;

import java.util.List;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;

public interface AdminQnaService {

	QnaDTO getQnaDetail(int QNAID);

	PageResDTO<QnaDTO> findQnaListWithPaging(PageReqDTO pageReqDTO, int boardId);

	int insertQnaComment(QnaCommentVO qnaComment);

	List<QnaCommentVO> getCommentsByQnaId(int qnaId);

	int updateQnaStatus(int qnaId, String newStatus);

}