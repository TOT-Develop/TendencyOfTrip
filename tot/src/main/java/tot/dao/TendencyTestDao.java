package tot.dao;

import org.apache.ibatis.annotations.Param;

public interface TendencyTestDao {

    // 회원 성향을 업데이트하는 메소드
	public abstract void updateMemberTendency(@Param("memId") String memId, @Param("code") String code);

	public abstract String getCodeByResultType(@Param("resultType") String resultType);
}
