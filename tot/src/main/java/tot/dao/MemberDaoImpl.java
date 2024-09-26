package tot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tot.common.page.PageDTO;
import tot.domain.MemberVO;


@Repository
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "tot.dao.MemberDao.";
    
    @Override
    public void saveMember(MemberVO member) {
        sqlSession.insert(NAMESPACE + "saveMember", member);
    }

    @Override
    @Transactional
    public void updateNickname(MemberVO member) {
        // MyBatis의 SQL 매퍼로 Member 객체 전달
        sqlSession.update(NAMESPACE + "updateNickname", member);
    }

    @Override
    public String findNicknameByMemId(String memId) {
        return sqlSession.selectOne(NAMESPACE + "findNicknameByMemId", memId);
    }

    @Override
    public String findNicknameByNick(String nickname) {
        return sqlSession.selectOne(NAMESPACE + "findNicknameByNick", nickname);
    }

    @Override
    public MemberVO findMemberByMemId(String memId) {
        return sqlSession.selectOne(NAMESPACE + "findMemberByMemId", memId);
    }

    @Override
    public void updateMemberStatus(Map<String, Object> params) {
        sqlSession.update(NAMESPACE + "updateMemberStatus", params);
    }
    
    @Override
    public String findEmailByMemberId(String memId) {
        return sqlSession.selectOne(NAMESPACE + ".findEmailByMemberId", memId);
    }

    
    // 회원 목록을 가져오는 메서드
    @Override
    public List<MemberVO> getMembersWithPaging(PageDTO pageDTO) {
    	System.out.println("검색어: " + pageDTO.getDto().getSearch()); // 검색어 로그
        System.out.println("쿼리 실행: " + NAMESPACE + "getMembersWithPaging"); // 쿼리 실행 로그
        return sqlSession.selectList(NAMESPACE + "getMembersWithPaging", pageDTO);
    }

    // 전체 회원 수를 가져오는 메서드
    @Override
    public int getTotalMemberCount(PageDTO pageDTO) {
        return sqlSession.selectOne(NAMESPACE + "getTotalMemberCount", pageDTO);
    }

}
