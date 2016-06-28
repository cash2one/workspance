package com.zz91.ep.admin.service.sys;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.data.CompAccountMakeMap;
import com.zz91.ep.dto.data.CompMakeMap;
import com.zz91.ep.dto.data.IbdCompanyMakeMap;
import com.zz91.ep.dto.data.NewsMakeMap;
import com.zz91.ep.dto.data.TradeBuyMakeMap;
import com.zz91.ep.dto.data.TradeSupplyMakeMap;

public interface DataGatherService {

	/**
	 * 保存或更新公司信息，若公司存在则执行更新公司以及公司账户信息操作
	 * 若公司不存在，则创建公司以及公司账户信息
	 * @param compProfile
	 * @param compAccount
	 * @return
	 */
	public Integer saveOrUpdateCompProfile(CompProfile compProfile,
			CompAccount compAccount);

	/**
	 * 通过省份名称查询省份代码
	 * @param provinceName
	 * @return
	 */
	public String getProvinceCodeByProvinceName(String provinceName);

	/**
	 * 通过地区名称查询地区代码
	 * @param areaName
	 * @return
	 */
	public String getAreaCodeByAreaName(String areaName);

	/**
	 * 通过期望供货地区名称查询期望供货地区代码
	 * @param supplyAreaName
	 * @return
	 */
	public String getSupplyAreaCodeBySupplyAreaName(String supplyAreaName);

	/**
	 * 根据目录类别名称得到目录代码
	 * @param categoryName
	 * @return
	 */
	public String getCategoryCodeByCategoryName(String categoryName);

	/**
	 * 根据ID删除记录
	 * @param ids
	 * @return
	 */
	public boolean deleteBuyById(Integer id);
	
	/**
	 * 根据ID删除记录
	 * @param ids
	 * @return
	 */
	public boolean deleteSupplyById(Integer id);
	/**
	 * 验证手机号是否已经存在
	 * @param mobile
	 * @return
	 */
	public boolean validateMobileExist(String mobile);

	/**
	 * 通过求购类别名称查询求购类别代码
	 * @param categoryName
	 * @return
	 */
	public String getBuyCategoryCodeByCategoryName(String categoryName);
	
	/**
	 * 通过供应类别名称查询供应类别代码
	 * @param categoryName
	 * @return
	 */
	public String getSupplyCategoryCodeByCategoryName(String categoryName);
	
	public Integer createProfile(CompProfile profile);
	
	public Integer updateCompProfile(CompProfile profile);
	
	public Integer createAccount(CompAccount account);
	
	public Integer updateAccount(CompAccount account);
	
	public Integer createCompProfile(CompMakeMap compMap, String account, HSSFRow row, String sourceFlag,Integer mainUse);

	public Boolean createCompAccount(Integer cid, String account, CompAccountMakeMap accountMap, HSSFRow row);
	
	public CompProfile buildCompProfile(CompProfile profile, CompMakeMap compMap, HSSFRow row);
	
	public CompAccount buildCompAccount(CompAccount account, CompAccountMakeMap accountMap, HSSFRow row);
	
	public Integer countTradeSupply(Integer cid, Integer uid, String categoryCode, String title);
	
	public Boolean createTradeSupply(Integer cid, Integer uid, TradeSupplyMakeMap tradeMap, HSSFRow row, String sourceFlag);
	
	public TradeSupply buildTradeSupply(TradeSupply supply, TradeSupplyMakeMap tradeMap, HSSFRow row,String sourceFlag);
	
	public String crawlWebPhoto(String picUrl, String destRoot);
	
	public Photo buildPhoto(Integer uid, Integer cid, Integer targetId, String targetType);

	public Integer updateAlibabaEmail(String account, String email);

	public boolean createTradeBuy(Integer cid, Integer uid,
			TradeBuyMakeMap tradeMap, HSSFRow row, String sourceFlag);
	
	public TradeBuy buildTradeBuy(TradeBuy buy,
			TradeBuyMakeMap tradeMap, HSSFRow row);

	public boolean createNews(NewsMakeMap newsMap, HSSFRow row);

	/**
	 * 导入买家库信息
	 * @param companyMap
	 * @param row
	 * @return
	 */
	public boolean createIbdCompany(IbdCompanyMakeMap companyMap, HSSFRow row);

	/**
	 * @param page
	 * @return
	 */
	public PageDto<TradeBuy> pageBuys(PageDto<TradeBuy> page);
}
