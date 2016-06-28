/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 下午04:32:21
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.dto.company.SubscribeDTO;
import com.ast.ast1949.dto.company.SubscribeForMyrcDTO;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *  
 */
public interface SubscribeService {
	/**
	 * 添加一条定制信息
	 *
	 * @param subscribeDO为定制对象br
	 *            />
	 * @return 添加后的记录编号,当返回值小于等于0时,表示插入无效， 当返回值大于0时,表示插入成功
	 */
	public Integer insertSubscribe(SubscribeDO subscribeDO);

	/**
	 * 根据id查询定制信息
	 *
	 * @param id为传入主键值,不可为0<br/>
	 * @return TagsInfoDO,当没找到数据时返回null
	 */
	public SubscribeDO selectSubscribeById(Integer id);
	
	/**
	 * @param subscribeDto定制的基本条件:编号id,中文名：chineseName,公司名：companyName,Email：email,
	 * 关键字：keywords,供求与报价：supplydemandoffer
	 * @return 查询出所有的数据记录
	 */
	public List<SubscribeDTO> selectSubscribeByCondition(SubscribeDTO subscribeDto);

	/**
	 * 根据公司ID和定制类别查找定制信息
	 *
	 * @param companyId
	 * @param subscribeType
	 *            0:供求,1:报价
	 * @return 符合信息的定制列表
	 */
	public List<SubscribeDO> querySubscribeByCompanyIdAndSubscribeType(Integer companyId, String subscribeType);
	/**
	 * 根据公司ID和定制类别删除定制信息
	 *
	 * @param companyId
	 * @param subscribeType
	 *            0:供求,1:报价
	 * @return 符合信息的定制列表
	 */
	public Integer deleteSubscribeByCompanyIdAndSubscribeType(Integer companyId, String subscribeType);

	/**
	 * 批量删除定制信息
	 *
	 * @param entities 不能为空
	 * @return 影响行数>0 删除成功  反之 删除失败
	 */
	public Integer batchDeleteSubscribeById(int[] entities);
	/**
	 * 批量删除定制信息
	 *
	 * @param entities 不能为空
	 * @return 影响行数>0 删除成功  反之 删除失败
	 */
	public Integer deleteSubscribeById(Integer id);
	
	/**
	 * 更新一条定制信息
	 *
	 * @param subscribeDO
	 *            更新后的定制对象
	 * @return 更新操作的影响行数
	 */
	public Integer updateSubscribe(SubscribeDO subscribeDO);
	
	/**
	 * @param subscribeDto 查询出对象的总个数
	 * @return 查询出数据
	 */
	public Integer selectCountSubscribeByCondition(SubscribeDTO subscribeDto);
	
	/**
	 * @param id 根据id查询出一条记录
	 * @return  符合信息的定制列表
	 */
	public SubscribeDTO selectByIdSubscribe(Integer id);
	
	/**
	 * @param subscribeDto  更新后的定制对象
	 * @return 更新操作的影响行数
	 */
	public Integer updateByIdSubscribe(SubscribeDTO subscribeDto);
	
	/**
	 * 批量给指定订制信息重新发送email
	 * @param ids:指定需要重新发送的订制信息id列表
	 * @return
	 */
	public Integer batchResendSubscript(Integer[] ids);
	
	/**
	 * 读取会员报价定制列表
	 * @param subscribeForMyrcDTO
	 * @return
	 */
//	public List<SubscribeForMyrcDTO> querySubscribeForMycrByCondition(SubscribeForMyrcDTO subscribeForMyrcDTO);
	
	/**
	 * 统计会员报价定制信息总数
	 * @param subscribeForMyrcDTO
	 * @return
	 */
	public Integer countSubscribeForMycrByCondition(SubscribeForMyrcDTO subscribeForMyrcDTO);
	
	public List<SubscribeForMyrcDTO> querySubscribeForMyrc(SubscribeForMyrcDTO subscribeForMyrcDTO);
	
	/**
	 *  查看公司的关键字
	 * @return Keywords
	 * 
	 */
	public SubscribeDO queryKeywordsByAccount(String account);
	
	public SubscribeDO querySubscribeById(Integer id);
	
	public boolean allowSubscribeByMemberRule(Integer companyId, String memberShipCode, String subscribeType);
}
