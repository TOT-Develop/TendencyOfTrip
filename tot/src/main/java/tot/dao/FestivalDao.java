package tot.dao;

import java.util.List;
import java.util.Map;

import tot.domain.FestivalDTO;

public interface FestivalDao {
	public abstract List<FestivalDTO> findFestivalsByDateRange(Map<String, Object> params);
	public abstract List<FestivalDTO> findFestivalsByMonth(Map<String, Object> params);
}
