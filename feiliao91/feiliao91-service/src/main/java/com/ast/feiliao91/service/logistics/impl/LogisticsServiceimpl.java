package com.ast.feiliao91.service.logistics.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.domain.logistics.Logistics;
import com.ast.feiliao91.persist.common.ParamDao;
import com.ast.feiliao91.persist.company.AddressDao;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.goods.OrderReturnDao;
import com.ast.feiliao91.persist.goods.OrdersDao;
import com.ast.feiliao91.persist.goods.PictureDao;
import com.ast.feiliao91.persist.logistics.LogisticsDao;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.logistics.LogisticsService;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

@Component("logisticsService")
public class LogisticsServiceimpl implements LogisticsService {
	@Resource
	private LogisticsDao logisticsDao;
	@Resource
	private OrderReturnDao orderReturnDao;
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private AddressDao addressDao;
	@Resource
	private PictureDao pictureDao;
	@Resource
	private ParamDao paramDao;
	@Override
	public Map<String, Object> selectLogisticsByCode(String code, String type) {

		Map<String, Object> out = new HashMap<String, Object>();
		Logistics oldLogistics = logisticsDao.selectLogisticsByCode(code);
		if (oldLogistics == null) {
			out.put("erro", "没有物流信息");
			return out;
		}
		//更新logistics
		OrderReturn orderReturn =orderReturnDao.queryByLogistics(oldLogistics.getLogisticsNo());
//		OrderReturn orderReturn=ls.get(0);
//		Map<String, Object> map5 = getMap(orderReturn.getDetailAll());
//		getLastestLogistics(oldLogistics.getLogisticsNo(),(String) map5.get("comp"));
		//再取logistics
		Logistics logistics = logisticsDao.selectLogisticsByCode(code);
//		OrderReturn orderReturn = orderReturnDao.queryByLogistics(logistics
//				.getLogisticsNo());
		if (orderReturn != null) {
			Orders order = ordersDao.selectById(orderReturn.getOrderId());
			CompanyInfo companyInfo = companyInfoDao.queryById(order
					.getBuyCompanyId());

			// 封面图片
			List<Picture> list = pictureDao.queryPictureByCondition(
					order.getGoodsId(), PictureService.TYPE_GOOD,
					order.getSellCompanyId(), 1);
			if (list != null && list.size() > 0) {
				out.put("picAddress", list.get(0).getPicAddress());
			}

			out.put("companyInfo", companyInfo);
			out.put("orderReturn", orderReturn);
		}
			if ("1".equals(type)) {
				List<Orders>  li=ordersDao.queryByLogistics(code);
				Orders ss = new Orders();
				for(Orders pp: li){
					if(pp!=null){
						ss=pp;
						break;
					}
				}
				String detail = ss.getDetails();
				if (StringUtils.isNotEmpty(detail)) {
					JSONObject map4 = JSONObject.fromObject(detail);
					String address = getArea(getMap(
							map4.get("Address").toString()).get("areaCode")
							.toString());
					String addre = getMap(map4.get("Address").toString()).get(
							"address").toString();
					String name = getMap(map4.get("Address").toString()).get(
							"name").toString();
					String mobile = getMap(map4.get("Address").toString()).get(
							"mobile").toString();
					if(map4.get("sell_post")!=null){
					String logisticsName= getMap(map4.get("sell_post").toString()).get(
							"logisticsCompany").toString();
					String phone= getMap(map4.get("sell_post").toString()).get(
							"logisticsCompanyPhone").toString();
					String code1=getMap(map4.get("sell_post").toString()).get(
							"logisticsNo").toString();
					out.put("logisticsCompany", logisticsName);
					out.put("logisticsPhone", phone);
					out.put("code", code1);
					}
					out.put("detailAll2", address + addre);
					out.put("name", name);
					out.put("mobile", mobile);
					Integer sellcompany = ss.getSellCompanyId();
					Address ad = addressDao.selectDefaultAddress(sellcompany);
					if (ad != null) {
						ad.setAreaCode(getArea(ad.getAreaCode()));
						out.put("selladdress", ad);
					}
				}
			} else if ("2".equals(type)) {
				if (StringUtils.isNotEmpty(orderReturn.getDetailAll())) {
					Map<String, Object> map3 = getMap(orderReturn
							.getDetailAll());
//					map3.put("company",paramDao.queryParamByKey((String) map3.get("company")).getValue());
					out.put("detailAll", map3);
				}
			}
		out.put("logistics", logistics);
		Map<String, Object> map = getmap(logistics.getLogisticsInfo());
		// 不存在物流信息
		if(map==null){
			return out;
		}
		// 查询结果状态：
		// 0：物流单暂无结果，
		// 1：查询成功，
		// 2：接口出现异常
		String status = (String) map.get("status");
		if ("0".equals(status)) {
			out.put("erro", "物流单暂无结果");
			return out;
		}
		if ("2".equals(status)) {
			out.put("erro", "查询异常");
			return out;
		}
		// 快递单当前的状态 ：　
		// 0：在途，即货物处于运输过程中；
		// 1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
		// 2：疑难，货物寄送过程出了问题；
		// 3：签收，收件人已签收；
		// 4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
		// 5：派件，即快递正在进行同城派件；
		// 6：退回，货物正处于退回发件人的途中；
		String obj = (String) map.get("state");
		// 获取跟踪信息
		String json = map.get("data").toString();
		String json1 = json.replaceAll("\\[", "");
		String json2 = json1.replaceAll("\\]", "");
		String[] arr = json2.split("},");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String get = "";
		// 分割切换成Map
		for (int i = 0; i < arr.length; i++) {
			get = arr[i] + "}";
			map = getmap(get);
			if (i == arr.length) {
				map = getmap(arr[i]);
			}
			list.add(map);
		}
		// 日期格式化
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 利用冒泡排序法排序
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size(); j++) {
				try {
					if ((format.parse((String) list.get(i).get("time")))
							.after(format.parse((String) (list.get(j)
									.get("time"))))) {
						Map<String, Object> tmp = list.set(i, list.get(j));
						tmp.put("Date",
								((String) tmp.get("time")).substring(0, 11));
						tmp.put("hour",
								((String) tmp.get("time")).substring(11));
						String week = getWeek(format.parse((String) tmp
								.get("time")));
						tmp.put("week", week);
						list.set(j, tmp);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		Map<String, Object> tmp = list.get(0);
		list.get(0).put("Date", ((String) tmp.get("time")).substring(0, 11));
		list.get(0).put("hour", ((String) tmp.get("time")).substring(11));
		try {
			list.get(0).put("week",
					getWeek(format.parse((String) tmp.get("time"))));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 拼装结果
		TreeMap<String, List<Map<String, Object>>> add = new TreeMap<String, List<Map<String, Object>>>();

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> ap = new HashMap<String, Object>();
			String time = (String) list.get(i).get("time");
			// 判断键值是否存在　
			List<Map<String, Object>> gou = add.get(time.substring(0, 11));
			if (gou == null) {
				gou = new ArrayList<Map<String, Object>>();
				ap.put("week", (String) list.get(i).get("week"));
			}
			String context = (String) list.get(i).get("context");
			ap.put("hour", time.substring(11));
			ap.put("context", context);
			gou.add(ap);
			add.put(time.substring(0, 11), gou);

		}
		out.put("state", obj);
		out.put("obj", obj);
		out.put("list", add);

		return out;
	}

	@Override
	public Integer insertLogistics(Logistics logistics) {
		return logisticsDao.insertLogistics(logistics);
	}

	@Override
	public Map<String, Object> getmap(String json) {

		return getMap(json);
	}

	// 将json字符串转换为map类型
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	// 给一个Date日期类型获取到星期
	private String getWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(date);
		String week1 = "周" + week.substring(2);
		return week1;
	}

	private String getArea(String areaCode) {
		String str = "";
		Integer i = 8;
		String tempCode = "";
		do {
			String fix = "";
			if (StringUtils.isEmpty(areaCode)) {
				break;
			}
			i = i + 4;
			if (areaCode.length() < i) {
				break;
			}
			tempCode = areaCode.substring(0, i);
			if (i == 12) {
				fix = "省";
			} else if (i == 16) {
				fix = "市";
			}
			str = str + CategoryFacade.getInstance().getValue(tempCode) + fix;
		} while (true);
		return str;
	}
	
	//物流公司依据键取对应的中文
	
	//快递100接口实现,并插入将返回的数据更新至logistics
	//参数为物流单号和物流公司
	@SuppressWarnings("static-access")
	public void getLastestLogistics(String LogisticsNo,String wuliu_com){
		try
		{	
			
			//身份授权key,(大小写敏感）
			String id = "21401df38d569f15";
			//要查询的快递公司代码，不支持中文,如debangwuliu对应徳邦物流
			String com = wuliu_com;
			//要查询的快递单号，请勿带特殊符号，不支持中文（大小写不敏感）
			String nu = LogisticsNo;
			//返回类型： 0：返回json字符串， 1：返回xml对象，2：返回html对象， 3：返回text文本。如果不填，默认返回json字符串。
//			String show = "0";
			//返回信息数量： 1:返回多行完整的信息， 0:只返回一行信息。 不填默认返回多行。 
//			String muti = "1";
			//排序： desc：按时间由新到旧排列， asc：按时间由旧到新排列。 不填默认返回倒序（大小写不敏感）
//			String order = "desc";
			
			String req="http://api.kuaidi100.com/api?id="+id+"&com="+com+"&nu="+nu+"&show=0&muti=1&order=desc";
//			URL url= new URL("http://api.kuaidi100.com/api?id=21401df38d569f15&com=debangwuliu&nu=325498582&show=0&muti=1&order=desc");
			URL url= new URL(req);
			URLConnection con=url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			String type = con.guessContentTypeFromStream(urlStream);
			String charSet=null;
			if (type == null)
			type = con.getContentType();

			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
				return ;

			if(type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);

			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			String content = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
				//String newContent = new String(b, 0, numRead);
				String newContent = new String(b, 0, numRead, charSet);
				content += newContent;
				}
			}
//			更新logistics_info
			logisticsDao.updateLogisticsByCode(LogisticsNo,content);
			urlStream.close();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getLogistics(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		Logistics obj = logisticsDao.selectLogisticsByCode(code);
		if (obj==null) {
			return null;
		}
		String info = obj.getLogisticsInfo();
		if (StringUtils.isEmpty(info)) {
			return null;
		}
		JSONObject js = JSONObject.fromObject(info);
		return js;
	}
	
	@Override
	public Integer updaLogisticsByCode(String code,String type){
		if(StringUtils.isEmpty(code)){
			return null;
		}
		//取出物流单
//		Logistics oldLogistics = logisticsDao.selectLogisticsByCode(code);
		if("1".equals(type)){
			//根据物流单取出发货单
			List<Orders> list = ordersDao.queryByLogistics(code);
			//解析发货单的details,获得物流公司代号
			if(list==null){
				return 0;
			}
			JSONObject jsonDetail = JSONObject.fromObject(list.get(0).getDetails());
			String comp="";
			Map<String, Object> map = getMap(JSONObject.fromObject(jsonDetail).toString());
			if(map.get("comp")!=null){
				comp = jsonDetail.get("comp").toString();
			}
			if(StringUtils.isNotEmpty(comp)){
				getLastestLogistics(code,comp);
				return 1;
			}
			return 0;
		}
		if("2".equals(type)){
			//根据物流单取出回退单
			OrderReturn list = orderReturnDao.queryByLogistics(code);
			//解析回退单的detail_all,获得物流公司代号
			if(list==null){
				return 0;
			}
			JSONObject jsonDetail = JSONObject.fromObject(list.getDetailAll());
			String comp = jsonDetail.get("comp").toString();
			if(StringUtils.isNotEmpty(comp)){
				getLastestLogistics(code,comp);
				return 1;
			}
			return 0;
		}
		return null;
	}
}
