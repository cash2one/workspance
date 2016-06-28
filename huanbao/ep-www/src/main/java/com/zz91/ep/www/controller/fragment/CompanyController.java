/*
 * 文件名称：CompanyController.java
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

import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.comp.SubnetCompRecommendService;
import com.zz91.ep.www.controller.BaseController;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：公司页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 *			2012-09-20		马元生				1.0.1		移到ep-api项目
 */
@Deprecated
@Controller
public class CompanyController extends BaseController {

	@Resource
	private CompProfileService compProfileService;
	
	@Resource
	private CompNewsService compNewsService;
	
	@Resource
	private SolrService solrService;
	
	@Resource
	private SubnetCompRecommendService subnetCompRecommendService;

	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新注册公司信息列表（信息填写完整的信息）。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newestCompany(HttpServletRequest request, Map<String, Object> out,String industryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompProfile> list = compProfileService.queryNewestCompany(industryCode,size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐公司列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendCompany(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompProfile> list = compProfileService.queryRecommendCompany(categoryCode, size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取公司发布资讯信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newestCompNews(HttpServletRequest request, Map<String, Object> out,String categoryCode, Integer cid, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompNews> list = compNewsService.queryNewestCompNews(categoryCode, cid, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取相关公司信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws UnsupportedEncodingException 
	 */
//	@RequestMapping
//	public ModelAndView keywordCompany(HttpServletRequest request, Map<String, Object> out,String keyword, Integer size) throws SolrServerException, UnsupportedEncodingException {
//		Map<String, Object> map = new HashMap<String, Object>();
////		String keywordEncode=StringUtils.decryptUrlParameter(keyword);
//		List<CompanySearchDto> list = solrService.queryCompanyByKeyword(keyword, size);
//		map.put("list", list);
//		return printJson(map, out);
//	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取子网推荐公司信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-06-27　　　齐振杰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView subRecommendComp(HttpServletRequest request,Map<String, Object> out,String cate,Integer size){
		List<CompProfile> list=subnetCompRecommendService.queryCompBySubRec(cate, size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
}