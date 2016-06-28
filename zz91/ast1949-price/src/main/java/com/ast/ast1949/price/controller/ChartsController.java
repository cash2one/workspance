/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-2 by Rolyer.
 */
package com.ast.ast1949.price.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.ChartDataDTO;
import com.ast.ast1949.service.information.ChartCategoryService;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Controller
public class ChartsController extends BaseController {
	final static String DEFAULT_ZST_ID = "7";
	final static Integer DEFAULT_ZST_CODE = 70;
	@Autowired
	private ChartCategoryService chartCategoryService;
	@Autowired
	private ChartDataService chartDataService;
	@Autowired
	private PriceService priceService;

	/***
	 * 存储走势度对应的id编号
	 */
	final static Map<String, Integer> idmap = new HashMap<String, Integer>();
	static {
		// 废铜废铝废铅废锌废锡废镍
		idmap.put("1", 70);
		idmap.put("7", 70);
		idmap.put("8", 70);
		idmap.put("9", 70);
		idmap.put("10", 70);
		// 光亮铜 黄杂铜
		idmap.put("2", 40);
		idmap.put("3", 40);
		idmap.put("13", 40);
		idmap.put("14", 40);
		idmap.put("15", 40);
		// 国产403
		idmap.put("4", 44);
		idmap.put("5", 44);
		idmap.put("17", 44);
		idmap.put("23", 44);
		// 锌合金
		idmap.put("6", 43);
		idmap.put("19", 43);
		idmap.put("21", 43);
		idmap.put("20", 43);
	}

	/**
	 * 初始化Chart页面
	 * 
	 * @param out
	 * @param id
	 *            类别编号
	 * @param f
	 * @param t
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, String id, String f, String t)
			throws ParseException {
		/**
		 * 1、验证请求的合法性 ->2 2、判断是父类还是子类 ->3.1 设置时间段 ->3.2 3 ->4
		 * 3.1、父类->找到第一个子类和其他子类 子类->找出同类 3.2 f$t都有值，以这俩个值为时间段，否则就当前月份减一
		 * 
		 * 4、查询出ChartData
		 */
		do {
			if(StringUtils.isEmpty(id)||idmap.get(id)==null){
				id = DEFAULT_ZST_ID;
			}
			if (!StringUtils.isNumber(id)) {
				break;
			}
			// Integer parentId=0;
			String parentName = "";
			String childName = "";
			@SuppressWarnings("unused")
			Integer relevanceId = 0;
			// 根据编号查找类别
			ChartCategoryDO chartCategory = chartCategoryService
					.queryChartCategoryById(Integer.valueOf(id));
			if (chartCategory == null) {
				break;
			}

			List<ChartCategoryDO> childList = new ArrayList<ChartCategoryDO>();

			if (chartCategory.getParentId() == 0) { // 当前为父类
				childList = chartCategoryService
						.queryChartCategoryByParentId(Integer.valueOf(id));
				if (childList.size() > 0) { // 取得第一个子类
					id = childList.get(0).getId().toString();
					childName = childList.get(0).getName();
				}
				parentName = chartCategory.getName();
				// parentId=chartCategory.getId();
				relevanceId = chartCategory.getRelevanceId();
			} else { // 当前为子类
				childList = chartCategoryService
						.queryChartCategoryByParentId(chartCategory
								.getParentId());
				ChartCategoryDO parent = chartCategoryService
						.queryChartCategoryById(chartCategory.getParentId());
				parentName = parent.getName();
				childName = chartCategory.getName();
				// parentId=parent.getId();
				relevanceId = parent.getRelevanceId();
			}

			// 时间段设置
			Date df = new Date();
			Date dt = new Date();
			if (StringUtils.isNotEmpty(f) && StringUtils.isNotEmpty(t)
					&& StringUtils.isSimpleDate(f)
					&& StringUtils.isSimpleDate(t)) {

				df = DateUtil.getDate(f, "yyyy-MM-dd");
				dt = DateUtil.getDate(t, "yyyy-MM-dd");
				// 保证f<t
				if (DateUtil.getIntervalDays(dt, df) < 0) {
					Date temp = df;
					df = dt;
					dt = temp;
				}
			} else {
				df = DateUtil.getDate(DateUtil.getDateAfterMonths(new Date(),-1), "yyyy-MM-dd");
				dt = DateUtil.getDate(new Date(), "yyyy-MM-dd");
			}
			/*
			 * if(relevanceId!=null){ //获取最新报价信息 List<PriceDO> list =
			 * priceService.queryLatestPriceByTypeId(relevanceId); //输出同类，已排序
			 * 
			 * model.put("list", list); }
			 */
			Integer tid = idmap.get(id);
			if(tid==null){
				tid = DEFAULT_ZST_CODE;
			}
			List<PriceDO> list = priceService.queryLatestPriceByTypeId(tid);
			out.put("list", list);
			out.put("childList", childList);
			out.put("id", id);
			out.put("f", DateUtil.toString(df, "yyyy-MM-dd"));
			out.put("t", DateUtil.toString(dt, "yyyy-MM-dd"));
			out.put("parentName", parentName);
			out.put("childName", childName);
			break;
		} while (true);
		SeoUtil.getInstance().buildSeo("zst",out);
		return null;
	}

	/**
	 * 生成折线图<br />
	 * 1 设置Chart;<br />
	 * e.g.<br />
	 * 2 设置LineChart;<br />
	 * 3 设置Dot;<br />
	 * 4 以JSON格式输出;
	 * 
	 * @param response
	 * @param p
	 * @param out
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public void data(HttpServletResponse response, String p,
			Map<String, Object> out) throws IOException, ParseException {
		setSiteInfo(new PageHeadDTO(), out);
		Integer id;
		String f;
		String t;
		String[] arrColor = { "0X763371", "0X457629", "0X203878", "0X154871",
				"0X170021", "0X456655", "0X851010", "0X162851", "0X154871",
				"0X572244" };
		try {
			String[] prams = p.split(",");

			id = Integer.valueOf(prams[0]);
			f = prams[1];
			t = prams[2];

			ChartCategoryDO child = chartCategoryService.queryChartCategoryById(id);
			ChartCategoryDO parent = chartCategoryService.queryChartCategoryById(child.getParentId());
			String[] name = parent != null ? parent.getSetting().split(";") : "".split(";");
			Map<String, List<ChartDataDTO>> chartdata = chartDataService.queryChartData(id, DateUtil.getDate(f, "yyyy-MM-dd"),DateUtil.getDate(t, "yyyy-MM-dd"), name);

			Chart flashChart = new Chart(parent != null ? parent.getName() : "","font-size:12px;color:#ff0000;"); // 整个图的标题。
			Text yText = new Text(" ", Text.createStyle(10, "#736AFF",
					Text.TEXT_ALIGN_LEFT)); // 标题样式，这里中文显示不了。
			LineChart line;
			XAxis x = new XAxis(); // X 轴
			List<Label> labelsList = new ArrayList<Label>();

			double max = 0.0; // Y 轴最大值
			double min = 0.0; // Y 轴最小值

			int i = 0;// 用于取颜色
			for (String key : chartdata.keySet()) {
				line = new LineChart(LineChart.Style.NORMAL);

				line.setFontSize(12); // 设置字体
				// line.setTooltip("#val#"); // 设置鼠标移到点上显示的内容
				line.setText(key); // 折线的名称
				line.setColour(arrColor[i]); // 折线的颜色
				i++;

				for (ChartDataDTO dto : chartdata.get(key)) {
					// 这是最大值，最小值
					if (max < dto.getChartData().getValue()) {
						max = dto.getChartData().getValue();
					}
					
					if(min==0.0){
						min = dto.getChartData().getValue();
					}
					
					if (min > dto.getChartData().getValue()) {
						min = dto.getChartData().getValue();
					}
					//
					line.addDots(new LineChart.Dot(dto.getChartData()
							.getValue()));
					// 设置时间轴
					if (labelsList != null) {
						boolean flag = true;
						for (Label s : labelsList) {
							// x轴 是否显示
							s.setVisible(true);
							s.setRotation(Rotation.DIAGONAL);
							if (s.getText().equals(
									DateUtil.toString(dto.getGmtDate(),
											"yyyy-MM-dd"))) {
								flag = false;
							}
						}

						if (flag) {
							Label l = new Label(DateUtil.toString(dto
									.getGmtDate(), "yyyy-MM-dd"));
							l.setRotation(Rotation.DIAGONAL);
							labelsList.add(l);
						}
					}
				}

				flashChart.addElements(line); // 添加一条折线
			}
			x.addLabels(labelsList);

			YAxis ycpu = new YAxis(); // Y 轴
			ycpu.setMax(Math.rint(max + (max - min) / 10)); // Y 轴最大值 四舍五入取整
			ycpu.setMin(min); //Y 轴最小值
			ycpu.setSteps(Math.rint((max - min) / 10)); // 递进值 四舍五入取整

			flashChart.setYAxis(ycpu); // 设置Y轴属性
			flashChart.setXAxis(x); // 设置X轴属性
			flashChart.setYLegend(yText); // 设置y轴显示内容

			String json = flashChart.toString(); // 转成JSON格式
			response.setContentType("application/json-rpc;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "No-cache");
			response.getWriter().print(json);// 写到客户端
		} catch (Exception e) {
		}
	}
	
	/**
	 * 生成折线图<br />
	 * 1 设置Chart;<br />
	 * e.g.<br />
	 * 2 设置LineChart;<br />
	 * 3 设置Dot;<br />
	 * 4 以JSON格式输出;
	 * 使用于报价资讯首页
	 * 
	 * @param response
	 * @param p
	 * @param out
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView getBeeChartData(HttpServletResponse response, String p,
			Map<String, Object> out) throws IOException, ParseException {
		setSiteInfo(new PageHeadDTO(), out);
		Integer id;
		String f;
		String t;
		List<String> indexAxisList = new ArrayList<String>();
		List<Map<String,Object>> dataSetsList =  new ArrayList<Map<String,Object>>();
		try {
			String[] prams = p.split(",");

			id = Integer.valueOf(prams[0]);
			f = prams[1];
			t = prams[2];

//			ChartCategoryDO child = chartCategoryService.queryChartCategoryById(id);
//			ChartCategoryDO parent = chartCategoryService.queryChartCategoryById(child.getParentId());
//			String[] name = parent != null ? parent.getSetting().split(";") : "".split(";");
//			Map<String, List<ChartDataDTO>> chartdata = chartDataService.queryChartData(id, DateUtil.getDate(f, "yyyy-MM-dd"),DateUtil.getDate(t, "yyyy-MM-dd"), name);
			List<ChartCategoryDO> list = chartCategoryService.queryChartCategoryByParentId(id);
			for(ChartCategoryDO obj :list){
				Map<Long, String> dataMap = new TreeMap<Long, String>();
				List<ChartDataDTO> cdList = chartDataService.queryChartData(obj.getId(), DateUtil.getDate(f, "yyyy-MM-dd"), DateUtil.getDate(t, "yyyy-MM-dd"));
				Map<String,Object> dataSetsMap = new HashMap<String, Object>();
				dataSetsMap.put("name",obj.getName());
				List<String> dataList = new ArrayList<String>();
				for (ChartDataDTO dto : cdList) {
					// 获得x轴 数据 小图 5个点
					Long l = dto.getGmtDate().getTime();
					if(dataMap.size()==5){
						if(dataMap.get(dto.getGmtDate().getTime())!=null){
							dataMap.put(l, String.valueOf(dto.getChartData().getValue()));
						}
					}else{
						dataMap.put(l, String.valueOf(dto.getChartData().getValue()));
					}
				}
				for(Long time:dataMap.keySet()){
					if(indexAxisList.size()<5){
						indexAxisList.add(DateUtil.toString(new Date(time), "M-d"));
					}
					dataList.add(dataMap.get(time));
				}
				dataSetsMap.put("values", dataList);
				dataSetsList.add(dataSetsMap);
			}
		} catch (Exception e) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> indexAxis = new HashMap<String, Object>();
		Map<String, Object> valueAxis = new HashMap<String, Object>();
		indexAxis.put("name", "");// x轴文字
		indexAxis.put("unit", "");// 单位
		indexAxis.put("labels", indexAxisList);
		
		valueAxis.put("name", ""); // y轴文字
		valueAxis.put("unit", ""); // 单位
		
		data.put("indexAxis", indexAxis);
		data.put("valueAxis", valueAxis);
		data.put("dataSets", dataSetsList);
		// 装载数据
		map.put("data", data);
//		{
//		    "data" : {
//		        "indexAxis" : {
//		            "name" : "月份",
//		            "unit" : "",
//		            "labels" : ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
//		        },
//		        "valueAxis" : {
//		            "name" : "温度",
//		            "unit" : "度"
//		        },
//		        "dataSets" : [
//		            {
//		                "name" : "Tokyo",
//		                "values" : [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
//		            },
//		            {
//		                "name" : "London",
//		                "values" : [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8],
//		            }
//		        ]
//		    }
//		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView getcategory(Map<String, Object> model, String id)
			throws IOException {
		if (!StringUtils.isNumber(id)) {
			id = "0";
		}
		JSONArray json = JSONArray.fromObject(chartCategoryService
				.queryChartCategoryTreeByParentId(Integer.valueOf(id)));
		return printJson(json, model);
	}
}
