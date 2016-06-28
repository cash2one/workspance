/*
 * 文件名称：EsiteController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CreditFile;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.trade.TradeGroupDto;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.comp.CreditFileService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeGroupService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.ep.www.controller.BaseController;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：门市部页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 *			2012-09-20		马元生				1.0.1		移到ep-api项目
 */
@Deprecated
@Controller
public class EsiteController extends BaseController {
	
	@Resource
    private PhotoService photoService;
	
    @Resource
    private TradeGroupService tradeGroupService;
    
    @Resource
    private CompProfileService compProfileService;
    
	@Resource
	private TradeSupplyService tradeSupplyService;
	
	@Resource
	private CreditFileService creditFileService;
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取公司形象照片信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView compImage(HttpServletRequest request, Map<String, Object> out, Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
        if (cid != null) {
            List<Photo> compImage = photoService.queryPhotoByTargetType(PhotoService.TARGET_COMPANY, cid, 1);
            map.put("compImage", compImage);
        }
        return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取公司logo图片信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView logoImage(HttpServletRequest request, Map<String, Object> out, Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
        if (cid != null) {
            List<Photo> logoImage = photoService.queryPhotoByTargetType(PhotoService.TARGET_LOGO, cid, 1);
            map.put("logoImage", logoImage);
        }
        return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取公司分组信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView tradeGroup(HttpServletRequest request, Map<String, Object> out, Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
        if (cid != null) {
        	//获取自定义类别层次
            List<TradeGroupDto> tradeGroupDto= tradeGroupService.queryTradeGroupDtoById(cid);
            map.put("list", tradeGroupDto);
        }
        return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取公司联系方式。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView contact(HttpServletRequest request, Map<String, Object> out, Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
        if (cid != null) {
        	//获取公司联系方式
        	CompProfileDto compProfileDto= compProfileService.queryContactByCid(cid);
        	map.put("compProfileDto", compProfileDto);
        }
        return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendSupply(HttpServletRequest request, Map<String, Object> out, Integer cid, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<TradeSupply> list = tradeSupplyService.queryRecommendSupplysByCid(cid, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取荣誉证书
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView creditFiles(HttpServletRequest request, Map<String, Object> out, Integer cid, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		//荣誉证书
		List<CreditFile> creditFiles = creditFileService.queryCreditFileByCid(cid, null, (short)1);
		map.put("list", creditFiles);
		return printJson(map, out);
	}
}