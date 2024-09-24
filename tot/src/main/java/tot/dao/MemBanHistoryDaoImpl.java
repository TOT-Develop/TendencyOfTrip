package tot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.domain.MemBanHistoryDTO;

@Repository
public class MemBanHistoryDaoImpl implements MemBanHistoryDao {
    // SqlSessionTemplate 객체 주입
    
    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "tot.dao.MemberDao.";
	
    @Override
    public void insertBanHistory(MemBanHistoryDTO banHistory) {
        sqlSession.insert(NAMESPACE + "insertBanHistory", banHistory);
    }

    @Override
    public List<MemBanHistoryDTO> getBanHistoryByMemId(String memId) {
        return sqlSession.selectList(NAMESPACE + "getBanHistoryByMemId", memId);
    }
}

