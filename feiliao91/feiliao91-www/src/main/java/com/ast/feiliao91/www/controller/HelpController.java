package com.ast.feiliao91.www.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.seo.SeoUtil;

@Controller
public class HelpController extends BaseController {

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, String flag) {
		if (flag != null) {
			if (flag.equals("2")) {
				SeoUtil.getInstance().buildSeo("help", new String[] { "注册流程" },
						new String[] { "注册流程" },
						new String[] { "注册流程", "注册过程" }, out);
			} else if (flag.equals("3")) {
				SeoUtil.getInstance().buildSeo("help", new String[] { "更换密码" },
						new String[] { "更换密码" },
						new String[] { "更换密码", "更换过程" }, out);
			} else if (flag.equals("4")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "买家入门之如何买货" }, new String[] { "如何买货" },
						new String[] { "如何买货", "如何买货过程" }, out);
			} else if (flag.equals("5")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "买家入门之如何委托买货" },
						new String[] { "如何委托买货" },
						new String[] { "买家如何委托买货", "如何委托买货过程" }, out);
			} else if (flag.equals("6")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "买家入门之如何自己找货" },
						new String[] { "如何自己找货" },
						new String[] { "如何自己找货", "如何自己找货过程" }, out);
			} else if (flag.equals("7")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "买家入门之买家如何下订单" },
						new String[] { "如何下订单" },
						new String[] { "如何下订单", "如何下订单过程中" }, out);
			} else if (flag.equals("8")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "卖家入门之发布产品" }, new String[] { "发布产品" },
						new String[] { "卖家发布产品", "卖家发布产品过程" }, out);
			} else if (flag.equals("9")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "发货问题的争议处理(买家版）" },
						new String[] { "发货问题的争议处理(买家版）" },
						new String[] { "发货问题的争议处理(买家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("10")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "发货问题的争议处理(卖家版）" },
						new String[] { "发货问题的争议处理(卖家版）" },
						new String[] { "发货问题的争议处理(卖家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("11")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "签收问题的争议处理（买家版）" },
						new String[] { "签收问题的争议处理（买家版）" },
						new String[] { "签收问题的争议处理（买家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("12")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "签收问题的争议处理（卖家版）" },
						new String[] { "签收问题的争议处理（卖家版）" },
						new String[] { "签收问题的争议处理（卖家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("13")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "运费问题的争议处理（买家版）" },
						new String[] { "运费问题的争议处理（买家版）" },
						new String[] { "运费问题的争议处理（买家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("14")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "运费问题的争议处理（卖家版）" },
						new String[] { "运费问题的争议处理（卖家版）" },
						new String[] { "运费问题的争议处理（卖家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("15")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "争议处理（买家版）" },
						new String[] { "争议处理（买家版）" },
						new String[] { "争议处理（买家版）", "对于争议处理流程" }, out);
			} else if (flag.equals("16")) {
				SeoUtil.getInstance().buildSeo("help",
						new String[] { "争议处理（卖家版）" },
						new String[] { "争议处理（卖家版）" },
						new String[] { "争议处理（卖家版）", "对于争议处理流程" }, out);
			}
		} else {
			SeoUtil.getInstance().buildSeo("help", new String[] { "注册流程" },
					new String[] { "注册流程" }, new String[] { "注册流程", "注册过程" },
					out);
		}
		out.put("flag", flag);
		return null;
	}

}
