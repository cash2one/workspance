/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.dataindex.DataIndexDto;
import com.ast.ast1949.service.dataindex.DataIndexCategoryService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author yuyh
 * 
 */
@Controller
public class DataIndexController extends BaseController {

	@Autowired
	private DataIndexService dataIndexService;

	@Autowired
	private DataIndexCategoryService dataIndexCategoryService;
	

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {

		return null;
	}

	@RequestMapping
	public ModelAndView categoryChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		List<ExtTreeDto> list = dataIndexCategoryService.child(parentCode);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView deleteIndex(Integer id, Map<String, Object> out)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = dataIndexService.deleteDataIndex(id);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryDataIndex(Map<String, Object> out,
			HttpServletRequest request, DataIndexDO index, String pagesort,
			PageDto<DataIndexDO> page) throws IOException {
		page.setSort(pagesort);
		page = dataIndexService.pageDataIndex(index, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView initCategory(String id,
			Map<String, Object> out, HttpServletRequest request)
			throws IOException {
		DataIndexCategoryDO category = dataIndexCategoryService
				.queryDataIndexCategoryByCode(id);
		return printJson(category, out);
	}
	
	@RequestMapping
	public ModelAndView deleteCategory(Integer id, String code,
			Map<String, Object> out, HttpServletRequest request) throws IOException{
		ExtResult result = new ExtResult();
		Integer impact = dataIndexCategoryService.deleteCategoryByCode(code);
		if(impact!=null){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateCategory(Map<String, Object> out,
			HttpServletRequest request, DataIndexCategoryDO category)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=dataIndexCategoryService.updateDataIndexCategory(category);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView addCategory(Map<String, Object> out,
			HttpServletRequest request, DataIndexCategoryDO category,
			String parentCode) throws IOException{
		
		ExtResult result=new ExtResult();
		Integer i=dataIndexCategoryService.insertDataIndexCategory(category, parentCode);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(category.getCode());
		}
		return printJson(result, out);
	}

	/**
	 * 初始化首页模块管理页面
	 * 
	 * @param model
	 * @param id
	 * @param code
	 * @throws UnsupportedEncodingException
	 */

	// @RequestMapping
	// public void index(Map<String, Object> out, String url, String title)
	// throws UnsupportedEncodingException {
	//
	// title = StringUtils.decryptUrlParameter(title);
	// out.put("title", title);
	// out.put("url", url);
	//
	// }

	@RequestMapping(value = "add.htm", method = RequestMethod.POST)
	public ModelAndView add(DataIndexDO dataIndexDO, String fontSize,
			String color, Map<String, Object> out,String content) throws IOException {
		ExtResult result = new ExtResult();
		if(dataIndexDO.getSort()==null){
			dataIndexDO.setSort(0);
		}
		Integer i = dataIndexService.insertDataIndex(dataIndexDO);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

//	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView initDataIndex(Integer id, Map<String, Object> map)
			throws IOException {

//		PageDto page = new PageDto();
//		List<DataIndexDto> list = new ArrayList<DataIndexDto>();
//		if (id != null) {
			DataIndexDO dataIndex = dataIndexService.queryDataIndexById(id);
			DataIndexDto dto = new DataIndexDto();
			dto.setDataIndexDO(dataIndex);
			DataIndexCategoryDO category = dataIndexCategoryService
					.queryDataIndexCategoryByCode(dataIndex.getCategoryCode());
			if (category != null) {
				dto.setCategoryName(category.getLabel());
			}
//			list.add(dto);
//			page.setRecords(list);
//		}
		return printJson(dto, map);
	}

	@RequestMapping
	public ModelAndView update(DataIndexDO dataIndexDO, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = dataIndexService.updateDataIndex(dataIndexDO);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 新增和编辑类别信息
	 * 
	 * @param category
	 *            类别对象
	 * @param preCode
	 *            父类别编号
	 * @param res
	 * @throws IOException
	 */
//	@RequestMapping(value = "edit.htm")
//	public ModelAndView edit(DataIndexCategoryDO dataIndexCategoryDO,
//			String preCode, Map<String, Object> out) throws IOException {
//		int impacted = 0;
//		if (dataIndexCategoryDO.getId() == null
//				|| dataIndexCategoryDO.getId() <= 0) {
//			impacted = dataIndexCategoryService.insertDataIndexCategory(
//					dataIndexCategoryDO, preCode);
//		} else {
//			impacted = dataIndexCategoryService.updateDataIndexCategory(dataIndexCategoryDO);
//		}
//		ExtResult result = new ExtResult();
//		if (impacted > 0) {
//			result.setSuccess(true);
//		}
//		result.setData(impacted);
//		return printJson(result, out);
//
//	}
//
//	@RequestMapping
//	public ModelAndView deleteCategory() {
//
//		return null;
//	}
	
	/**
	 * 现货商城 推荐 至 首页模块 按钮
	 * @throws IOException
	 * 
	 */
	public ModelAndView spotDataIndex(Map<String, Object> out,Integer spotId) throws IOException{
		ExtResult result = new ExtResult();
		return printJson(result, out);
	}

}
