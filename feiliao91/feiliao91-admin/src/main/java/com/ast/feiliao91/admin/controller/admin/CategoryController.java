package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.ExtTreeDto;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.service.commom.CategoryService;
import com.ast.feiliao91.service.facade.CategoryFacade;


@Controller
public class CategoryController extends BaseController {
	/*
	 * 类别管理列表显示界面
	 */
	@Autowired
	private CategoryService categoryService;
	
	//省市联动父节点(别的节点都属于这个节点位数加4*n)
	public final static String PROVINCE_CODE="1001";


	@RequestMapping
	public void index(HttpServletRequest request, Map<String, Object> out){
		
	}
	
	@RequestMapping
	public ModelAndView refreshCache(HttpServletRequest request, Map<String, Object> out){
//		CategoryFacade.getInstance().init(categoryService.queryCategoryList(),null);
		return null;
	}
	
//	@RequestMapping
//	public ModelAndView queryOne(HttpServletRequest request, Map<String, Object> out, String id) throws IOException{
//		return printJson(categoryService.queryCategoryByCode(id), out);
//	}
//	
	@RequestMapping
	public ModelAndView create(HttpServletRequest request, Map<String, Object> out, Category category, String parentCode) throws IOException{
		category.setParentcode(parentCode);
		Integer i=categoryService.insert(category);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOne(HttpServletRequest request, Map<String, Object> out, String id) throws IOException{
		return printJson(categoryService.selectByCode(id), out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request, Map<String, Object> out, String code) throws IOException {
		ExtResult result = new ExtResult();
		int i = categoryService.delete(code);
         if(i>0){
        	 result.setSuccess(true);
         }else {
			result.setSuccess(false);
		}
		return printJson(result,out);
	}
	
	@RequestMapping
	public ModelAndView update(HttpServletRequest request, Map<String, Object> out, Category category) throws IOException {
		ExtResult result = new ExtResult();
		int i = categoryService.updateCategory(category);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result,out);
	}
//	
//	@RequestMapping
//	public ModelAndView update(HttpServletRequest request, Map<String, Object> out, CategoryDO category) throws IOException {
//		ExtResult result = new ExtResult();
//		int i = categoryService.updateCategory(category);
//		if(i>0){
//			result.setSuccess(true);
//		}
//		return printJson(result,out);
//	}
//	
//	/***********************************************************/
//	
//	@Deprecated
//	@RequestMapping
//	public void getSingleRecord(String id) throws IOException {
//		int intId = Integer.parseInt(id);
//		Map<String, Object> map = new HashMap<String, Object>();
//		CategoryDO category = categoryService.queryCategoryById(intId);
//		List<CategoryDO> list = new ArrayList<CategoryDO>();
//		list.add(category);
//		map.put("records", list);
//
//	}
//
//	@Deprecated
//	@RequestMapping(value = "selectCategoiesByPreCode.htm")
//	public ModelAndView selectCategoiesByPreCode(String preCode, Map<String, Object> out) throws IOException {
//
//		List<CategoryDO> list=categoryService.queryCategoriesByPreCode(preCode);
//		PageDto<CategoryDO> page=new PageDto<CategoryDO>();
//		page.setRecords(list);
//		return printJson(page,out);
//	}
//	
//	@RequestMapping(value = "selectCategoiesByCode.htm")
//	public ModelAndView selectCategoiesByCode(String preCode, Map<String, Object> out) throws IOException {
//		List<CategoryDO> list=categoryService.queryCategoriesByPreCode(preCode);
//		PageDto<CategoryDO> page=new PageDto<CategoryDO>();
//		if (list.size()>0) {
//			CategoryDO categoryDO = new CategoryDO();
//			categoryDO.setCode("");
//			categoryDO.setLabel("全部");
//			list.add(categoryDO);
//		}
//		page.setRecords(list);
//		return printJson(page,out);
//	}
//	
//	/**
//	 * 跳转到类别管理主界面
//	 */
//	@Deprecated
//	@RequestMapping
//	public void view() {
//
//	}
//
	/**
	 * 通过父节点编号(code)获取子节点列表
	 * @param parentCode
	 *            父节点编号
	 * @param response
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value = "child.htm")
	public ModelAndView child(@RequestParam(required = false) String parentCode, Map<String, Object> out)
			throws IOException {
		if (parentCode == null||parentCode == "") {
			parentCode = "";
		} else if (parentCode.equals("0")) {
			parentCode = "";
		}
		List<ExtTreeDto> list = categoryService.child(parentCode);
		return printJson(list,out);
	}
//	/*
//	 *专门zz91后台交易中心里的公司库做的地区搜索
//	 */
//	@Deprecated
//	@RequestMapping(value = "childSearch.htm")
//	public ModelAndView childSearch(@RequestParam(required = false) String parentCode, Map<String, Object> out)
//			throws IOException {
//		if (parentCode == null||parentCode == "") {
//			parentCode = "";
//		} else if (parentCode.equals("0")) {
//			parentCode = "";
//		}
//		List<ExtTreeDto> list = categoryService.childSearch(parentCode);
//		return printJson(list,out);
//	}
//	
//	/**
//	 * 获得省市联动节点列表
//	 * @param parentCode
//	 *            父节点编号
//	 * @param response
//	 * @throws IOException
//	 */
//	@Deprecated
//	@RequestMapping(value = "childProvinces.htm")
//	public ModelAndView childProvinces(@RequestParam(required = false) String parentCode, Map<String, Object> out)
//			throws IOException {
//		if (parentCode == null||parentCode == "") {
//			parentCode = PROVINCE_CODE;
//		} else if (parentCode.equals("0")) {
//			parentCode = PROVINCE_CODE;
//		}
//		List<ExtTreeDto> list = categoryService.child(parentCode);
//		return printJson(list,out);
//	}
	/**
	 * 获得子类别（List类型）
	 * @author zhujq
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getChildListByMem(HttpServletRequest request, Map<String, Object> out, String parentCode) throws IOException{
		List<Category> list=new ArrayList<Category>();
		PageDto<Category> page=new PageDto<Category>();
			Map<String, String> umap = CategoryFacade.getInstance().getChild(parentCode);
			if (umap != null) {
				for(String s : umap.keySet()){
					Category c = new Category();
					c.setCode(s);
					c.setLabel(umap.get(s));
					list.add(c);
				}
			}
		page.setRecords(list);
		return printJson(page,out);
	}
}
