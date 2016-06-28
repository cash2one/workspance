/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-15.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.price.PriceDataDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.service.price.PriceDataService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Controller
public class PriceDataController extends BaseController {
	private static final Integer DEDALUT_PAGE_SIZE=100;

	@Autowired
	private PriceDataService priceDataService;
	@Resource
	private PriceService priceService;
	/**
	 * 初始化为报价导入报价信息
	 * @param out
	 */
	@RequestMapping
	public void view(Map<String, Object> out,Integer id){
		out.put("id", id);
	}
	
	/**
	 * 通过报价id获取所有报价数据
	 * @param model
	 * @param id
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, Integer id, PageDto<PriceDataDO> page) throws IOException {
		if(page == null){
			page = new PageDto<PriceDataDO>(DEDALUT_PAGE_SIZE);
		} else {
			if(page.getStartIndex()==null){
				page.setStartIndex(0);
			}
			if(page.getPageSize()==null){
				page.setPageSize(DEDALUT_PAGE_SIZE);
			}
		}
		
//		page.setSort("id");
//		page.setDir("desc");
		
		page.setTotalRecords(priceDataService.countPriceDataByPriceId(id));
		List<PriceDataDO> list =priceDataService.queryPriceDataByPriceId(id,page);
		page.setRecords(list);

		return printJson(page, model);
	}
	
	/**
	 * （手工）添加一条报价数据
	 * @param model
	 * @param priceData
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView insert(Map<String, Object> model,PriceDataDO priceData,Integer companyId) throws IOException {
		ExtResult result = new ExtResult();
		
		result = priceDataService.insert(priceData,companyId);
		
		// 添加成功
		if (result.isSuccess()) {
			priceService.updateContentQueryById(priceData.getPriceId(), " "+priceData.getArea()+" "+priceData.getQuote()+" "+priceData.getProductName());
		}
		
		return printJson(result, model);
	}
	
	/**
	 * 通过企业报价添加报价数据
	 * @param model
	 * @param ids
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addPriceDataFromCompanyPrice(Map<String, Object> model,PriceDataDO priceData) throws IOException {
		ExtResult result = new ExtResult();
		
		Integer id = priceDataService.insertPriceData(priceData);
		if(id.intValue()>0){
			result.setSuccess(true);
		}

		return printJson(result, model);
	}
	
	/**
	 * 根据id删除报价数据
	 * @param model
	 * @param id 报价数据编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model,String ids) throws IOException {
		
		ExtResult result= new ExtResult();

		String[] entities=ids.split(",");

		int impacted=0;
		for(int ii=0;ii<entities.length;ii++){
			if(StringUtils.isNumber(entities[ii])){
				Integer im=priceDataService.deletePriceDataById(Integer.valueOf(entities[ii]));
				if(im.intValue()>0){
					impacted++;
				}
			}
		}

		if(impacted!=entities.length) {
			result.setSuccess(false);
		}else{
			result.setSuccess(true);
		}

		result.setData(impacted);

		return printJson(result, model);
	}
}
