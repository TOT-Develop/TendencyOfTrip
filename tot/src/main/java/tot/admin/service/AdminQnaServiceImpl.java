package tot.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.admin.dao.AdminQnaDao;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.QnaCommentVO;
import tot.domain.QnaDTO;

@Service
public class AdminQnaServiceImpl implements AdminQnaService {

	private final AdminQnaDao adminQnaDao;

	public AdminQnaServiceImpl(AdminQnaDao adminQnaDao) {
		this.adminQnaDao = adminQnaDao;
	}

	@Override
	public QnaDTO getQnaDetail(int qnaId) {
		return adminQnaDao.getQnaDetail(qnaId);
	}

	@Override
	public PageResDTO<QnaDTO> findQnaListWithPaging(PageReqDTO pageReqDTO, int boardId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId);

		int totalQnaCount = adminQnaDao.selectQnaTotalCount(pageDTO);

		List<QnaDTO> qnaList = adminQnaDao.qnaListWithPaging(pageDTO);
		return new PageResDTO<>(totalQnaCount, pageReqDTO.getPage(), qnaList);
	}

	@Override
	public List<QnaCommentVO> getCommentsByQnaId(int qnaId) {
		return adminQnaDao.getCommentsByQnaId(qnaId);
	}

	@Override
	public int insertQnaComment(QnaCommentVO qnaComment) {
		return adminQnaDao.insertQnaComment(qnaComment);
	}

	@Override
	public int updateQnaStatus(int qnaId, String qna_002) {
		return adminQnaDao.updateQnaStatus(qnaId, qna_002);
	}

}