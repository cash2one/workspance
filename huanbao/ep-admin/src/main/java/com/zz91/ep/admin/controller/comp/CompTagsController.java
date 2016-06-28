package com.zz91.ep.admin.controller.comp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompTagsService;
import com.zz91.ep.domain.comp.CompTags;
import com.zz91.ep.dto.ExtResult;

@Controller
public class CompTagsController extends BaseController {

	@Resource
	private CompTagsService compTagsService;

	/**
	 * 首页
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}

	/**
	 * 查询类别
	 * 
	 * @param request
	 * @param out
	 * @param parentId
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryComCategoryByParentId(HttpServletRequest request,
			Map<String, Object> out, Integer parentId) {
		List<CompTags> list = compTagsService
				.queryComCategoryByParentId(parentId);
		return printJson(list, out);
	}

	/**
	 * 添加标签
	 * 
	 * @param request
	 * @param out
	 * @param compTags
	 * @return
	 */
	@RequestMapping
	public ModelAndView addcomTags(HttpServletRequest request,
			Map<String, Object> out, CompTags compTags) {
		ExtResult result = compTagsService.addComTags(compTags);
		return printJson(result, out);

	}

	/**
	 * 修改公司标签
	 * 
	 * @param request
	 * @param out
	 * @param compTags
	 * @return
	 */
	@RequestMapping
	public ModelAndView updateCompTags(HttpServletRequest request,
			Map<String, Object> out, CompTags compTags) {

		ExtResult result = compTagsService.updateComTags(compTags);
		return printJson(result, out);

	}

	/**
	 * 删除标签
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param parentId
	 * @return
	 */
	@RequestMapping
	public ModelAndView deleteChildCategory(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer parentId) {
		ExtResult result = compTagsService.deleteChildCategory(id, parentId);
		return printJson(result, out);

	}
}
