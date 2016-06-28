/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.web.controller.zz91.admin.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.news.NewsModuleDO;
import com.ast.ast1949.dto.ExtCheckBoxTreeDto;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.news.NewsModuleService;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Controller
public class NewsModuleController extends BaseController {
	
	@Autowired
	private NewsModuleService newsModuleService;
	
	@RequestMapping
	public void view(){
		
	}
	
	@RequestMapping
	public void tree(){
		
	}
	
	/**
	 * 获取所有节点
	 * @param id 父节点编号
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView child(Integer id, Map<String, Object> model) throws IOException {
		if(id==null){
			id=0;
		}else if (id.equals("0")) {
			id=0;
		}

		List<ExtTreeDto> list = newsModuleService.queryExtTreeChildNodeByParentId(id);

		return printJson(list, model);
	}
	
	/**
	 * 初始带复选框(CheckBox)的树,根据父ID获取所有节点
	 * @param parentId 父节点编号
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView children(Integer parentId, String ids, Map<String, Object> model) throws IOException {
		if(parentId==null){
			parentId=0;
		}else if (parentId.equals("0")) {
			parentId=0;
		}

		List<ExtCheckBoxTreeDto> list = newsModuleService.queryExtCheckBoxTreeChildNodeByParentId(parentId,ids);

		return printJson(list, model);
	}

	/**
	 * 获取指定模块
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSimpleNewsModule(Integer id,Map<String, Object> model) throws IOException{
		PageDto pageDto =new PageDto();
		List<NewsModuleDO> list = new ArrayList<NewsModuleDO>();
		if(id!=null&&id.intValue()>0){
			list.add(newsModuleService.queryNewsModuleById(id));
		}else {
			list.add(new NewsModuleDO());
		}
		pageDto.setRecords(list);

		return printJson(pageDto, model);
	}
	
	/**
	 * 添加/编辑模块
	 * @param chartCategory
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="edit.htm",method=RequestMethod.POST)
	public ModelAndView edit(NewsModuleDO newsModule,Map<String, Object> model) throws IOException{
		ExtResult result =new ExtResult();
		if(newsModule.getId()!=null&&newsModule.getId()>0){
			if(newsModuleService.updateNewsModuleById(newsModule)>0){
				result.setSuccess(true);
			}
		}else {
			int i=newsModuleService.insertNewsModule(newsModule);
			if (i>0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}
	
	/**
	 * 删除模块
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Integer id,Map<String, Object> model) throws IOException {
		ExtResult result =new ExtResult();
		if(newsModuleService.deleteNewsModuleById(id)>0){
			result.setSuccess(true);
		}

		return printJson(result, model);
	}
	
}
