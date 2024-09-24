package tot.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tot.domain.TripVO;


public interface TripDao {
    // 특정 사용자의 모든 여행 목록 조회
	public abstract List<TripVO> getTripsByMemId(String memId);
    
    // 특정 여행 아이디에 대한 여행 정보 조회
	public abstract TripVO getTripById(int tripId);
}
