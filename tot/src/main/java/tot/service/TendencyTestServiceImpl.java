package tot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tot.dao.TendencyTestDao;

@Service
public class TendencyTestServiceImpl implements TendencyTestService{

    @Autowired
    private TendencyTestDao tendencyTestDao;

    @Override
    public void updateMemberTendency(String memId, String resultType) {
        // resultType을 코드로 변환
        String code = tendencyTestDao.getCodeByResultType(resultType);
        System.out.println(code);
        if (code == null) {
            throw new IllegalArgumentException("Invalid resultType: " + resultType);
        }

        // memId와 code를 사용하여 DB 업데이트
        tendencyTestDao.updateMemberTendency(memId, code);
    }
}

