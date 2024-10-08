package tot.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TendencyTestDaoImpl implements TendencyTestDao {

	private static final String NAMESPACE = "tot.dao.TendencyTestDao";
	private final SqlSession sqlSession;

	public TendencyTestDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void updateMemberTendency(String memId, String code) {
		sqlSession.update(NAMESPACE + ".updateMemberTendency", Map.of("memId", memId, "code", code));
	}

	@Override
	public String getCodeByResultType(String resultType) {
		return sqlSession.selectOne(NAMESPACE + ".getCodeByResultType", resultType);
	}
}
