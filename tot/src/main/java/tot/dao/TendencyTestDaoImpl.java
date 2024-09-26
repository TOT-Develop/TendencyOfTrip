package tot.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public class TendencyTestDaoImpl implements TendencyTestDao {

    private final SqlSession sqlSession;

    @Autowired
    public TendencyTestDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    @Transactional
    public void updateMemberTendency(String memId, String code) {
        sqlSession.update("tot.dao.TendencyTestDao.updateMemberTendency",
            Map.of("memId", memId, "code", code));
    }

    @Override
    public String getCodeByResultType(String resultType) {
        return sqlSession.selectOne("tot.dao.TendencyTestDao.getCodeByResultType", resultType);
    }
}
