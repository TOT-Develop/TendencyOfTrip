package tot.admin.dao;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.Notice;

@Repository
public class AdminNoticeDaoImpl implements AdminNoticeDao {
	
	@Inject
	SqlSession sqlSession;
	
	private static final String NAMESPACE = "NoticeDao";

	@Override
	public List<Notice> noticeList() throws Exception {
	    return sqlSession.selectList("NoticeDao.NoticeList");
	}
	
    @Override
    public Notice getNoticeById(int noid) throws Exception {
        return sqlSession.selectOne("NoticeDao.getNoticeById", noid);
    }
    
    @Override
    public void insertNotice(Notice notice) throws Exception {
        sqlSession.insert("NoticeDao.insertNotice", notice); // MyBatis 쿼리 호출
    }
    
    @Override
    public int deleteNotice(int noid) throws Exception {
    	 return sqlSession.delete("NoticeDao.deleteNotice", noid);
    }
    
    @Override
    public void updateNotice(Notice notice) throws Exception {
        sqlSession.update("NoticeDao.updateNotice", notice);
    }
    
    
    @Override
    public List<Notice> noticeListWithPaging(PageDTO pageDTO) throws Exception {
        return sqlSession.selectList(NAMESPACE + ".noticeListWithPaging", pageDTO);
    }

    @Override
    public int selectNoticeTotalCount(PageDTO pageDTO) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".selectNoticeTotalCount", pageDTO);
    }

}
