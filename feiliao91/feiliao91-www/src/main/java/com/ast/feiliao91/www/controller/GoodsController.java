/**
 * @author shiqp
 * @date 2016-01-28
 */
package com.ast.feiliao91.www.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.domain.goods.Shopping;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.JudgeDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.service.company.JudgeService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.goods.ShoppingService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class GoodsController extends BaseController {
	@Resource
	private GoodsService goodsService;
	@Resource
	private PictureService pictureService;
	@Resource
	private JudgeService judgeService;
	@Resource
	private OrdersService ordersService;
	@Resource
	private ShoppingService shoppingService;

	@RequestMapping
	public ModelAndView goods(HttpServletRequest request,
			Map<String, Object> out, Integer id,String flag) {
		if (id == null) {
			return new ModelAndView("redirect:" + AddressTool.getAddress("www"));
		}
		// 登录信息
		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser!=null) {
			//查询用户对此商品是否评价过
			boolean i = ordersService.selecJudge(ssoUser.getCompanyId(), id);
			Integer inte = ordersService.getJudgeOrder(ssoUser.getCompanyId(), id);
			out.put("ssoUser", ssoUser);
			if (inte != null) {
				out.put("orderId", inte);
			}
			out.put("bool", i);
		}
		
		// 产品信息
		GoodsDto dto = goodsService.queryGoodInfoById(id);
		if (dto == null) {
			// 错误页面还没有，就先这样
			return new ModelAndView("redirect:" + AddressTool.getAddress("www"));
		}
		//运费显示
		String yunFei=dto.getGoods().getFare();
		// 商品详情
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(dto.getGoods().getGoodAttribute())) {
			String[] att = dto.getGoods().getGoodAttribute().split("\\|");
			for (String ss : att) {
				if (StringUtils.isNotEmpty(ss)) {
					String[] s = ss.replace("：", ":").split(":");
					// 数据必须都存在才显示，不然前台不展示
					if (s.length > 1&&StringUtils.isNotEmpty(s[0])&&StringUtils.isNotEmpty(s[1])) {
						map.put(s[0], s[1]);
					}
				}
			}
		}
		//标志　自动选中选项卡评价
        out.put("flag", flag);
        out.put("yunFei", yunFei);
		out.put("map", map);
		// String picString = "{";
		// 图片信息
		List<Picture> list = pictureService.queryPictureByCondition(dto.getGoods().getId(), PictureService.TYPE_GOOD, null, 5);
		out.put("picList", list);
		// 服务承诺
		out.put("dto", dto);
		// 交易量
		Float tquality = ordersService.countTradeQuality(id);
		DecimalFormat decimalFormat=new DecimalFormat(".000");
		String strTquality=decimalFormat.format(tquality);
		out.put("tquality", strTquality);
		// 成交笔数
		Integer tnumber = ordersService.countTradeNum(id);
		out.put("tnumber", tnumber);
		// 评价数据
		Map<String, Integer> mappj = judgeService.countJudgeNumByCondition(id);
		out.put("mappj", mappj);
		// 商铺信誉信息
		Map<String, String> mapxy = judgeService.getAvgStar(dto.getCompanyInfo().getId(), null);
		out.put("mapxy", mapxy);
		// 产品信誉
		String xy = judgeService.avgGoodStar(dto.getCompanyInfo().getId());
		out.put("xy", xy);
		// 最新产品信息 9条(改为分三页【相同关键字】第一页（type=1）取最新发布的  第二页（type=2）取销量最高的 第三页（type=3）取随机的 By zhujq)
//		List<GoodsDto> nineList = goodsService.listNewGoods(null, dto.getGoods().getMainCategory(), 9, dto.getGoods().getId());
		List<GoodsDto> nineList = goodsService.queryTypeBySameCategory(dto.getGoods().getMainCategory(),3,dto.getGoods().getId());
		out.put("nineList", nineList);
		// 最新产品信息 4条(改为【随机相同关键字】 By zhujq)
//		List<GoodsDto> forList = goodsService.listNewGoods(null, dto.getGoods().getMainCategory(), 4, dto.getGoods().getId());
		List<GoodsDto> forList = goodsService.queryRandomGoods(dto.getGoods().getMainCategory(), 4, dto.getGoods().getId());
		out.put("forList", forList);
		String dtoDetail="";
		if(StringUtils.isNotEmpty(dto.getDetail())){
			//SEO详细信息去除<html>的标签
			String des = Jsoup.clean(dto.getDetail(),Whitelist.none());
			if(des.length()<=55){
				dtoDetail=des;
			}else{
				dtoDetail=des.substring(0,55);
			}
		}
		SeoUtil.getInstance().buildSeo("goods",new String[]{dto.getGoods().getTitle()},new String[]{dto.getGoods().getTitle()},new String[]{dto.getCompanyInfo().getName(),dtoDetail}, out);
		return null;
	}

	/**
	 * 累计评论
	 * 
	 * @param out
	 * @param type
	 * @param index
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listJudge(Map<String, Object> out, String type,
			Integer index, Integer id) throws IOException {
		if (index == null) {
			index = 1;
		}
		PageDto<JudgeDto> page = new PageDto<JudgeDto>();
		page.setPageSize(3);
		page.setStartIndex((Integer.valueOf(index) - 1) * page.getPageSize());
		page = judgeService.pageJudgeByCondition(page, Integer.valueOf(type),
				null, null, id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", page.getRecords());
		map.put("pageStr", pageString(index, page, id));
		return printJson(map, out);
	}

	/**
	 * 成交记录
	 * 
	 * @param out
	 * @param index
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listTrade(Map<String, Object> out, Integer index,
			Integer id) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PageDto<OrdersDto> page = new PageDto<OrdersDto>();
		page.setPageSize(3);
		if (index==null) {
			page.setStartIndex(0);
		}else{
			page.setStartIndex((Integer.valueOf(index) - 1) * page.getPageSize());
		}
		page = ordersService.pageOrdersByGoodsId(page, id);
		map.put("list", page.getRecords());
		map.put("pageStr", pageString(index, page, id));
		return printJson(map, out);
	}

	public String pageString(Integer index, PageDto<?> page, Integer id) {
		String pageStr = "";
		if (page.getRecords() != null && page.getRecords().size() > 0) {
			pageStr = "<span class=\"page-do\"><</span>";
			if (index < 5) {
				if (page.getTotalPages() > 4) {
					for (Integer i = 0; i < 5; i++) {
						if ((i + 1) == Integer.valueOf(index)) {
							pageStr = pageStr
									+ "<span class=\"page-do this-page\">"
									+ (i + 1) + "</span>";
						} else {
							pageStr = pageStr + "<span class=\"page-do\">"
									+ (i + 1) + "</span>";
						}
					}
				} else {
					for (Integer i = 0; i < page.getTotalPages(); i++) {
						if ((i + 1) == Integer.valueOf(index)) {
							pageStr = pageStr
									+ "<span class=\"page-do this-page\">"
									+ (i + 1) + "</span>";
						} else {
							pageStr = pageStr + "<span class=\"page-do\">"
									+ (i + 1) + "</span>";
						}

					}
				}
			} else {
				int min = index - 3;
				int max = index + 2;
				if (max <= page.getTotalPages()) {
					for (Integer i = min; i < max; i++) {
						if ((i + 1) == Integer.valueOf(index)) {
							pageStr = pageStr
									+ "<span class=\"page-do this-page\">"
									+ (i + 1) + "</span>";
						} else {
							pageStr = pageStr + "<span class=\"page-do\">"
									+ (i + 1) + "</span>";
						}
					}
				} else {
					int minx = page.getTotalPages() - 5;
					for (Integer i = minx; i < page.getTotalPages(); i++) {
						if ((i + 1) == Integer.valueOf(index)) {
							pageStr = pageStr
									+ "<span class=\"page-do this-page\">"
									+ (i + 1) + "</span>";
						} else {
							pageStr = pageStr + "<span class=\"page-do\">"
									+ (i + 1) + "</span>";
						}
					}
				}
			}
			pageStr = pageStr + "<span class=\"page-do\">></span>";
		} else {
			pageStr = "";
		}
		return pageStr;
	}

	@RequestMapping
	public ModelAndView addShopping(HttpServletRequest request,
			Map<String, Object> out, String attribute, Integer id, String number)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer flag = 0;
		do {
			SsoUser user = getCachedUser(request);
			if (user == null) {
				break;
			}
			Shopping sp = new Shopping();
			sp.setGoodId(id);
			sp.setAttributes(attribute);
			sp.setNumber(number);
			sp.setBuyCompanyId(user.getCompanyId());
			flag = shoppingService.createShopping(sp);
			if (flag>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView addJudge(Judge judge, Integer orderId, String text,
			String service, String picAddress, Map<String, Object> out)
			throws IOException {
		ExtResult rs = new ExtResult();
		if (orderId == null) {
			rs.setData("评论失败");
			return printJson(rs, out);
		}

		Orders order = ordersService.selectById(orderId);
		if(picAddress!=null){
		List<String> pic = pictureService.selecPicById(picAddress);
		String address = "";
		if (pic != null) {
			for (String addr : pic) {
				address = address + addr + ",";
			}
			if (address != null) {
				address = address.substring(0, address.length() - 1);
			}
		}
		judge.setPicAddress(address);
		}
		judge.setGoodId(order.getGoodsId());
		judge.setOrderId(orderId);
		judge.setJudgeId(0);
		judge.setCompanyId(order.getBuyCompanyId());
		judge.setTargetId(order.getSellCompanyId());
		judge.setGoodJudge(text);
		judge.setServeJudge(service);
		// 图片地址
		judge.setIsDel(0);
		Integer i = judgeService.insert(judge);
		if (i > 0) {
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}
	
	
	
	/**
	 * 登陆页面
	 */
	@RequestMapping
	public ModelAndView loginpop(Map<String, Object> out,String type){
		out.put("type", type);
		return null;
	}
}
