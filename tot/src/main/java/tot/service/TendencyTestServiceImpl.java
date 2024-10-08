package tot.service;

import org.springframework.stereotype.Service;

import tot.dao.TendencyTestDao;

@Service
public class TendencyTestServiceImpl implements TendencyTestService {

	private TendencyTestDao tendencyTestDao;

	public TendencyTestServiceImpl(TendencyTestDao tendencyTestDao) {
		this.tendencyTestDao = tendencyTestDao;
	}

	@Override
	public void updateMemberTendency(String memId, String resultType) {
		// resultType을 코드로 변환
		String code = tendencyTestDao.getCodeByResultType(resultType);
		if (code == null) {
			throw new IllegalArgumentException("Invalid resultType: " + resultType);
		}

		// memId와 code를 사용하여 DB 업데이트
		tendencyTestDao.updateMemberTendency(memId, code);
	}
}
