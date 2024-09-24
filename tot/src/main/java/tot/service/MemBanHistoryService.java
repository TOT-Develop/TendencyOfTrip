package tot.service;

import java.util.List;

import tot.domain.MemBanHistoryDTO;

public interface MemBanHistoryService {
	 public abstract void insertBanHistory(MemBanHistoryDTO banHistory);
	 public abstract List<MemBanHistoryDTO> getBanHistoryByMemId(String memId);
}
