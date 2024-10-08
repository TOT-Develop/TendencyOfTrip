package tot.service;

import java.util.List;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;
import tot.domain.QnaVO;

public interface QnaService {

	PageResDTO<QnaDTO> findQnaListWithPaging(PageReqDTO pageReqDTO, int boardId);

	int insertQna(QnaVO qna);

	QnaDTO getQnaDetail(int QnaId);

	List<QnaCommentVO> getCommentsByQnaId(int qnaId);

}