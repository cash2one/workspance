/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:14:16
 */
package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeSupply;

public interface SubnetTradeRecommendService {
	
	/**
	 * 
	 * 函数名称：querySupplyBySubRec
	 * 功能描述：[查询子网推荐产品]
	 * 输入参数：@param subnetCategory 子网类别
	 * 　　　　　.......
	 * 　　　　　@param size 显示数量
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> querySupplyBySubRec(String subnetCategory,Integer size);
	
}
