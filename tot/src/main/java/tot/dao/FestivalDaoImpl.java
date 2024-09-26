package tot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.domain.FestivalDTO;

@Repository
public class FestivalDaoImpl implements FestivalDao {

	private final SqlSession sqlSession;

    @Autowired
    public FestivalDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<FestivalDTO> findFestivalsByDateRange(Map<String, Object> params) {
        return sqlSession.selectList("tot.dao.FestivalDao.findFestivalsByDateRange", params);
    }

    @Override
    public List<FestivalDTO> findFestivalsByMonth(Map<String, Object> params) {
        return sqlSession.selectList("tot.dao.FestivalDao.findFestivalsByMonth", params);
    }
}

