package tot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tot.dao.TripDao;
import tot.domain.TripDTO;

@Service
public class TripServiceImpl implements TripService {

	private final TripDao tripDao;

	public TripServiceImpl(TripDao tripDao) {
		this.tripDao = tripDao;
	}

	/**
	 * 주어진 회원 ID에 해당하는 여행 목록을 조회합니다.
	 *
	 * @param memId 조회할 회원 ID
	 * @return 해당 회원의 여행 정보 리스트
	 */
	public List<TripDTO> getTripsByMemIdWithPaging(String memId, int page, int pageSize) {
		// offset 계산
		int offset = (page - 1) * pageSize;
		System.out.println("memId: " + memId + ", offset: " + offset + ", pageSize: " + pageSize);

		// 파라미터를 담을 맵 생성
		Map<String, Object> params = new HashMap<>();
		params.put("memId", memId);
		params.put("offset", offset);
		params.put("pageSize", pageSize);

		return tripDao.getTripsByMemIdWithPaging(params);
	}

	@Override
	public List<TripDTO> getTripsByMemId(String memId) {
		return tripDao.getTripsByMemId(memId);
	}

	@Override
	public TripDTO getTripById(int tripId) {
		return tripDao.getTripById(tripId);
	}

	public int getTotalTripsByMemId(String memId) {
		return tripDao.getTotalTripsByMemId(memId);
	}

}
