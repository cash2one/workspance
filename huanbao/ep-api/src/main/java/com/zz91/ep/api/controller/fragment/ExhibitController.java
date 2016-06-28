/*
 * 文件名称：ExhibitController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.api.controller.fragment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.api.controller.BaseController;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.exhibit.ExhibitDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.exhibit.ExhibitService;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：展会信息页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class ExhibitController extends BaseController {

	@Resource
	private ExhibitService exhibitService;
	
	@Resource
	private SolrService solrService;

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别展会列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView categoryExhibits(HttpServletRequest request, Map<String, Object> out, String categoryCode,String industryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<Exhibit> list = exhibitService.queryExhibitsByCategory(categoryCode, industryCode,size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别展会列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 *			2012-08-27		马元生				1.0.1		替换service方法名，由queryExhibitDtosByCategory变更为queryByCategory
	 */
	@RequestMapping
	public ModelAndView categoryExhibitDtos(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<ExhibitDto> list = exhibitService.queryByCategory(categoryCode, size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐资讯列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendExhibits(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<Exhibit> noticeExhibitList = exhibitService.queryExhibitsByRecommend(categoryCode, size);
		map.put("list", noticeExhibitList);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：不同类别导航内容。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView keywordExhibit(HttpServletRequest request, Map<String, Object> out, String keyword, Integer size) throws SolrServerException, UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
//		String keywordEncode=StringUtils.decryptUrlParameter(keyword);
		List<Exhibit> list = solrService.queryExhibitByKeywords(keyword, size);
		map.put("list", list);
		return printJson(map, out);
	}
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别展会列表及图片。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2013-09-14　　　方潮　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView queryExhibitsByCategoryCode(HttpServletRequest request, Map<String, Object> out, String categoryCode,String industryCode, Integer size){
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<ExhibitDto> dtoList = exhibitService.queryExhibitsByCategoryCode(categoryCode, industryCode, size);    
		map.put("list", dtoList);
		return printJson(map, out);
	}
	
}