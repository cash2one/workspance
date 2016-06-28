package com.ast.ast1949.service.sample;


public interface ContactClickLogService {
	
	/**
	 * 积分兑换查看联系方式服务 
	 * 1.contact_click_log 或 weixin_lookcontactlog 已经存在记录，则可以查看联系方式。
	 * 2.不存在记录，则判断 ( weixin_prizelog中查看服务的个数) - ( 电脑端contact_click_log中查看服务的个数)-(手机端 weixin_lookcontactlog 中查看服务的个数)
	 * 如果大于0，说明还有待使用的查看服务，此时返回true，并新增一条contact_click_log记录。
	 * 
	 * @param companyId   用户companyId
	 * @param account   用户account
	 * @param targetComId  目标公司ID
	 * @return
	 */
	public boolean scoreCvtViewContact(Integer companyId, String account, Integer targetComId);
}
