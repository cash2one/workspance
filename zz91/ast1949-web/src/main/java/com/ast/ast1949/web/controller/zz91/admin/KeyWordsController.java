/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-30 by luocheng.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsKeywordsRankDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.products.ProductsKeywordsRankDTO;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.products.ProductsKeywordsRankService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author luocheng
 * 
 */

@Controller
public class KeyWordsController extends BaseController {
	
	@Autowired
	private ProductsKeywordsRankService productsKeywordsRankService;
	@Autowired
	private ProductsService  productsService;

	/**
	 * 初始化列表页面
	 */
	@RequestMapping
	public void list() {
	}

	/**
	 * 查询列表
	 * 
	 * @param model
	 * @param page
	 *            分页
	 * @param productsKeywordsRankDTO
	 *            查询条件
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, PageDto<ProductsKeywordsRankDTO> page,String isChecked,Boolean expire,
			ProductsKeywordsRankDTO productsKeywordsRankDTO,ProductsKeywordsRankDO productsKeywordsRankDO,String startTimeStr,String endTimeStr) throws IOException, ParseException {

		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("id");
		}

		if (page.getPageSize() == null) {
			page.setPageSize(AstConst.PAGE_SIZE);
		}

		if (page.getStartIndex() == null) {
			page.setStartIndex(0);
		}
		
		// 获取开始时间
		if(StringUtils.isNotEmpty(startTimeStr)){
			productsKeywordsRankDO.setStartTime(DateUtil.getDate(startTimeStr, "yyyy-MM-dd"));
		}
		
		// 获取结束时间
		if(StringUtils.isNotEmpty(endTimeStr)){
			productsKeywordsRankDO.setEndTime(DateUtil.getDate(endTimeStr, "yyyy-MM-dd"));
		}
		
		Map<String, String > KEY_MAP = CategoryFacade.getInstance().getChild("1043");
		for (String code :KEY_MAP.keySet()) {
			if(KEY_MAP.get(code).equals(productsKeywordsRankDO.getType())){
				productsKeywordsRankDO.setType(code);
				break;
			}
		}
		
		productsKeywordsRankDO.setIsChecked(isChecked);
		productsKeywordsRankDTO.setExpire(expire);
		productsKeywordsRankDTO.setPage(page);
		productsKeywordsRankDTO.setProductsKeywordsRank(productsKeywordsRankDO);
		//DateUtil.getTheDayZero();
		List<ProductsKeywordsRankDTO> list=productsKeywordsRankService.queryProductsKeywordsRankByConditions(productsKeywordsRankDTO);
		page.setRecords(list);
		
		page.setTotalRecords(productsKeywordsRankService.countProductsKeywordsRankByConditions(productsKeywordsRankDTO));
		return printJson(page, model);
	}

	/**
	 * 初始化添加关键字页面
	 * 
	 * @param model
	 * @param id
	 *            供求信息编号
	 */
	@RequestMapping
	public void addnew(Map<String, Object> model, Integer id,Integer companyId,String account) {
		model.put("id", id);
		model.put("companyId", companyId);
		model.put("account", account);
	}
	
	@RequestMapping
	public ModelAndView queryById(Map<String, Object> model,Integer id) throws IOException{
		PageDto page=new PageDto();
		ProductsKeywordsRankDO  productsKeywordsRankDO = productsKeywordsRankService.queryProductsKeywordsRankById(id);
		Map<String, String > KEY_MAP = CategoryFacade.getInstance().getChild("1043");
		productsKeywordsRankDO.setType(KEY_MAP.get(productsKeywordsRankDO.getType()));
		List<ProductsKeywordsRankDO> list=new ArrayList<ProductsKeywordsRankDO>();
		list.add(productsKeywordsRankDO);
		page.setRecords(list);
		return printJson(page, model);
	}

	/**
	 * 保存/添加一条记录
	 * 
	 * @param model
	 * @param productsKeywordsRank
	 *            记录详细信息
	 * @return ExtResult {@link #ExtResult}
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView save(Map<String, Object> model,ProductsKeywordsRankDO productsKeywordsRank) throws IOException {
		ExtResult result=new ExtResult();
		Map<String, String > KEY_MAP = CategoryFacade.getInstance().getChild("1043");
		for (String code :KEY_MAP.keySet()) {
			if(KEY_MAP.get(code).equals(productsKeywordsRank.getType())){
				productsKeywordsRank.setType(code);
				break;
			}
		}
		Integer im = productsKeywordsRankService.insertProductsKeywordsRank(productsKeywordsRank);
		if (im.intValue()>0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
}

	/**
	 * 删除记录
	 * 
	 * @param out
	 * @param ids
	 *            要删除的记录的编号，如:1,2,3,4
	 * @return ExtResult {@link #ExtResult}
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> out, String ids)
			throws IOException {
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int impacted = 0;
		for (int ii = 0; ii < entities.length; ii++) {
			if (StringUtils.isNumber(entities[ii])) {
				Integer im = productsKeywordsRankService.deleteProductsKeywordsRankById((Integer.valueOf(entities[ii])));
				if (im.intValue() > 0) {
					impacted++;
				}
			}
		}
		if (impacted != entities.length) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setData(impacted);
		return printJson(result, out);
	}

	/**
	 * 审核记录
	 * 
	 * @param model
	 * @param ids
	 *            要审核的记录的编号，如:1,2,3,4
	 * @param value
	 *            审核状态<br/>
	 *            当value等于“1”时代表通过审核； 当value等于“0”时代表不通过审核。
	 * @return ExtResult {@link #ExtResult}
	 */
	@RequestMapping
	public ModelAndView updateChecked(String isChecked,String ids,Map<String, Object> out)
			throws IOException {
		ExtResult result = new ExtResult();
		result.setSuccess(productsKeywordsRankService.updateCheckedById(isChecked,ids));
		return printJson(result, out);

	}

	/**
	 * 更新记录
	 * 
	 * @param model
	 * @param productsKeywordsRank
	 *            信息详情
	 * @return ExtResult {@link #ExtResult}
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView update(Map<String, Object> model,ProductsKeywordsRankDO productsKeywordsRank) throws IOException {
			ExtResult result=new ExtResult();
//			if("金牌标王".equals(type)){
//				productsKeywordsRank.setType("10431000");
//			}else if("银牌标王".equals(type)){
//				productsKeywordsRank.setType("10431001");
//			}else if("铜牌标王".equals(type)){
//				productsKeywordsRank.setType("10431002");
//			}
			Integer im = productsKeywordsRankService.updateProductsKeywordsRankById(productsKeywordsRank);
			if (im.intValue()>0) {
				result.setSuccess(true);
			}
			return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView queryProductsByProductId(Integer productId, Map<String, Object> out) throws IOException {
		//TODO 重构
		ProductsDto products = productsService.queryProductsDetailsById(productId);
		return printJson(products, out);
	}
}
