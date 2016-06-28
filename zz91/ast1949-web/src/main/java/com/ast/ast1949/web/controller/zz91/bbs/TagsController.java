/**
 * @author shiqp
 * 标签库的管理
 */
package com.ast.ast1949.web.controller.zz91.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.bbs.BbsPostTagsService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

@Controller
public class TagsController extends BaseController{
	@Resource
	private BbsPostTagsService bbsPostTagsService;
	@RequestMapping
	public void index(){
		
	}
	@RequestMapping
	public ModelAndView queryBbsTags(Map<String, Object>out,PageDto<BbsPostTags> page,Integer categoryId) throws IOException{
		page=bbsPostTagsService.pageTagsByCategory(categoryId, page);
		return printJson(page, out);
	} 
	@RequestMapping
	public ModelAndView addTag(Map<String, Object> out,BbsPostTags tag,String id) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(false);
		if(StringUtils.isEmpty(id)){
			Integer flag=bbsPostTagsService.addTags(tag);
			if(flag==1){
				result.setData("");
				result.setSuccess(true);
			}else if(flag==0){
				result.setData("您添加的标签已在库中，无需再添加");
			}else if(flag==2){
				result.setData("您的标签无法添加到标签库，请重新添加");
			}
		}else{
			tag.setId(Integer.valueOf(id));
			Integer flag=bbsPostTagsService.updateNameAndCategory(tag);
			if(flag>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	} 
	@RequestMapping
	public ModelAndView deleteTag(Map<String, Object> out,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(false);
		Integer in=bbsPostTagsService.deleteTags(id);
		if(in>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView init(Map<String, Object> out,Integer id) throws IOException{
		List<BbsPostTags> list = new ArrayList<BbsPostTags>();
		BbsPostTags tag=bbsPostTagsService.queryTagById(id);
		if(tag!=null){
			list.add(tag);
		}
		return printJson(list, out);
	}
	@RequestMapping
	public ModelAndView recom(Map<String, Object> out,BbsPostTags tag,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(false);
		tag.setId(id);
		Integer i=bbsPostTagsService.updateTag(tag);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

}
