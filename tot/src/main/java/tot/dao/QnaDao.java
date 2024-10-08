package tot.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;
import tot.domain.QnaVO;

public interface QnaDao {

	int selectQnaTotalCount(PageDTO pageDTO);

	List<QnaDTO> qnaListWithPaging(PageDTO pageDTO);

	int insertQna(QnaVO qna);

	QnaDTO getQnaDetail(int QNAID);

	List<QnaCommentVO> getCommentsByQnaId(int qnaId);

}