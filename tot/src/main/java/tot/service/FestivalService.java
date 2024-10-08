package tot.service;

import java.util.List;
import java.util.Map;

import tot.domain.FestivalDTO;

public interface FestivalService {

	List<FestivalDTO> findFestivalsByDateRange(Map<String, Object> params);

	List<FestivalDTO> findFestivalsByMonth(Map<String, Object> params);
}
