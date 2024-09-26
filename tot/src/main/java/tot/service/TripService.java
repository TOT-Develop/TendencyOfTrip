package tot.service;

import java.util.List;
import tot.domain.TripVO;

public interface TripService {
    // 특정 사용자의 모든 여행 목록 조회
	public abstract List<TripVO> getTripsByMemId(String memId);
    
    // 특정 여행 아이디에 대한 여행 정보 조회
	public abstract TripVO getTripById(int tripId);
}
