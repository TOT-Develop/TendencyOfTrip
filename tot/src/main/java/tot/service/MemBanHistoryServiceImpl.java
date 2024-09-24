package tot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.dao.MemBanHistoryDao;
import tot.domain.MemBanHistoryDTO;

@Service
public class MemBanHistoryServiceImpl implements MemBanHistoryService {
    private MemBanHistoryDao memBanHistoryDAO;

	@Override
	public void insertBanHistory(MemBanHistoryDTO banHistory) {
		 memBanHistoryDAO.insertBanHistory(banHistory);
	}

	@Override
	public List<MemBanHistoryDTO> getBanHistoryByMemId(String memId) {
		return memBanHistoryDAO.getBanHistoryByMemId(memId);
	}
}

