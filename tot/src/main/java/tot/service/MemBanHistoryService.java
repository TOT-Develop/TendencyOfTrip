package tot.service;

import java.util.List;

import tot.domain.MemBanHistoryDTO;

public interface MemBanHistoryService {

	void insertBanHistory(MemBanHistoryDTO banHistory);

	List<MemBanHistoryDTO> getBanHistoryByMemId(String memId);

	void updateLiftHistory(String id, String reason);

}
