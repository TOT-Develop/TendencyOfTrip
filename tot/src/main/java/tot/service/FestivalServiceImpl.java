package tot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tot.dao.FestivalDao;
import tot.domain.FestivalDTO;

@Service
public class FestivalServiceImpl implements FestivalService {

    private final FestivalDao festivalDao;

    @Autowired
    public FestivalServiceImpl(FestivalDao festivalDao) {
        this.festivalDao = festivalDao;
    }

	@Override
	public List<FestivalDTO> findFestivalsByDateRange(Map<String, Object> params) {
		  return festivalDao.findFestivalsByDateRange(params);
	}

	@Override
	public List<FestivalDTO> findFestivalsByMonth(Map<String, Object> params) {
		return festivalDao.findFestivalsByMonth(params);
	}
}

