package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.JudgeDto;
import com.ast.feiliao91.service.company.JudgeService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class EvaluationController extends BaseController {
	@Resource
	private JudgeService judgeService;

	@RequestMapping
	public ModelAndView discuss(Map<String, Object> out,
			PageDto<JudgeDto> page, Integer eval, Integer type2, Integer index,HttpServletRequest request) {
		SsoUser ssoUser=getCachedUser(request);
		Integer companyId =ssoUser.getCompanyId();
		Map<String, String> map = judgeService.getAvgStar(companyId, 12);
		out.put("evaluation", map);
		Map<String, Object> map2 = new HashMap<String, Object>();
		// 一个月内　好评
		Integer good = judgeService.getEvaluation(1, 0, companyId);
		// 一个月内　中评
		Integer goodIn = judgeService.getEvaluation(1, 1, companyId);
		// 一个月内　差评
		Integer goodpoor = judgeService.getEvaluation(1, 2, companyId);

		// 六个月内　好评
		Integer Threegood = judgeService.getEvaluation(6, 0, companyId);
		// 六个月内　中评
		Integer ThreegoodIn = judgeService.getEvaluation(6, 1, companyId);
		// 六个月内　差评
		Integer Threegoodpoor = judgeService.getEvaluation(6, 2, companyId);

		// 六个月前　好评
		Integer sexgood = judgeService.getEvaluation(1000, 0, companyId);
		// 六个月前　中评
		Integer sexgoodIn = judgeService.getEvaluation(1000, 1, companyId);
		// 六个月前　差评
		Integer sexgoodpoor = judgeService.getEvaluation(1000, 2, companyId);

		// 评价人数
		Integer count = judgeService.getcount(companyId);

		if (index == null) {
			index = 1;
		}
		page.setDir("desc");
		page.setSort("gmt_created");
		page.setPageSize(2);
		page.setStartIndex((Integer.valueOf(index) - 1) * page.getPageSize());
		// 获取信用积分
		Integer integral = judgeService.countTradeNum(companyId);
		if (eval == null) {
			eval = 1000;
		}
		// type2 0 所有评价　　１带评价的评价　　２无评价内容
		if (eval.equals(1000)) {
			page = judgeService
					.pageJudgeBySellAll(page, eval, companyId, type2);
		} else {
			page = judgeService
					.pageJudgeBySellAll(page, eval, companyId, type2);
		}
		out.put("resultMap", page.getRecords());
		out.put("integral", integral);
		out.put("page", page);
		map2.put("good", good);
		map2.put("goodIn", goodIn);
		map2.put("goodpoor", goodpoor);
		map2.put("Threegood", Threegood);
		map2.put("ThreegoodIn", ThreegoodIn);
		map2.put("Threegoodpoor", Threegoodpoor);
		map2.put("sexgood", sexgood);
		map2.put("sexgoodIn", sexgoodIn);
		map2.put("sexgoodpoor", sexgoodpoor);
		out.put("eval", map2);
		out.put("count", count);
		out.put("companyId", companyId);
		out.put("pageStr", pageString(index, page));
		SeoUtil.getInstance().buildSeo("discuss", out);
		return new ModelAndView();
	}

	// 买家评价
	@RequestMapping
	public ModelAndView getjudge(Integer companyId, Map<String, Object> out,
			PageDto<JudgeDto> page, Integer eval, Integer type2, Integer index)
			throws IOException {
		// type2 0 所有评价　　１带评价的评价　　２无评价内容
		if (index == null) {
			index = 1;
		}
		page.setPageSize(2);
		page.setDir("desc");
		page.setSort("gmt_created");
		page.setStartIndex((Integer.valueOf(index) - 1) * page.getPageSize());
		if (eval == null) {
			eval = 1000;
		}
		if (eval.equals(1000)) {
			page = judgeService.pageJudgeByAll(page, eval, companyId, type2);
		} else {
			page = judgeService.pageJudgeByAll(page, eval, companyId, type2);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", getEval(page.getRecords(), 2));
		map.put("pageStr", pageString(index, page));

		return printJson(map, out);
	}

	// 卖家评价
	@RequestMapping
	public ModelAndView getSelljudge(Integer companyId,
			Map<String, Object> out, PageDto<JudgeDto> page, Integer eval,
			Integer type2, Integer index) throws IOException {
		// type2 0 所有评价　　１带评价的评价　　２无评价内容
		if (index == null) {
			index = 1;
		}
		page.setPageSize(2);
		page.setDir("desc");
		page.setSort("gmt_created");
		page.setStartIndex((Integer.valueOf(index) - 1) * page.getPageSize());
		if (eval == null) {
			eval = 1000;
		}
		if (eval.equals(1000)) {
			page = judgeService
					.pageJudgeBySellAll(page, eval, companyId, type2);
		} else {
			page = judgeService
					.pageJudgeBySellAll(page, eval, companyId, type2);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", getEval(page.getRecords(), 1));
		map.put("pageStr", pageString(index, page));

		return printJson(map, out);
	}

	private List<String> getEval(List<JudgeDto> records, Integer pool) {
		if (records == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		String addre = "";
		for (JudgeDto dto : records) {

			if (dto.equals(null)) {
				continue;
			}
			String adr = "";
			if (dto.getPicAddress() != null) {
				for (String ad : dto.getPicAddress()) {
					adr = adr + "<span  bigimgSrc='"
							+ AddressTool.getAddress("img") + ad
							+ "'><img src='" + AddressTool.getAddress("img")
							+ ad + "' width=\"60\" height=\"60\"></span>";
				}
			}
			String goodJudge = "";
			if (dto.getJudge().getGoodJudge() != null) {
				goodJudge = dto.getJudge().getGoodJudge();
			}
			Integer cmy = null;
			Integer goodId = null;
			String cmyAddress = "";
			String goodAddress = "";
			String xinyu = "";
			if (pool.equals(1)) {
				cmy = dto.getInfo().getId();
				cmyAddress = "<p>卖家:<a href='" + AddressTool.getAddress("www")
						+ "/compinfo" + cmy + ".htm' class=\"blue\">"+dto.getInfo().getName();
				goodId = dto.getGoods().getId();
				goodAddress = "<a href='" + AddressTool.getAddress("www")
						+ "/goods" + goodId + ".htm' class=\"blue\">";
				xinyu = "<p class=\"pingj-pp-xy\" xyNum="+dto.getSellCred()+"></p>";
			} else if (pool.equals(2)) {
				cmy = dto.getBuyInfo().getId();
				cmyAddress = "<p>买家:<a href='" + AddressTool.getAddress("www")
						+ "/compinfo" + cmy + ".htm' class=\"blue\">"+dto.getBuyInfo().getName();
				goodId = dto.getGoods().getId();
				goodAddress = "<a href='" + AddressTool.getAddress("www")
						+ "/goods" + goodId + ".htm' class=\"blue\">";
			}
			addre = "<dd class=\"clearfix clearfix1\" style=\"position:relative;z-index:1\"><div class=\"list-dis-ts1 fl\"><div class=\"pingj-word\"><i class=\"good-pj pingj-tujie\"></i><p>"
					+ goodJudge
					+ "</p><div class=\"btmma btmma-img clearfix j_seepick\">"
					+ adr
					+ "</div><div class=\"btmma clearfix big_picture\" style=\"display:none\"><span class=\"pre_pic qhpic\"></span><span class=\"next_pic\"></span><img src=\"\" class=\"bigimg-pic\"></div><p class=\"pingj-time\">"
					+ DateUtil.toString(dto.getJudge().getGmtCreated(),
							"yyyy-MM-dd HH:mm:ss")
					+ "</p></div></div><div class=\"list-dis-ts2 fl\"><div class=\"pingj-pp\">"
					+ cmyAddress
					+ ""
					+ "</a></p>"
					+ xinyu
					+ "</div></div>"
					+ "<div class=\"list-dis-ts3 fl\"><div class=\"pingj-wpinfo\"><p class=\"wp-lianjie\">"
					+ goodAddress
					+ ""
					+ dto.getGoods().getTitle()
					+ "</a><p class=\"wpinfo-word clearfix\"><span class=\"fl\"><span class=\"red\">"
					+ dto.getPrice()
					+ "</span> 元</span><span class=\"fr\">（注：此价格不含邮费）</span></p></div></div><div class=\"list-dis-ts4 fl\"></div></dd>";
			list.add(addre);
		}
		return list;
	}

	public String pageString(Integer index, PageDto<?> page) {
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

}
