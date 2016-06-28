package com.ast.ast1949.service.company;



public interface CompanyAccessViewService {
	
	/**
	 * 验证用户今日是否 可以查看
	 * 情况有五种：	1、	今日已经查看过，							可以查看
	 * 					今日没有查看过，查看公司数小于10			可以查看
	 * 					今日查看过10个，但是解绑了又小于15条		可以查看
	 * 				2	今日查看超过10个，没有解绑				不可以查看
	 * 				3	今日查看超过15个						不可以查看
	 * 				4	没有绑定微信							不可以查看
	 * 				5	绑定微信过期							不可以查看
	 * 
	 * 注：解绑——	每天发布5条供求审核通过或对10家客户询盘，可再次查看5家客户联系方式
	 * 注：用户——一定要是手机微信绑定过帐号的用户才可以
	 * @param companyId
	 * @param targetId
	 * @param account
	 * @return
	 */
	public Integer validateIsExists(Integer companyId,Integer targetId,String account);

	/**
	 * 添加一条浏览记录
	 */
	public Integer insert(Integer companyId, Integer targetId, String account);

	/**
	 * 验证是否微信帐号绑定有刷新权限
	 * 
	 * @param companyId
	 * @param account
	 * @return		0：默认值 不可刷新
	 * 				1：可以刷新
	 * 				2：没有绑定微信
	 * 				3：绑定微信过期 （7日）
	 */
	public Integer validateIsRefresh(Integer companyId, String account);
}
