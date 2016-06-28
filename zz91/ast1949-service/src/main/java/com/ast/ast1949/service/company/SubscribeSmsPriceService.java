package com.ast.ast1949.service.company;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;

import com.ast.ast1949.domain.company.SubscribeSmsPriceDO;
import com.ast.ast1949.dto.PageDto;

public interface SubscribeSmsPriceService {
	
	/**
	 * 添加一条定制信息
	 *
	 * @param companyId,公司id
	 * @param categoryCode,类别
	 * @param areaNodeId 地区类别
	 *            />
	 * @return 添加后的记录编号,当返回值小于等于0时,表示插入无效， 当返回值大于0时,表示插入成功
	 */
	public Integer addSubscribeSMS(Integer companyId,String categoryCode,Integer areaNodeId);
	

	/**
	 * 根据公司ID和定制类别查找定制信息
	 *
	 * @param companyId
	 * @return 符合信息的定制列表
	 */
	public List<SubscribeSmsPriceDO> querySubscribeSMS(Integer companyId);
	
	/**
	 * 删除客户订阅的短信报价内容
	 * @param categoryCode 客户订阅的类别是唯一的不能重复订阅，所以可以考虑根据类别来删除
	 * @param companyId 考虑数据安全,删除语句必须附带companyId
	 */
	public Integer deleteSubscribeSMS(String categoryCode,Integer companyId);
	
	/**
	 * 浏览订阅的短信报价信息
	 * @param companyId
	 * @param categoryCode
	 * @param areaNodeId
	 * @param page
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public PageDto querySubscribeSMSPrice(Integer companyId,String categoryCode,String areaNodeId,Integer startIndex) throws HttpException, IOException, org.apache.http.HttpException;

	/**
	 * 查询是否重复订阅，一个类别只能订阅一次
	 * @param categoryCode
	 * @param companyId 
	 */
	public Integer countSubscribeSMS(String categoryCode,Integer companyId);
	
	/**
	 * 查询订阅的短信类别显示在短信行情的上面
	 * @param companyId 
	 */
	public String selectSubscribeSmsForList(Integer companyId);
	
	public Integer deleteSubscribeSMSPrice(Integer id,Integer companyId);
	
	public Integer deleteSubscribeSMSByArea(String categoryCode,Integer areaNodeId,Integer companyId);
}
