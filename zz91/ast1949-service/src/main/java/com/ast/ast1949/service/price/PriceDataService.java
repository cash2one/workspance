/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-12.
 */
package com.ast.ast1949.service.price;

import java.util.List;

import com.ast.ast1949.domain.price.PriceDataDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface PriceDataService {
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
//	public Integer deletePriceDataByPriceId(Integer id);
	/**
	 * 根据id更新数据
	 * @param priceData
	 * @return
	 */
//	public Integer updatePriceDataById(PriceDataDO priceData);
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
	public List<PriceDataDO> queryPriceDataByPriceId(Integer id,PageDto<PriceDataDO> page);
	/**
	 * 添加记录,先根据priceId和companyPriceId判断记录是否存在，若存在则更新该记录，否则插入一条新记录。
	 * @param priceData
	 * @return 添加成功 ExtResult.setSuccess(true);
	 */
	public ExtResult insert(PriceDataDO priceData,Integer companyId);
	/**
	 * 根据priceId和companyPriceId查询记录
 	 * @param priceId 报价信息编号
	 * @param companyPriceId 企业报价编号
	 * @return
	 */
	public PriceDataDO queryPriceDataByPriceIdAndCompanyPriceId(Integer priceId, Integer companyPriceId);
	/**
	 * 根据Id查询记录
	 * @param id 编号
	 * @return
	 */
//	public PriceDataDO queryPriceDataById(Integer id);
}
