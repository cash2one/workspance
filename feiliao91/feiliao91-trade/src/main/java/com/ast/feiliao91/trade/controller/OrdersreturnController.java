package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.logistics.Logistics;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
import com.ast.feiliao91.service.commom.ParamService;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.OrderReturnService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.logistics.LogisticsService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONObject;

@Controller
public class OrdersreturnController extends BaseController {
	@Resource
	private OrdersService ordersService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private OrderReturnService orderReturnService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private LogisticsService logisticsService;
	@Resource
	private CompanyServiceDao companyServiceDao;
	@Resource
	private AddressService addressService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private PictureService pictureService;
	@Resource
	private ParamService paramService;
	@Resource
	private TradeLogService tradeLogService;

	/**
	 * 申请退货第一步
	 * 
	 * @param out
	 * @param orderId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView thSpFrist(Map<String, Object> out, Integer orderId,
			HttpServletRequest request) throws ParseException {
		SsoUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:" + AddressTool.getAddress("www")
					+ "/login.htm");
		}
		Orders order = ordersService.selectById(orderId);
		if (order == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		if (!user.getCompanyId().equals(order.getBuyCompanyId())) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		GoodsDto goodsDto = goodsService.queryGoodInfoById(order.getGoodsId());
		// 退货订单信息
		OrderReturn orderReturn = orderReturnService.selectByOrdId(order
				.getId());
		if (orderReturn != null) {
			// 协商记录
			String json = orderReturn.getRecodeDetail();
			if (json != null) {
				Map<String, Object> map = getMap(json);
//				if(map!=null&&map.get("agree")!=null){
//					Object obj = map.get("agree");
//					if(obj!=null){
//						JSONArray cc=JSONArray.fromObject(obj);
//						if(cc!=null){
//							Object bb = cc.get(0);
//							if(bb!=null){
//								JSONArray dd=JSONArray.fromObject(bb);
//								if(dd!=null){
//									String code = (String) dd.get(3);
//									String []arr = code.split(",");
//									String addr = getArea(arr[2].toString());
//									arr[2]=addr;
//									StringBuffer bf = new StringBuffer();
//									for(int i=0;i<arr.length;i++){
//										bf.append(arr[i]);
//									}
//									dd.set(3, bf.toString());
//									cc.set(0, dd.toString());
//									map.put("agree",cc.toString());
//								}
//							}
//						}
//					}
//				}
				out.put("out", map);
			}
			String json2 = orderReturn.getDetailAll();
			if (json2 != null) {
				Map<String, Object> map = getMap(json2);
				if(map!=null&&map.get("areaCode")!=null){
				String addr = getArea((String)map.get("areaCode"));
				map.put("areaCode", addr);
				}
				out.put("detail", map);
			}
			out.put("orderReturn", orderReturn);
			out.put("ntime", DateUtil.toString(orderReturn.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
		}
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(order
				.getBuyCompanyId());

		Integer i = companyServiceDao.queryServiceCount(
				order.getSellCompanyId(),
				CompanyServiceService.SEVEN_DAY_SERVICE);
		if (i > 0) {
			out.put("sevenDay", 1);
		}
		Integer j = companyServiceDao.queryServiceCount(
				order.getSellCompanyId(), CompanyServiceService.BZJ_SERVICE);
		if (j > 0) {
			out.put("BZJ", 1);
		}

		// 物流公司选择列表
		List<Param> wuliuList = paramService.listParamByTypes("wuliu");
		for (Param wuliu : wuliuList) {
			String s = wuliu.getValue();
			String[] s1 = s.split(",");
			wuliu.setValue(s1[0]);// 仅仅设置物流公司名称
		}
		// 交易小计
		String obj = ""
				+ JSONObject.fromObject(order.getDetails())
						.get("orderGoodsPay");
		if (StringUtils.isNotEmpty(obj) && !"null".equals(obj)) {
			out.put("cost", obj);
		}
		// 交易运费
		String obj2 = ""
				+ JSONObject.fromObject(order.getDetails()).get(
						"orderFreightPay");
		if (StringUtils.isNotEmpty(obj2) && !"null".equals(obj2)) {
			out.put("orderFreightPay", obj2);
		}
		//总计
		String obj3 = ""
				+ JSONObject.fromObject(order.getDetails()).get(
						"orderTotalPay");
		if (StringUtils.isNotEmpty(obj3) && !"null".equals(obj3)) {
			out.put("orderTotalPay", obj3);
		}

		// 订单详情
		List<Orders> list = ordersService.queryOrdersByOrderNo(order
				.getOrderNo());
		// 计算运费
		Float yunfei = 0f;
		for (Orders or : list) {
			Goods good = goodsService.queryGoodById(or.getGoodsId());
			Float yunfei1 = 0f;
			if (good.getFare() != null) {
				try {
					yunfei1 = Float.valueOf(good.getFare());
					if(yunfei1<0){
						yunfei1=0f;
					}
				} catch (Exception e) {
					yunfei1 = 0f;
				}
				yunfei = yunfei + yunfei1;
			}
		}
		Float sumPrice = 0f;
		for (Orders or : list) {
			Float pri = or.getBuyPricePay();
			Float num = or.getBuyQuantity();
			if (num != null) {
				if (num < 0) {
					num = 0f;
				}
			}
			Float price = pri * num;
			sumPrice = sumPrice + price;
		}
        Float sun = sumPrice+yunfei;
		// Integer count = orderReturnService.selectByOrderId(order.getId());
		// 物流信息
		if (StringUtils.isNotEmpty(order.getLogisticsNo())) {
			Map<String, Object> map1 = logisticsService.selectLogisticsByCode(
					order.getLogisticsNo(), "1");
			out.put("logistics", map1);
		}

		java.text.DecimalFormat df = new java.text.DecimalFormat("#######0.0");
		String sumPrice1 = df.format(sumPrice);
		String yunfei1 = df.format(yunfei);
		String zongji = df.format(sun);
		out.put("zongji", zongji);
		out.put("companyInfo", companyInfo);
		out.put("sumPirce", sumPrice1);
		out.put("yunfei", yunfei1);
		out.put("wuliuList", wuliuList);
		out.put("order", order);
		out.put("goodsDto", goodsDto);

		SeoUtil.getInstance().buildSeo("orderReturn", out);
		return null;
	}

	/**
	 * 卖家处理退货申请
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping
	public ModelAndView thSpSrcdTy(Map<String, Object> out, Integer orderId,
			HttpServletRequest request, Integer flag) throws IOException, ParseException {

		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser == null) {
			return new ModelAndView("redirect:" + AddressTool.getAddress("www")
					+ "/login.htm");
		}
		if (orderId == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		Orders order = ordersService.selectById(orderId);
		if (!ssoUser.getCompanyId().equals(order.getSellCompanyId())) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		// 退货订单信息
		OrderReturn orderReturn = orderReturnService.selectByOrdId(order
				.getId());
		if (orderReturn != null) {
			// 协商记录
			Map<String, Object> map3 = getMap(orderReturn.getRecodeDetail());
			out.put("orderReturn", orderReturn);
//			if(map3!=null&&map3.get("agree")!=null){
//				Object obj = map3.get("agree");
//				if(obj!=null){
//					JSONArray cc=JSONArray.fromObject(obj);
//					if(cc!=null){
//						Object bb = cc.get(0);
//						if(bb!=null){
//							JSONArray dd=JSONArray.fromObject(bb);
//							if(dd!=null){
//								String code = (String) dd.get(3);
//								String []arr = code.split(",");
//								String addr = getArea(arr[2].toString());
//								arr[2]=addr;
//								StringBuffer bf = new StringBuffer();
//								for(int i=0;i<arr.length;i++){
//									bf.append(arr[i]);
//								}
//								dd.set(3, bf.toString());
//								cc.set(0, dd.toString());
//								map3.put("agree",cc.toString());
//							}
//						}
//					}
//				}
//			}
			out.put("out", map3);
			out.put("ntime", orderReturn.getGmtCreated().getTime());
			String json2 = orderReturn.getDetailAll();
			if (json2 != null) {
				Map<String, Object> map = getMap(json2);
				out.put("detail", map);
				if(map.get("deliveTime")!=null){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					map.get("deliveTime").toString();
					Date time=format.parse(map.get("deliveTime").toString());
					out.put("ntime", time.getTime());
					out.put("time", format.format(time));
				}
			}
		} else {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		// 订单信息
		GoodsDto goodsDto = goodsService.queryGoodInfoById(order.getGoodsId());
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(order
				.getBuyCompanyId());

		CompanyAccount companyAccount = companyAccountService
				.queryAccountByCompanyId(order.getBuyCompanyId());

		if (companyAccount != null) {
			if (StringUtils.isNotEmpty(companyAccount.getMobile())) {
				out.put("mobile", addHide(companyAccount.getMobile()));
			}
		}

		Integer i = companyServiceDao.queryServiceCount(
				order.getSellCompanyId(),
				CompanyServiceService.SEVEN_DAY_SERVICE);
		if (i > 0) {
			out.put("sevenDay", 1);
		}
		Integer j = companyServiceDao.queryServiceCount(
				order.getSellCompanyId(), CompanyServiceService.BZJ_SERVICE);
		if (j > 0) {
			out.put("BZJ", 1);
		}
		if (goodsDto.getCompanyInfo() != null) {
			List<Address> address = addressService.selectById(goodsDto
					.getCompanyInfo().getId());
			if (address.size() > 0) {
				for (Address ad : address) {
					ad.setAreaCode(getArea(ad.getAreaCode()));
				}
			}
			out.put("addressTWo", address);
		}

		List<Address> address = addressService.selectById(ssoUser
				.getCompanyId());
		if (address.size() > 0) {
			for (Address ad : address) {
				ad.setAreaCode(getArea(ad.getAreaCode()));
			}
		}

		// 订单详情
		List<Orders> list = ordersService.queryOrdersByOrderNo(order
				.getOrderNo());
		// 计算运费
		Float yunfei = 0f;
		for (Orders or : list) {
			Goods good = goodsService.queryGoodById(or.getGoodsId());
			Float yunfei1 = 0f;
			if (good.getFare() != null) {
				try {
					yunfei1 = Float.valueOf(good.getFare());
				} catch (Exception e) {
					yunfei1 = 0f;
				}
				yunfei = yunfei + yunfei1;
			}
		}
		Float sumPrice = 0f;
		for (Orders or : list) {
			Float pri = or.getBuyPricePay();
			Float num = or.getBuyQuantity();
			if (num != null) {
				if (num < 0) {
					num = 0f;
				}
			}
			Float price = pri * num;
			sumPrice = sumPrice + price;
		}
		sumPrice = sumPrice + yunfei;

		java.text.DecimalFormat df = new java.text.DecimalFormat("#######0.0");
		String sumPrice1 = df.format(sumPrice);
		String yunfei1 = df.format(yunfei);
		out.put("addressto", address);
		out.put("sumPirce", sumPrice1);
		out.put("yunfei", yunfei1);
		out.put("companyInfo", companyInfo);
		out.put("goodsDto", goodsDto);
		out.put("order", order);
		if (flag != null) {
			out.put("flag", flag);
			if (flag.equals(99)) {
				return new ModelAndView("/ordersreturn/paySsucc");
			}
		}
		SeoUtil.getInstance().buildSeo("orderReturn", out);
		return null;
	}

	/**
	 * 发送验证码
	 */
	@RequestMapping
	public ModelAndView sendCode(Map<String, Object> out, Integer orderId,
			HttpServletRequest request) throws IOException {
		SsoUser user = getCachedUser(request);
		ExtResult rs = new ExtResult();
		if (orderId == null || user == null) {
			rs.setSuccess(false);
			return printJson(rs, out);
		}
		Orders order = ordersService.selectById(orderId);
		// 退货订单信息
		OrderReturn orderReturn = orderReturnService.selectByOrdId(order
				.getId());
		CompanyAccount companyAccount = companyAccountService
				.queryAccountByCompanyId(user.getCompanyId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("price", orderReturn.getReurnPrice());
		companyValidateService.sendAllMobile(companyAccount.getMobile(), map);
		rs.setSuccess(true);
		return printJson(rs, out);
	}

	/**
	 * 申请退货(退款)
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView returnOrer(Map<String, Object> out, Integer orderId,
			String reason, Float price, String textarea, String picAddress,
			String type) throws IOException {
		ExtResult rs = new ExtResult();
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OrderReturn orderReturn = new OrderReturn();
		Orders order = ordersService.selectById(orderId);
		Integer count = orderReturnService.selectByOrderId(order.getId());
		if (count < 1) {
			CompanyInfo companyInfo = companyInfoService.queryInfoByid(order
					.getBuyCompanyId());
			orderReturn.setOrderId(orderId);
			orderReturn.setOrderNo(order.getOrderNo());
			orderReturn.setReturnReason(reason);
			orderReturn.setReurnPrice(price);
			// 暂时物流编号默认000000
			if ("0".equals(type)) {
				orderReturn.setLogisticsNo("000000");
			}
			if ("1".equals(type)) {
				orderReturn.setLogisticsNo(null);
			}
			orderReturn.setReturnRemark(textarea);
			if (picAddress != null) {
				List<String> li = pictureService.selecPicById(picAddress);
				String address = "";
				if (li != null) {
					for (String addr : li) {
						address = address + addr + ",";
					}
					if (address != null || address != "") {
						address = address.substring(0, address.length() - 1);
					}
				}
				orderReturn.setReturnPic(address);
			}
			orderReturn.setStatus(0);
			orderReturn.setCompanyId(order.getBuyCompanyId());
			orderReturn.setTargetId(order.getSellCompanyId());
			SimpleDateFormat formart = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startTime", formart.format(new Date()));
			orderReturn.setDetailAll(JSONObject.fromObject(map1).toString());
			// 插入协商记录
			String reson = "退货并退款";
			if (orderReturn.getLogisticsNo() == ""
					|| orderReturn.getLogisticsNo() == null) {
				reson = "退款";
			}
			Date date = new Date();
			List<String[]> list = new ArrayList<String[]>();
			Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
			String[] ar = new String[6];
			ar[0] = fomart.format(date);
			ar[1] = "买家(" + companyInfo.getName() + ")于" + fomart.format(date)
					+ "创建了退款申请";
			ar[2] = "退款类型:" + reson;
			ar[3] = "退款金额:" + price;
			ar[4] = "退款原因:" + reason;
			if (textarea != null) {
				ar[5] = "退款说明:" + textarea;
			}
			list.add(ar);
			map.put("regist", list);
			orderReturn.setRecodeDetail(JSONObject.fromObject(map).toString());
			Integer i = orderReturnService.insert(orderReturn);
			ordersService.updateStatus(orderId, 50);
			rs.setData(i);
		}
		rs.setSuccess(true);

		return printJson(rs, out);
	}

	/**
	 * 卖家拒绝退货(退款)
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView refused(Map<String, Object> out, Integer orderReturnId,
			String reason, String text) throws IOException {
		if (orderReturnId == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		ExtResult rs = new ExtResult();
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OrderReturn orderReturn = orderReturnService.selectById(orderReturnId);
		Orders order = ordersService.selectById(orderReturn.getOrderId());
		CompanyInfo Info = companyInfoService.queryInfoByid(order
				.getSellCompanyId());
		CompanyInfo Info2 = companyInfoService.queryInfoByid(order
				.getBuyCompanyId());
		Map<String, Object> map = getMap(orderReturn.getDetailAll());
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("refused", reason);
		map.put("refusedText", text);
		orderReturn.setDetailAll(JSONObject.fromObject(map).toString());
		orderReturn.setStatus(2);
		// 插入协商记录
		String reson = "退货退款";
		if (orderReturn.getLogisticsNo() == ""
				|| orderReturn.getLogisticsNo() == null) {
			reson = "退款";
		}
		Date date = new Date();
		List<String[]> list = new ArrayList<String[]>();
		Map<String, Object> js = getMap(orderReturn.getRecodeDetail());
		if (js == null) {
			js = new HashMap<String, Object>();
		}
		String[] ar = new String[4];
		ar[0] = fomart.format(date);
		ar[1] = "卖家(" + Info.getName() + ")于" + fomart.format(date) + "拒绝了买家("
				+ Info2.getName() + ")的" + reson + "申请";
		if (reason != null) {
			ar[2] = "拒绝原因:" + reason;
		}
		if (text != null) {
			ar[3] = "拒绝说明:" + text;
		}
		list.add(ar);
		js.put("refused", list);
		orderReturn.setRecodeDetail(JSONObject.fromObject(js).toString());
		orderReturnService.updateAll(orderReturn);
		rs.setData(order.getId());
		rs.setSuccess(true);
		return printJson(rs, out);

	}

	/**
	 * 查询退款申请
	 */
	@RequestMapping
	public ModelAndView returnRegit(Map<String, Object> out, Integer id)
			throws IOException {
		ExtResult rs = new ExtResult();
		OrderReturn orderReturn = orderReturnService.selectById(id);
		if (orderReturn != null) {
			rs.setSuccess(true);
			rs.setData(orderReturn);
		}
		return printJson(rs, out);
	}

	/**
	 * 修改退款申请
	 */
	@RequestMapping
	public ModelAndView updateRegit(Map<String, Object> out,
			Integer orderReturnId, Float reurnPrice, String returnReason,
			String returnRemark, String picAddress, String type)
			throws IOException {
		ExtResult rs = new ExtResult();
		OrderReturn orderReturn = orderReturnService.selectById(orderReturnId);
		if ("0".equals(type)) {
			orderReturn.setLogisticsNo("000000");
		}
		if ("1".equals(type)) {
			orderReturn.setLogisticsNo(null);
		}
		orderReturn.setReturnReason(returnReason);
		orderReturn.setReturnRemark(returnRemark);
		orderReturn.setReurnPrice(reurnPrice);
		if (picAddress != null) {
			List<String> li = pictureService.selecPicById(picAddress);
			String address = "";
			if (li != null) {
				for (String addr : li) {
					address = address + addr + ",";
				}
				if (address != null || address != "") {
					address = address.substring(0, address.length() - 1);
				}
			}
			orderReturn.setReturnPic(address);
		}
		orderReturn.setStatus(0);
		Integer i = orderReturnService.updateOrdersReturn(orderReturn);
		if (i > 0) {
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 卖家处理退货退款申请
	 */
	@RequestMapping
	public ModelAndView updateRegitTwo(Map<String, Object> out,
			Integer orderReturnId, Integer addressId, String text)
			throws IOException {
		ExtResult rs = new ExtResult();
		if (addressId == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		OrderReturn orderReturn = orderReturnService.selectById(orderReturnId);
		// 订单信息
		Orders order = ordersService.selectById(orderReturn.getOrderId());
		GoodsDto goodsDto = goodsService.queryGoodInfoById(order.getGoodsId());
		Map<String, String> map = new HashMap<String, String>();
		Address addre = addressService.selectAddress(addressId);
		map.put("areaCode", addre.getAreaCode());
		map.put("address", getArea(addre.getAddress()));
		map.put("name", addre.getName());
		map.put("mobile", addre.getMobile());
		if (text != null) {
			map.put("returnText", text);
		}
		JSONObject obj = JSONObject.fromObject(map);
		String json = obj.toString();
		OrderReturn or = new OrderReturn();
		or.setDetailAll(json);
		or.setId(orderReturnId);
		or.setStatus(1);
		or.setOrderNo(order.getOrderNo());
		Map<String, Object> js = getMap(orderReturn.getRecodeDetail());
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		List<String[]> list = new ArrayList<String[]>();
		String[] ar = new String[6];
		ar[0] = fomart.format(date);
		ar[1] = "【标题】卖家" + goodsDto.getCompanyInfo().getName() + "已经同意了申请";
		ar[2] = "【内容】";
		ar[3] = "退货地址为:" + map.get("name") + "," + map.get("mobile") + ","
				+ map.get("areaCode") + "," + map.get("address");
		if (text != null) {
			ar[4] = "退款说明:" + text;
		}else{
			ar[4] ="";
		}
		if (addre.getMobile() != null) {
			ar[5] = "商家电话:" + addre.getMobile();
		}else{
			ar[5] = "";
		}
		list.add(ar);
		js.put("agree", list);
		or.setRecodeDetail(JSONObject.fromObject(js).toString());
		Integer i = orderReturnService.updateOrdersReturnTwo(or);
		if (i > 0) {
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 添加退货物流信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addLogistics(Map<String, Object> out,
			Integer orderReturnId, String company, String sum, String phone,
			String text, String picAddress) throws IOException {
		ExtResult rs = new ExtResult();
		if (orderReturnId == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		OrderReturn orderReturn = orderReturnService.selectById(orderReturnId);
		// 将json 转换为Map格式
		Orders order = ordersService.selectById(orderReturn.getOrderId());
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(order
				.getBuyCompanyId());
		Map<String, Object> map = getMap(orderReturn.getDetailAll());
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		// 物流公司名称(字母拼音)
		map.put("comp", company);
		// 物流公司名称(中文)
		map.put("company", paramService.getValueBylogisticsCompany(company, 0));
		// 物流公司电话
		map.put("logisticsCompanyPhone",
				paramService.getValueBylogisticsCompany(company, 1));
		// 物流单号
		map.put("sum", sum);
		// 联系方式
		map.put("phone", phone);
		// 发货说明
		if (StringUtils.isNotEmpty(text)) {
			map.put("logisticsText", text);
		}
		// 上传图片
		if (picAddress != null) {
			List<String> li = pictureService.selecPicById(picAddress);
			String address = "";
			if (li != null) {
				for (String addr : li) {
					address = address + addr + ",";
				}
				if (address != null || address != "") {
					address = address.substring(0, address.length() - 1);
				}
			}
			map.put("picAddress", address);
		}
		JSONObject json = JSONObject.fromObject(map);
		String detail = json.toString();
		orderReturn.setLogisticsNo(sum);
		orderReturn.setDetailAll(detail);
		orderReturn.setStatus(3);
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		List<String[]> list = new ArrayList<String[]>();
		// 协商记录
		Map<String, Object> map2 = getMap(orderReturn.getRecodeDetail());
		String[] ar = new String[6];
		ar[0] = fomart.format(date);
		ar[1] = "【标题】买家（" + companyInfo.getName() + "）已经退货";
		ar[2] = "【内容】";
		ar[3] = "物流公司:" + map.get("company");
		ar[4] = "退货单号:" + orderReturn.getLogisticsNo();
		ar[5] = "退货地址:" + map.get("areaCode") + map.get("address");
		list.add(ar);
		// 保存信息
		map2.put("logistics", list);
		orderReturn.setRecodeDetail(JSONObject.fromObject(map2).toString());
		Integer i = orderReturnService.updateAll(orderReturn);
		// 向物流表保存信息
		Logistics logistics = new Logistics();
		logistics.setLogisticsNo(sum);
		logistics.setLogisticsCode(company);
		logistics.setLogisticsStatus(0);
		Integer j = logisticsService.insertLogistics(logistics);
		if (i > 0 && j > 0) {
			rs.setSuccess(true);
		}
		SeoUtil.getInstance().buildSeo("orderReturn", out);
		return printJson(rs, out);
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

	/**
	 * 验证信息
	 */
	@RequestMapping
	public ModelAndView yzm(Map<String, Object> out, String vcode,
			HttpServletRequest request, Integer orderReturnId)
			throws IOException {
		ExtResult rs = new ExtResult();
		Map<String, String> map1 = new HashMap<String, String>();
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount accu = companyAccountService
				.queryAccountByAccount(ssoUser.getAccount());
		Integer count = companyValidateService.validateByMobile(
				accu.getMobile(), vcode);
		if (count > 0) {
			// 退货订单信息
			OrderReturn orderReturn = orderReturnService
					.selectById(orderReturnId);
			Orders order = ordersService.selectById(orderReturn.getOrderId());
			GoodsDto goodsDto = goodsService.queryGoodInfoById(order
					.getGoodsId());
			Map<String, Object> map = getMap(orderReturn.getDetailAll());
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			Map<String, Object> map2 = getMap(orderReturn.getRecodeDetail());
			SimpleDateFormat fomrt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			map.put("successTime", fomrt.format(date));
			List<String[]> list = new ArrayList<String[]>();
			String[] ar = new String[3];
			ar[0] = fomrt.format(date);
			ar[1] = "【标题】卖家（" + goodsDto.getCompanyInfo().getName()
					+ "）已经同意了申请";
			ar[2] = "【内容】退款申请达成";
			list.add(ar);
			map2.put("pay", list);
			orderReturn.setDetailAll(JSONObject.fromObject(map).toString());
			orderReturn.setRecodeDetail(JSONObject.fromObject(map2).toString());
			orderReturn.setStatus(99);
			orderReturnService.updateAll(orderReturn);
			//买家信息
			CompanyAccount company=companyAccountService.queryAccountByCompanyId(orderReturn.getCompanyId());
			Float money = company.getSumMoney();
			companyAccountService.updateSumMoney(company.getCompanyId(), money+orderReturn.getReurnPrice());
			tradeLogService.paySucForSell(company.getCompanyId(), orderReturn.getOrderNo(), orderReturn.getReurnPrice());
			//卖家信息
			CompanyAccount sellcompany=companyAccountService.queryAccountByCompanyId(orderReturn.getTargetId());
			Float money1 = sellcompany.getSumMoney();
			if(money1>=orderReturn.getReurnPrice()){
			companyAccountService.updateSumMoney(sellcompany.getCompanyId(), money1-orderReturn.getReurnPrice());
			}else{
			companyAccountService.updateSumMoney(sellcompany.getCompanyId(), 0f);
			}
			tradeLogService.payOrder(sellcompany.getCompanyId(), orderReturn.getOrderNo(), orderReturn.getReurnPrice());
			rs.setSuccess(true);
		} else {
			map1.put("erro", "效证码错误");
			return printJson(map1, out);
		}
		return printJson(rs, out);
	}

	/**
	 * 加星隐藏保密隐私
	 * 
	 * @param str
	 * @return
	 */
	private String addHide(String str) {
		if (str.length() < 6) {
			return str;
		}
		int start = 0;
		if (str.length() > 7) {
			start = 3;
		} else {
			start = 3 - (8 - str.length());
		}
		int end = 0;
		if (str.length() < 9) {
			end = 1;
		} else {
			end = 1 + (str.length() - 8);
		}
		str = str.substring(0, start) + "****"
				+ str.substring(str.length() - end, str.length());
		return str;
	}

}
