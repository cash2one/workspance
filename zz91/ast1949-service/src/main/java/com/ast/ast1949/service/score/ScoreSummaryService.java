package com.ast.ast1949.service.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreSummaryDto;

public interface ScoreSummaryService {
 
	/**
	 * 查找积分最多的部分用户，按照积分，从多到少排序
	 * max：null表示获取默认条记录
	 */
	public List<ScoreSummaryDto> queryMostOfUserScore(Integer max);
	public ScoreSummaryDo querySummaryByCompanyId(Integer companyId);
	/**
	 * 查找用户积分信息，可按公司名称、邮箱、会员类型查找
	 */
	public PageDto<ScoreSummaryDto> pageSummaryByCondictions(ScoreSummaryDto condictions, PageDto<ScoreSummaryDto> page);
}
