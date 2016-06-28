package com.zz91.ep.admin.service.data;

import java.util.Map;

public interface CompProfileDataService {
	/**
	 * 根据关键字逻辑删除公司信息
	 * @return
	 */
	public Integer updateCompByKeywords(String keywords);
	/**
	 * 根据关键字获取公司信息
	 * @param page
	 * @param keywords
	 * @return
	 */
	public Map<String, Object> queryCompanyIdByKeywords(Integer start,Integer limit,String keywords);
}
