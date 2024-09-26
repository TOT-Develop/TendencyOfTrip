package tot.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tot.dao.NoticeDao;
import tot.domain.Notice;
import tot.exception.ServerException;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;

@Service
public class AdminNoticeServiceImpl implements AdminNoticeService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminNoticeServiceImpl.class); // logger 정의
	
    @Autowired
    private DataSourceTransactionManager transactionManager;
	
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    
	@Inject
	NoticeDao noticeDao;
	
	@Override
	public List<Notice> noticeList() throws Exception {
		return noticeDao.noticeList();
	}
	
    @Override
    public PageResDTO<Notice> findNoticeListWithPaging(PageReqDTO pageReqDTO) {
        try {
            PageDTO pageDTO = new PageDTO(pageReqDTO);  // PageReqDTO 기반의 DTO 생성
            
            int totalNoticeCount = noticeDao.selectNoticeTotalCount(pageDTO);  // 전체 공지사항 수 조회

            // 검색 및 페이징 처리된 공지사항 목록 조회
            List<Notice> postList = noticeDao.noticeListWithPaging(pageDTO);

            // PageResDTO에 데이터와 페이징 정보 포함해서 반환
            return new PageResDTO<>(totalNoticeCount, pageReqDTO.getPage(), postList);
        } catch (DataAccessException e) {
            throw new ServerException("공지사항 목록을 가져오는 중 데이터베이스 오류 발생", e);
        } catch (Exception e) {
            // Exception 처리 (예외를 로깅하거나 다른 처리를 할 수 있음)
            throw new ServerException("알 수 없는 오류 발생", e); // 필요에 따라 구체적인 예외로 감싸서 던짐
        }
    }


    @Override
    public Notice getNoticeById(int noid) throws Exception {
        return noticeDao.getNoticeById(noid);
    }
    
    @Override
    public void insertNotice(Notice notice) throws Exception {
        noticeDao.insertNotice(notice); // DAO 계층으로 데이터 전송
    }
    
    @Override
    public void deleteNotice(int noid) throws Exception {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            int result = noticeDao.deleteNotice(noid);
            if (result == 0) {
                throw new Exception("공지사항을 찾을 수 없습니다."); // 삭제 실패
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("공지사항 삭제 실패: " + e.getMessage(), e);
            throw e; // 예외를 다시 던짐
        }
    }
    
    @Override
    public void updateNotice(Notice notice) throws Exception {
        noticeDao.updateNotice(notice);
    }

}
