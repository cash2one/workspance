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

import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreConversionHistoryDto;
import com.ast.ast1949.service.score.ScoreConversionHistoryService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Controller
public class ConversionController extends BaseController {

	@Autowired
	private ScoreConversionHistoryService scoreConversionHistoryService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view() {

	}

	/**
	 * 读取积分兑换申请
	 * 
	 * @param model
	 * @param conversion
	 *            参数包含：<br/>
	 *            status（兑换申请的状态：0 申请中；1 成功；2 失败。）
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, ScoreConversionHistoryDo conversion,
			PageDto<ScoreConversionHistoryDto> page) throws IOException {
		if (page == null) {
			page = new PageDto<ScoreConversionHistoryDto>(AstConst.PAGE_SIZE);
		}
		page.setSort("sch.gmt_modified");
		page.setDir("desc");
		PageDto<ScoreConversionHistoryDto> p = scoreConversionHistoryService
				.pageConversionHistoryWithGoods(conversion, page);

		return printJson(p, model);
	}

	/**
	 * 读取某商品的兑换历史
	 * 
	 * @param model
	 * @param goodsId
	 *            商品编号
	 * @param status
	 *            兑换申请的状态：0 申请中；1 成功；2 失败。
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryByGoodsId(Map<String, Object> model, Integer goodsId, String status,
			PageDto<ScoreConversionHistoryDto> page) throws IOException {
		if (page == null) {
			page = new PageDto<ScoreConversionHistoryDto>(AstConst.PAGE_SIZE);
		}
		page.setSort("id");
		page.setDir("desc");

		PageDto<ScoreConversionHistoryDto> p = new PageDto<ScoreConversionHistoryDto>();
		if (goodsId != null) {
			p = scoreConversionHistoryService.pageConversionHistoryByGoodsId(goodsId, status, page);
		}
		return printJson(p, model);
	}

	/**
	 * 读取一条记录
	 * 
	 * @param model
	 * @param id
	 *            编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSingleById(Map<String, Object> model, String id) throws IOException {
		PageDto<ScoreConversionHistoryDo> page = new PageDto<ScoreConversionHistoryDo>();
		if (StringUtils.isNumber(id)) {
			ScoreConversionHistoryDo goods = scoreConversionHistoryService
					.queryConversionHistoryById(Integer.parseInt(id));
			List<ScoreConversionHistoryDo> list = new ArrayList<ScoreConversionHistoryDo>();
			list.add(goods);
			page.setRecords(list);
		}
		return printJson(page, model);
	}

	/**
	 * 兑换成功
	 * 
	 * @param model
	 * @param id
	 * @param remark
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateSuccess(Map<String, Object> model, String id, String remark)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer im = scoreConversionHistoryService.updateConversionSuccess(Integer.parseInt(id),
				remark);

		if (im != null && im.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	/**
	 * 兑换失败
	 * 
	 * @param model
	 * @param id
	 * @param remark
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateFailure(Map<String, Object> model, String id, String remark)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer im = scoreConversionHistoryService.updateConversionFailure(Integer.parseInt(id),
				remark);
		if (im != null && im.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
}
