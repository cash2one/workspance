/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-15.
 */
package com.zz91.ads.board.controller.ad;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.domain.ad.PositionExactType;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.ExactTypeService;
import com.zz91.ads.board.service.ad.PositionExactTypeService;
import com.zz91.util.lang.StringUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Controller
public class ExacttypeController extends BaseController {
	@Resource
	private ExactTypeService exactTypeService;
	@Resource
	private PositionExactTypeService positionExactTypeService;

	/**
	 * 读取所有精确条件
	 * 
	 * @param model
	 * @param pager
	 * @return
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, Pager<ExactType> pager) {
		// 分页排序设置
		if (pager != null) {
			if (pager.getSort() == null) {
				pager.setSort("id");
			}
			if (pager.getDir() == null) {
				pager.setDir("DESC");
			}
		} else {
			pager = new Pager<ExactType>();
			pager.setDir("DESC");
			pager.setSort("id");
		}
		// 读取数据
		pager = exactTypeService.pageExactType(pager);
		return printJson(pager, model);
	}

	/**
	 * 读取指定广告位的精确条件
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryForAdPosition(Map<String, Object> model, String id) {
		Pager<ExactType> pager = new Pager<ExactType>();
		if (StringUtils.isNumber(id)) {
			// 读取数据
			pager.setRecords(exactTypeService.queryExactTypeByAdPositionId(Integer.parseInt(id)));
		}
		return printJson(pager, model);
	}

	/**
	 * 添加关联
	 * 
	 * @param model
	 * @param exactType
	 * @return
	 */
	@RequestMapping
	public ModelAndView addRelated(Map<String, Object> model, PositionExactType positionExactType) {
		ExtResult result = new ExtResult();
		result.setSuccess(positionExactTypeService.savePositionExactType(positionExactType));
		return printJson(result, model);
	}

	/**
	 * 删除关联
	 * 
	 * @param model
	 * @param adPositionId
	 * @param exactTypeId
	 * @return
	 */
	@RequestMapping
	public ModelAndView delRelated(Map<String, Object> model, String adPositionId,
			String exactTypeId) {
		ExtResult result = new ExtResult();
		if (StringUtils.isNumber(adPositionId) && StringUtils.isNotEmpty(exactTypeId)) {
			Integer im = positionExactTypeService
					.deletePositionExactTypeByPositionIdAndExactTypeId(Integer
							.parseInt(adPositionId), Integer.parseInt(exactTypeId));

			if (im != null && im.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		
		return printJson(result, model);
	}

}
