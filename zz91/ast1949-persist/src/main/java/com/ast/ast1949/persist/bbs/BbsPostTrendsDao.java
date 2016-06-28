/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostTrends;
import com.ast.ast1949.dto.PageDto;

public interface BbsPostTrendsDao {
	/**
	 * 插入动态
	 * @param trends
	 * @return
	 */
    public Integer insertBbsPostTrends(BbsPostTrends trends);
    /**
     * 某用户的动态
     * @param companyId
     * @param size
     * @return
     */
    public List<BbsPostTrends> queryTrendsByCompanyId(Integer companyId,Integer size);
    /**
     * 某用户的动态列表
     * @param companyId
     * @param page
     * @return
     */
    public List<BbsPostTrends> queryListTrendsByCompanyId(Integer companyId,PageDto<BbsPostDO> page);
    /**
     * 动态列表记录数
     * @param companyId
     * @param page
     * @return
     */
    public Integer countListTrendsByCompanyId(Integer companyId,PageDto<BbsPostDO> page);
}
