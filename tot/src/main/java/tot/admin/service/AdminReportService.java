package tot.admin.service;

import java.util.List;

import tot.admin.enums.BanReason;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.ReportDTO;

public interface AdminReportService {

	void updateReportStatus(String status, List<Integer> reportIds, BanReason reason);

	PageResDTO<ReportDTO> findReportListWithPaging(PageReqDTO dto, int boardId);

}
