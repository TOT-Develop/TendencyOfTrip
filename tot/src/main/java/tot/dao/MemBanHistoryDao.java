package tot.dao;

import java.util.List;

import tot.domain.MemBanHistoryDTO;

public interface MemBanHistoryDao {
	 public abstract void insertBanHistory(MemBanHistoryDTO banHistory);
	 public abstract List<MemBanHistoryDTO> getBanHistoryByMemId(String memId);
	 public abstract void updateLiftHistory(MemBanHistoryDTO history);
}
