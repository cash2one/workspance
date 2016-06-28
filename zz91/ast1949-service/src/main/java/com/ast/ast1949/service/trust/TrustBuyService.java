/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.service.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;

public interface TrustBuyService {
	
	final static String STATUS_00 = "00";	// 00:未审核
	final static String STATUS_01 = "01"; // 01:正在报价
	final static String STATUS_02 = "02"; // 02:已有报价
	final static String STATUS_03 = "03"; // 03:正在洽谈
	final static String STATUS_04 = "04"; // 04:等待打款
	final static String STATUS_05 = "05"; // 05:交易完成
	final static String STATUS_06 = "06"; // 06:交易终止
	final static String STATUS_99 = "99"; // 99:审核不通过
	
	final static Integer IS_PAUSE = 1;
	final static Integer IS_PUB = 0;
	
	
	/**
	 * 发布新采购信息
	 * 1.生成流水号
	 * 2.初始化 trustbuy实例，设置状态为 未审核00
	 * 3.调用 dao insert语句实现发布
	 * 4.返回发布后采购信息id
	 */
	public Integer publishBuy(Integer companyId,String detail);
	
	/**
	 * 检索该公司所有的采购
	 * 1.检索所有 该公司id下所有 采购
	 * 2.组装trustbuydto实例，组装 list
	 * 3.返回page
	 */
	public PageDto<TrustBuyDto> pageByCompamyId(TrustBuySearchDto searchDto,PageDto<TrustBuyDto> page);
	
	/**
	 * 更改采购信息
	 * 1.更改采购信息
	 * 2.判断id是否为空 为空返回 0
	 * 3.执行dao对应 update语句实现更改信息
	 */
	public Integer editTrustByAdmin(TrustBuy trustBuy);
	
	/**
	 * 检索所有采购订单 用于后台
	 */
	public PageDto<TrustBuyDto> page(TrustBuySearchDto searchDto,PageDto<TrustBuyDto> page);
	
	/**
	 * 检索所有采购订单 用于feiliao91网接口
	 */
	public PageDto<TrustBuyDto> pageSimple(TrustBuySearchDto searchDto,PageDto<TrustBuyDto> page);
	
	
	/**
	 * 查看一条采购信息详细
	 */
	public TrustBuyDto showTrustBuy(Integer id);
	
	/**
	 * 更改采购单状态
	 */
	public Integer updateStateByAdmin(Integer id,String status);
	
	/**
	 * 统计该公司所有采购信息数量
	 * @param companyId
	 * @return
	 */
	public Integer countByCompanyId(Integer companyId);

	/**
	 * 未登陆情况下发布采购信息
	 * @param companyName
	 * @param companyContact
	 * @param mobile
	 * @param detail
	 * @return
	 */
	public Integer publishBuyWithoutLogin(String companyName, String companyContact,String mobile, String detail);
	
	/**
	 * 批量刷新采购
	 * @param ids
	 * @return
	 */
	public Integer batchRefresh(String ids);
	
	public Integer createBuyByAdmin(TrustBuy trustBuy);

	/**
	 * 删除采购信息(假删除)
	 * @param id
	 * @return
	 */
	public 	Integer deleteById(Integer id);
	
	/**
	 * 根据公司id检索所有订单详细 默认最新20条
	 * @param id
	 * @return
	 */
	public List<TrustBuy> queryTrustByCompanyId(Integer companyId);
	
	/**
	 * 根据采购id获取采购信息
	 */
	public TrustBuy queryTrustById(Integer id);
	
	/**
	 * 关联帐号
	 * 已经关联的不再关联
	 * @param companyId
	 * @param mobile
	 * @return
	 */
	public Integer relateCompanyByMobile(Integer companyId,String mobile);
	
	/**
	 * 采购列表中将选中的id数组，进行暂不发布处理
	 */
	public Integer pauseBuy(String ids);
	
	/**
	 * 采购列表中将选中的id数组，进行暂不发布处理
	 */
	public Integer pubBuy(String ids);
	/**
	 * 更改发布状态
	 */
	public Integer batchUpdatePauseById(String  ids,Integer status);
}
