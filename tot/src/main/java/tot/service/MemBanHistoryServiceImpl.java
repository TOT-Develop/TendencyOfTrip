package tot.service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tot.dao.MemBanHistoryDao;
import tot.domain.MemBanHistoryDTO;

@Service
public class MemBanHistoryServiceImpl implements MemBanHistoryService {
    @Autowired
    private MemBanHistoryDao memBanHistoryDao;

    @Override
    public void insertBanHistory(MemBanHistoryDTO banHistory) {
        memBanHistoryDao.insertBanHistory(banHistory);
    }

    @Override
    public List<MemBanHistoryDTO> getBanHistoryByMemId(String memId) {
        return memBanHistoryDao.getBanHistoryByMemId(memId);
    }

    @Override
    public void updateLiftHistory(String id, String reason) {
        MemBanHistoryDTO history = new MemBanHistoryDTO();
        history.setMemId(id);
        history.setLiftReason(reason);
        history.setBanLifted(new Timestamp(System.currentTimeMillis())); // 현재 시간으로 설정

        memBanHistoryDao.updateLiftHistory(history);
    }
}
