/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-12.
 */
package com.ast.ast1949.persist.price;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.price.PriceDataDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface PriceDataDAO {
	/**
	 * 添加报价数据
	 * @param priceData
	 * @return 成功返回新增记录的id
	 */
	public Integer insertPriceData(PriceDataDO priceData);
	/**
	 * 根据编号删除记录
	 * @param id 编号
	 * @return 
	 */
	public Integer deletePriceDataById(Integer id);
	/**
	 * 根据报价编号删除记录
	 * @param id 报价编号
	 * @return
	 */
	public Integer deletePriceDataByPriceId(Integer id);
	/**
	 * 根据报价编号统计记录
	 * @param id 报价编号
	 * @return
	 */
	public Integer countPriceDataByPriceId(Integer id);
	/**
	 * 根据报价编号查询记录
	 * @param id 报价编号
	 * @return
	 */
	public List<PriceDataDO> queryPriceDataByPriceId(Integer id, PageDto<PriceDataDO> page);
	/**
	 * 根据id更新数据
	 * @param priceData
	 * @return
	 */
	public Integer updatePriceDataById(PriceDataDO priceData);
	/**
	 * 根据priceId和companyPriceId查询记录
	 * @param param 参数：<br/>
 	 *  priceId 报价信息编号,不能为空<br/>
	 *  companyPriceId 企业报价编号,不能为空<br/>
	 * @return
	 */
	public PriceDataDO queryPriceDataByPriceIdAndCompanyPriceId(Map<String, Object> param);
	/**
	 * 根据Id查询记录
	 * @param id 编号
	 * @return
	 */
	public PriceDataDO queryPriceDataById(Integer id);
}
