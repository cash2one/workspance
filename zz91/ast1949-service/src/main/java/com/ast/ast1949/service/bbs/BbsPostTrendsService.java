/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.dto.PageDto;

public interface BbsPostTrendsService {
	public List<BbsPostDO> queryTrendsByCompanyId(Integer companyId,Integer size);
	/**
	 * 某用户的动态列表
	 * @param companyId
	 * @param page
	 * @return
	 */
	public PageDto<BbsPostDO> ListBbsPostByCompanyId(Integer companyId,PageDto<BbsPostDO> page);
}
