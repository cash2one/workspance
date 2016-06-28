/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-27 by Rolyer.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.domain.information.ChartDataDO;
import com.ast.ast1949.domain.information.ChartsInfoDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ChartCategoryDTO;
import com.ast.ast1949.dto.information.ChartsInfoDTO;
import com.ast.ast1949.service.information.ChartCategoryService;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.service.information.ChartsInfoService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Controller
public class ChartsController extends BaseController {
	@Autowired
	private ChartCategoryService chartCategoryService;
	@Autowired
	private ChartsInfoService chartsInfoService;
	@Autowired
	private ChartDataService chartDataService;
	
	@RequestMapping
	public void category(){
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

		List<ExtTreeDto> list =chartCategoryService.queryExtTreeChildNodeByParentId(id);

		return printJson(list, model);
	}

	/**
	 * 获取指定类别
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSimpleChartsCategory(Integer id,Map<String, Object> model) throws IOException{
		PageDto pageDto =new PageDto();
		List<ChartCategoryDTO> list = new ArrayList<ChartCategoryDTO>();
		if(id!=null&&id.intValue()>0){
			list.add(chartCategoryService.queryChartCategoryDtoById(id));
		}else {
			list.add(new ChartCategoryDTO());
		}
		pageDto.setRecords(list);

		return printJson(pageDto, model);
	}
	
	/**
	 * 添加/编辑类别
	 * @param chartCategory
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="edit.htm",method=RequestMethod.POST)
	public ModelAndView edit(ChartCategoryDO chartCategory,Map<String, Object> model) throws IOException{
		ExtResult result =new ExtResult();
		if(chartCategory.getId()!=null&&chartCategory.getId()>0){
			if(chartCategoryService.updateChartCategoryById(chartCategory)>0){
				result.setSuccess(true);
			}
		}else {
			int i=chartCategoryService.insertChartCategory(chartCategory);
			if (i>0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}
	
	/**
	 * 删除类别
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping/*(value="sign.htm")*/
	public ModelAndView delete(Integer id,Map<String, Object> model) throws IOException {
		ExtResult result =new ExtResult();
		if(chartCategoryService.deleteChartCategoryById(id)>0){
			result.setSuccess(true);
		}

		return printJson(result, model);
	}
	
//	/**
//	 * 初始化列表页面
//	 */
//	@RequestMapping
//	public void list(){
//	}
	
	/**
	 * 获取走势图列表
	 * @param model
	 * @param page
	 * @param chartsInfoDTO
	 * @param chartsInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getChartsInfoList(Map<String, Object> model,PageDto page,ChartsInfoDTO chartsInfoDTO,ChartsInfoDO chartsInfo) throws IOException{
		if(page == null){
			page = new PageDto(AstConst.PAGE_SIZE);
		} else {
			if(page.getPageSize()==null){
				page.setPageSize(AstConst.PAGE_SIZE);
			}
			if (page.getStartIndex()==null) {
				page.setStartIndex(0);
			}
		}
		page.setSort("ci.id");
		page.setDir("desc");
		chartsInfoDTO.setPage(page);
		chartsInfoDTO.setChartsInfo(chartsInfo);
		
		page.setTotalRecords(chartsInfoService.countChartsInfoList(chartsInfoDTO));
		page.setRecords(chartsInfoService.queryChartsInfoList(chartsInfoDTO));

		return printJson(page, model);
	}
	
//	/**
//	 * 初始走势图信息编辑页面
//	 */
//	@RequestMapping
//	public void view(Map<String, Object> model,Integer id){
//		//获取父节点的setting值
//		if(id!=null&&id>0){
//			ChartCategoryDO parentCategory=new ChartCategoryDO();
//			parentCategory=chartCategoryService.queryChartCategoryById(id);
//			if(parentCategory!=null){
//				model.put("settings", parentCategory.getSetting());
//				model.put("parentName", parentCategory.getName());
//			}
//			
//			model.put("parentId", id);
//		}
//	}
	
	/**
	 * 初始走势图信息编辑表格
	 */
	@RequestMapping
	public void table(Map<String, Object> model,Integer id,Integer chartId){
		if(chartId==null){
			chartId=0;
		}
		//获取父节点的setting值
		if(id!=null&&id>0){
			ChartCategoryDO parentCategory=new ChartCategoryDO();
			parentCategory=chartCategoryService.queryChartCategoryById(id);
			if(parentCategory!=null){
				model.put("settings", parentCategory.getSetting());
				model.put("parentName", parentCategory.getName());
			}
			
			model.put("parentId", id);
		}
		model.put("chartId", chartId);
	}
	
	/**
	 * 
	 * @param model
	 * @param page
	 * @param chartCategory
	 * @param chartCategoryDTO
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getCategoryList(Map<String, Object> model,PageDto page,
			Integer chartInfoId,
			ChartCategoryDO chartCategory,ChartCategoryDTO chartCategoryDTO) throws IOException{
		if(page == null){
			page = new PageDto(100);
		} else {
//			if(page.getPageSize()==null){
//				page.setPageSize(100);
//			}
			if (page.getStartIndex()==null) {
				page.setStartIndex(0);
			}
		}
		page.setSort("cc.show_index");
		page.setDir("desc");
		page.setPageSize(100);
		chartCategoryDTO.setPage(page);
		
		if(chartCategory==null||chartCategory.getParentId()==null){
			chartCategory.setParentId(0);
		}
		chartCategoryDTO.setChartCategory(chartCategory);
		
		
		List<ChartCategoryDTO> list=chartCategoryService.queryChartCategoryListByParentId(chartCategoryDTO);

		
		if(chartCategory.getParentId().intValue()>0){
			list=chartCategoryService.initChartData(list,chartInfoId,chartCategory.getParentId());
		}
		
		page.setRecords(list);
		
		return printJson(page, model);
	}
	
	/**
	 *  初始化走势图类别下拉框
	 * @param id 父ID，默认值为“0”，即：根节点
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getCategoryComboList(Integer id, Map<String, Object> out) throws IOException {
		if(id==null){
			id=0;
		}
		List<ChartCategoryDO> list=chartCategoryService.queryChartCategoryByParentId(id);
		PageDto page=new PageDto();
		page.setRecords(list);
		return printJson(page,out);
	}
	
	/**
	 * 添加/修改走势图数据
	 * @param model
	 * @param chartData
	 * @return
	 * @throws IOExceptionPageDto
	 */
	@RequestMapping
	public ModelAndView setChartDataValue(Map<String, Object> model,ChartDataDO chartData) throws IOException{
		ExtResult result = chartDataService.setChartDataValue(chartData);
		return printJson(result, model);
	}
	
	@RequestMapping
	public void chart(Map<String, Object> model){
		
	}
	
	/**
	 * 添加走势图信息
	 * @param model
	 * @param chartsInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addChartInfo(Map<String, Object> model,ChartsInfoDO chartsInfo) throws IOException{
		ExtResult result = new ExtResult();
		Integer id = chartsInfoService.insertChartsInfo(chartsInfo);
		if(id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
	
	/**
	 * 获取指定的走势图信息
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSingleRecord(Map<String, Object> model,Integer id) throws IOException{
		PageDto page=new PageDto();
		ChartsInfoDO info=chartsInfoService.queryChartsInfoById(id);
		List<ChartsInfoDO> list=new ArrayList<ChartsInfoDO>();
		list.add(info);
		page.setRecords(list);
		
		return printJson(page, model);
	}
	
	/**
	 * 修改走势图信息
	 * @param model
	 * @param chartsInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView editChartInfo(Map<String, Object> model,ChartsInfoDO chartsInfo) throws IOException{
		ExtResult result = new ExtResult();
		Integer id = chartsInfoService.updateChartsInfoById(chartsInfo);
		if(id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
	
	/**
	 * 根据id删除报价数据
	 * @param model
	 * @param id 报价数据编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteinfo(Map<String, Object> model,String ids) throws IOException {
		
		ExtResult result= new ExtResult();

		String[] entities=ids.split(",");

		int impacted=0;
		for(int ii=0;ii<entities.length;ii++){
			if(StringUtils.isNumber(entities[ii])){
				Integer im=chartsInfoService.deleteChartsInfoById(Integer.valueOf(entities[ii]));
				if(im.intValue()>0){
					chartDataService.deleteChartDataByChartInfoId(Integer.valueOf(entities[ii]));
					impacted++;
				}
			}
		}

		if(impacted!=entities.length) {
			result.setSuccess(false);
		}else{
			result.setSuccess(true);
		}

		result.setData(impacted);

		return printJson(result, model);
	}
}
