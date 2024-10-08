package tot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.dao.QnaDao;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;
import tot.domain.QnaVO;

@Service
public class QnaServiceImpl implements QnaService {

	private final QnaDao qnaDao;

	public QnaServiceImpl(QnaDao qnaDao) {
		this.qnaDao = qnaDao;
	}

	@Override
	public PageResDTO<QnaDTO> findQnaListWithPaging(PageReqDTO pageReqDTO, int boardId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId);

		int totalQnaCount = qnaDao.selectQnaTotalCount(pageDTO);

		List<QnaDTO> qnaList = qnaDao.qnaListWithPaging(pageDTO);
		return new PageResDTO<>(totalQnaCount, pageReqDTO.getPage(), qnaList);
	}

	@Override
	public int insertQna(QnaVO qna) {
		return qnaDao.insertQna(qna);
	}

	@Override
	public QnaDTO getQnaDetail(int qnaId) {
		return qnaDao.getQnaDetail(qnaId);
	}

	@Override
	public List<QnaCommentVO> getCommentsByQnaId(int qnaId) {
		return qnaDao.getCommentsByQnaId(qnaId);
	}

}