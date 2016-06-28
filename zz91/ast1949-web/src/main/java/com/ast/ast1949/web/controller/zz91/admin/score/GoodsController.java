/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-3.
 */
package com.ast.ast1949.web.controller.zz91.admin.score;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.score.ScoreGoodsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Controller
public class GoodsController extends BaseController {

	@Autowired
	private ScoreGoodsService scoreGoodsService;
	
	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view(Map<String, Object> model) {
		model.put("resourceUrl",MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
	}
	
	/**
	 * 读取物品列表
	 * @param model
	 * @param category 商品类别
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, Integer category,
			PageDto<ScoreGoodsDo> page) throws IOException {
		if (page == null) {
			page = new PageDto<ScoreGoodsDo>(AstConst.PAGE_SIZE);
		}
		page.setSort("id");
		page.setDir("desc");
		PageDto<ScoreGoodsDo> p = scoreGoodsService.pageScoreGoodsByCategory(category, page);

		return printJson(p, model);
	}
	
	/**
	 * 保存
	 * @param model
	 * @param goods
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView save(Map<String, Object> model,ScoreGoodsDo goods) throws IOException {
		ExtResult result = new ExtResult();
		
		Integer im=0;
		if(goods!=null&&goods.getId()!=null&&goods.getId().intValue()>0) {
			//更新记录
			im=scoreGoodsService.updateGoodsById(goods);
		} else {
			//添加记录
			im=scoreGoodsService.insertGoods(goods);
		}
		if (im.intValue()>0) {
			result.setSuccess(true);
			result.setData(im);
		}
		return printJson(result, model);
	}
	
	/**
	 * 读取一条记录
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSingleGoodsById(Map<String, Object> model,String id) throws IOException {
		PageDto<ScoreGoodsDo> page=new PageDto<ScoreGoodsDo>();
		if(StringUtils.isNumber(id)) {
			ScoreGoodsDo goods=scoreGoodsService.queryGoodsById(Integer.parseInt(id));
			List<ScoreGoodsDo> list=new ArrayList<ScoreGoodsDo>();
			list.add(goods);
			page.setRecords(list);
		}
		return printJson(page, model);
	}
	
	/**
	 * 删除
	 * @param model
	 * @param ids
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model,String ids) throws IOException{
		ExtResult result= new ExtResult();

		String[] entities=ids.split(",");

		int impacted=0;
		for(int ii=0;ii<entities.length;ii++){
			if(StringUtils.isNumber(entities[ii])){
				Integer im=scoreGoodsService.deleteGoodsById(Integer.valueOf(entities[ii]));
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
