package tot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {

	private static final String NAMESPACE = "tot.dao.MemberDao";
	private final SqlSession sqlSession;

	public MemberDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void saveMember(MemberVO member) {
		sqlSession.insert(NAMESPACE + ".saveMember", member);
	}

	@Override
	public void updateNickname(String memId, String newNickname) {
		Map<String, Object> params = new HashMap<>();
		params.put("memId", memId);
		params.put("memNick", newNickname);
		sqlSession.update(NAMESPACE + ".updateNickname", params);
	}

	@Override
	public String findNicknameByMemId(String memId) {
		return sqlSession.selectOne(NAMESPACE + ".findNicknameByMemId", memId);
	}

	@Override
	public String findNicknameByNick(String nickname) {
		return sqlSession.selectOne(NAMESPACE + ".findNicknameByNick", nickname);
	}

	@Override
	public MemberVO findMemberByMemId(String memId) {
		return sqlSession.selectOne(NAMESPACE + ".findMemberByMemId", memId);
	}

	@Override
	public void updateMemberStatus(Map<String, Object> params) {
		sqlSession.update(NAMESPACE + ".updateMemberStatus", params);
	}

	@Override
	public List<MemberVO> getMembersWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".getMembersWithPaging", pageDTO);
	}

	@Override
	public int getTotalMemberCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".getTotalMemberCount", pageDTO);
	}

}
