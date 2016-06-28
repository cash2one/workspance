/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-2 by Rolyer.
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jofc2.model.Chart;
import jofc2.model.Text;
import jofc2.model.axis.Label;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.axis.Label.Rotation;
import jofc2.model.elements.LineChart;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.ChartDataDTO;
import com.ast.ast1949.service.information.ChartCategoryService;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Deprecated
@Controller
public class ChartsController extends BaseController {
	@Autowired
	private ChartCategoryService chartCategoryService;
	@Autowired
	private ChartDataService chartDataService;
	
	/**
	 * 初始化Chart页面
	 * @param model
	 * @param id 类别编号
	 * @param f 
	 * @param t
	 * @return 
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> model,String id,String f,String t) throws ParseException{
		setSiteInfo(new PageHeadDTO(), model);
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/charts/index.htm?id="+id);
	}
	
	/**
	 * 生成折线图<br />
	 * 1 设置Chart;<br />
	 * 	e.g.<br />
	 * 2 设置LineChart;<br />
	 * 3 设置Dot;<br />
	 * 4 以JSON格式输出;
	 * @param response
	 * @param p
	 * @param out
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public void data (HttpServletResponse response,String p, Map<String, Object> out) throws IOException, ParseException {
		setSiteInfo(new PageHeadDTO(), out);
		Integer id;
		String f;
		String t;
		String[] arrColor={"0X763371","0X457629","0X203878","0X154871","0X170021","0X456655","0X851010","0X162851","0X154871","0X572244"};
		try {
			String[] prams=p.split(",");
			
			id=Integer.valueOf(prams[0]);
			f=prams[1];
			t=prams[2];
			
			ChartCategoryDO child = chartCategoryService.queryChartCategoryById(id);
			ChartCategoryDO parent = chartCategoryService.queryChartCategoryById(child.getParentId());
			String[] name=parent!=null?parent.getSetting().split(";"):"".split(";");
			Map<String, List<ChartDataDTO>> chartdata = chartDataService.queryChartData(id, DateUtil.getDate(f,"yyyy-MM-dd"), DateUtil.getDate(t,"yyyy-MM-dd"), name);
			
			Chart flashChart = new Chart( parent!=null?parent.getName():"" , "font-size:12px;color:#ff0000;" ); // 整个图的标题。
			Text yText = new Text(" ",Text.createStyle(10, "#736AFF",Text.TEXT_ALIGN_LEFT));	// 标题样式，这里中文显示不了。
			LineChart line;
			XAxis x = new XAxis(); // X 轴
			List<Label> labelsList = new ArrayList<Label>();
			
			double max = 0.0; // Y 轴最大值
			double min = 0.0; // Y 轴最小值
			
			int i=0;//用于取颜色
			for (String key : chartdata.keySet()) {
				line = new LineChart(LineChart.Style.NORMAL);
				
				line.setFontSize(12); // 设置字体
//				line.setTooltip("#val#"); // 设置鼠标移到点上显示的内容
				line.setText(key); // 折线的名称
				line.setColour(arrColor[i]); // 折线的颜色
				i++;
				
				for(ChartDataDTO dto: chartdata.get(key)){
					//这是最大值，最小值
					if(max<dto.getChartData().getValue()) {
						max=dto.getChartData().getValue();
					}
					if (min>dto.getChartData().getValue()) {
						min=dto.getChartData().getValue();
					}
					//
					line.addDots(new LineChart.Dot(dto.getChartData().getValue()));
					//设置时间轴
					if(labelsList!=null){
						boolean flag=true;
						for (Label s : labelsList) {	/**
//							 * 1、验证请求的合法性					->2
//							 * 2、判断是父类还是子类					->3.1
//							 * 	    设置时间段							->3.2
//							 * 3									->4
//							 * 3.1、父类->找到第一个子类和其他子类	
//							 * 	          子类->找出同类					
//							 * 3.2 f$t都有值，以这俩个值为时间段，否则就当前月份减一
//							 * 	
//							 * 4、查询出ChartData
//							 */
//							do {
//								if(!StringUtils.isNumber(id)){
//									break;
//								}
////								Integer parentId=0;
//								String parentName="";
//								String childName="";
//								Integer relevanceId=0;
//								//根据编号查找类别
//								ChartCategoryDO chartCategory = chartCategoryService.queryChartCategoryById(Integer.valueOf(id));
//								if(chartCategory==null) {
//									break;
//								}
//								
//								List<ChartCategoryDO> childList =new ArrayList<ChartCategoryDO>();
//								
//								if(chartCategory.getParentId()==0){ //当前为父类
//									childList = chartCategoryService.queryChartCategoryByParentId(Integer.valueOf(id));
//									if(childList.size()>0){ //取得第一个子类
//										id=childList.get(0).getId().toString();
//										childName=childList.get(0).getName();
//									}
//									parentName=chartCategory.getName();
////									parentId=chartCategory.getId();
//									relevanceId=chartCategory.getRelevanceId();
//								} else { //当前为子类
//									childList = chartCategoryService.queryChartCategoryByParentId(chartCategory.getParentId());
//									ChartCategoryDO parent = chartCategoryService.queryChartCategoryById(chartCategory.getParentId());
//									parentName = parent.getName();
//									childName=chartCategory.getName();
////									parentId=parent.getId();
//									relevanceId=parent.getRelevanceId();
//								}
//								
//								//时间段设置
//								Date df=new Date();
//								Date dt=new Date();
//								if(StringUtils.isNotEmpty(f)&&StringUtils.isNotEmpty(t)&&StringUtils.isSimpleDate(f)&&StringUtils.isSimpleDate(t)){
//									
//									df = DateUtil.getDate(f,"yyyy-MM-dd");
//									dt = DateUtil.getDate(t,"yyyy-MM-dd");
//									//保证f<t
//									if(DateUtil.getIntervalDays(dt,df)<0){
//										Date temp=df;
//										df=dt;
//										dt=temp;
//									}
//								} else {
//									df = DateUtil.getDate(DateUtil.getDateAfterMonths(new Date(), -1),"yyyy-MM-dd");
//									dt = DateUtil.getDate(new Date(),"yyyy-MM-dd");
//								}
//								if(relevanceId!=null)
//								{
//									//获取最新报价信息
//									List<PriceDO> list = priceService.queryLatestPriceByTypeId(relevanceId);
//									//输出同类，已排序
//									model.put("list", list);
//								}
//								
//								model.put("childList", childList);
//								model.put("id", id);
//								model.put("f", DateUtil.toString(df, "yyyy-MM-dd"));
//								model.put("t", DateUtil.toString(dt, "yyyy-MM-dd"));
//								model.put("parentName", parentName);
//								model.put("childName", childName);
//								break;
//							} while (true);
//							s.setVisible(true);
							s.setRotation(Rotation.DIAGONAL);
							if(s.getText().equals(DateUtil.toString(dto.getGmtDate(), "yyyy-MM-dd"))){
								flag=false;
							}
						}
						
						if(flag){
							Label l= new Label(DateUtil.toString(dto.getGmtDate(), "yyyy-MM-dd"));
							l.setRotation(Rotation.DIAGONAL);
							labelsList.add(l);
						}
					} 
				}
				
				flashChart.addElements(line); // 添加一条折线
			}
			x.addLabels(labelsList);
			
			YAxis ycpu = new YAxis(); // Y 轴 
			ycpu.setMax(Math.rint(max+(max-min)/10)); //Y 轴最大值 四舍五入取整
//			ycpu.setMin(min); //Y 轴最小值 
			ycpu.setSteps(Math.rint((max-min)/10)); // 递进值 四舍五入取整
			
			flashChart.setYAxis(ycpu); // 设置Y轴属性
			flashChart.setXAxis(x); // 设置X轴属性
			flashChart.setYLegend(yText); // 设置y轴显示内容

			String json = flashChart.toString(); // 转成JSON格式
			response.setContentType( "application/json-rpc;charset=utf-8" );
			response.setHeader( "Cache-Control" , "no-cache" );
			response.setHeader( "Expires" , "0" );
			response.setHeader( "Pragma" , "No-cache" );
			response.getWriter().print(json);// 写到客户端
		} catch (Exception e) {
		}
	} 
	
	@RequestMapping(value = "getcategory.htm", method = RequestMethod.GET)
	public ModelAndView getcategory(Map<String, Object> model,String id) throws IOException{
		if(!StringUtils.isNumber(id)){
			id="0";
		}
		JSONArray json = JSONArray.fromObject(chartCategoryService.queryChartCategoryTreeByParentId(Integer.valueOf(id)));
		return printJson(json, model);
	}
}
