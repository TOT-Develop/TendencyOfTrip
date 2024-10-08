package tot.dao;

import java.util.List;

import tot.domain.MemBanHistoryDTO;

public interface MemBanHistoryDao {

	void insertBanHistory(MemBanHistoryDTO banHistory);

	List<MemBanHistoryDTO> getBanHistoryByMemId(String memId);

	void updateLiftHistory(MemBanHistoryDTO history);

}
