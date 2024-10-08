package tot.admin.dao;

import java.util.List;

import tot.common.page.PageDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;

public interface AdminQnaDao {

	QnaDTO getQnaDetail(int QNAID);

	List<QnaDTO> qnaListWithPaging(PageDTO pageDTO);

	int selectQnaTotalCount(PageDTO pageDTO);

	List<QnaCommentVO> getCommentsByQnaId(int qnaId);

	int insertQnaComment(QnaCommentVO qnaComment);

	int updateQnaStatus(int qnaId, String newStatus);

}