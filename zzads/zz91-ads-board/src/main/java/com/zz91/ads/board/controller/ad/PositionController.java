/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-13.
 */
package com.zz91.ads.board.controller.ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.domain.ad.DeliveryStyle;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.ExtTreeDto;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdPositionDto;
import com.zz91.ads.board.service.ad.AdPositionService;
import com.zz91.ads.board.service.ad.DeliveryStyleService;
import com.zz91.util.lang.StringUtils;

/**
 * 广告位控制器
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Controller
public class PositionController extends BaseController {
	@Resource
	private AdPositionService adPositionService;

	@Resource
	private DeliveryStyleService deliveryStyleService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void index() {
	}

	/**
	 * 获取子节点
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView child(Map<String, Object> model, String id) {
		List<ExtTreeDto> tree = new ArrayList<ExtTreeDto>();
		if (StringUtils.isNumber(id)) {
			tree = adPositionService.queryAdPositionNodesByParentId(Integer.parseInt(id));
		}

		return printJson(tree, model);
	}
	/**
	 *获取广告位 
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView detail(Map<String, Object> model) {
		List<Integer> list=new ArrayList<Integer>();
		list.add(632);//来电宝通用广告位
		list.add(639);//样品中心——通用广告位
		List<AdPosition> listA=new ArrayList<AdPosition>();
		for(Integer li:list){
			List<AdPosition> parent=adPositionService.queryAdPositionByParentId(li);
			for(AdPosition ad:parent){
				List<AdPosition> lists=adPositionService.queryAdPositionByParentId(ad.getId());
				AdPosition ads=new AdPosition();
				ads=adPositionService.queryAdPositionById(ad.getId());
				lists.add(ads);
				listA.addAll(lists);
			}
		}
		return printJson(listA, model);
	}

	/**
	 * 添加广告位
	 * 
	 * @param model
	 * @param adPosition
	 * @return
	 */
	@RequestMapping
	public ModelAndView add(Map<String, Object> model, AdPosition adPosition) {
		ExtResult result = new ExtResult();

		if (adPosition != null) {
			Integer id = adPositionService.insertAdPosition(adPosition);
			if (id != null && id.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 更新广告位
	 * 
	 * @param model
	 * @param adPosition
	 * @return
	 */
	@RequestMapping
	public ModelAndView update(Map<String, Object> model, AdPosition adPosition) {
		ExtResult result = new ExtResult();

		if (adPosition != null) {
			Integer id = adPositionService.updateAdPosition(adPosition);
			if (id != null && id.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 查询指定广告位
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryById(Map<String, Object> model, String id) {
		Pager<AdPositionDto> pager = new Pager<AdPositionDto>();
		if (StringUtils.isNumber(id)) {
			AdPositionDto obj = adPositionService.queryAdPositionDtoByIdForEdit((Integer
					.parseInt(id)));
			if (obj != null && obj.getAdPosition() != null
					&& obj.getAdPosition().getId().intValue() > 0) {
				List<AdPositionDto> list = new ArrayList<AdPositionDto>();
				list.add(obj);
				pager.setRecords(list);
			}
		}
		return printJson(pager, model);
	}

	/**
	 * 删除指定广告位
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model, String id) {
		ExtResult result = new ExtResult();

		if (StringUtils.isNumber(id)) {
			Integer im = 0;
			im = adPositionService.signDelete(Integer.parseInt(id));
			if (im != null && im.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 读取投放方式
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryDeliveryStyle(Map<String, Object> model) {
		Pager<DeliveryStyle> page = new Pager<DeliveryStyle>();
		page.setRecords(deliveryStyleService.queryDeliveryStyle());
		return printJson(page, model);
	}

}
