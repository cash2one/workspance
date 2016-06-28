package com.zz91.ads.count.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.ads.count.service.AdsService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * 广告抓取servlet
 * 
 */
public class ZZ91ads extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ZZ91ads() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * <br />
	 * 提交的参数： <br />
	 * a：表示广告ID <br />
	 * p：表示广告位ID
	 * */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// step1 获取参数（基本）:广告位ID
		// step2 获取广告位和广告展示方式
		// step2.1 如果是精确广告，则获取精通条件，同时获取相应参数，根据精确条件获取匹配广告
		// step2.2 如果是普通广告，获取该广告位下的可用广告
		// step3 获取广告位对应的展示方式
		// step4 解析广告展示方式，输出结果，同时log广告行为

		Integer pid = Integer.valueOf(request.getParameter("p"));

		Map<String, Object> m = request.getParameterMap();
		Map<String, String> parameterMap = new HashMap<String, String>();
		for (String k : m.keySet()) {
			if (!"p".equals(k)) {
				parameterMap.put(k, StringUtils.decryptUrlParameter(request
						.getParameter(k)));
			}
		}

		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(AdsService.getInstance().showAd(pid,
				HttpUtils.getInstance().getIpAddr(request), parameterMap));
		pw.close();

		// end

		// AdPositionDto position = AdsService.getInstance().queryPosition(pid);
		// do {
		// if (position.getPosition() == null) {
		// break;
		// }
		//			
		// List<Ad> adlist = null;
		// if (position.getPosition().getHasExactAd() != null
		// && position.getPosition().getHasExactAd() == 1) {
		// // 精确广告
		// Map<Integer, String> map = AdsService.getInstance()
		// .queryExactByPosition(position.getPosition().getId());
		// Map<Integer, String> anchorMap = new HashMap<Integer, String>();
		// for (Integer p : map.keySet()) {
		// anchorMap.put(p,
		// StringUtils.decryptUrlParameter(request.getParameter(map
		// .get(p))));
		// // anchorMap.put(p, URLDecoder.decode(request.getParameter(map
		// // .get(p)), "utf-8"));
		// }
		// adlist = AdsService.getInstance().queryExactAd(
		// position.getPosition().getId(),
		// position.getPosition().getMaxAd(), anchorMap);
		// } else {
		// adlist = AdsService.getInstance().queryAd(
		// position.getPosition().getId(),
		// position.getPosition().getMaxAd());
		// }

		// response.setCharacterEncoding("utf-8");
		// PrintWriter pw = response.getWriter();
		// pw.print(AdsService.getInstance().buildAd(adlist,
		// position.getStyle(), position.getPosition().getWidth(),
		// position.getPosition().getHeight(),
		// HttpUtils.getInstance().getIpAddr(request)));
		// pw.close();
		// ServletOutputStream out=response.getOutputStream();
		// out.print(AdsService.getInstance().buildAd(adlist,
		// position.getStyle(), position.getPosition().getWidth(),
		// position.getPosition().getHeight(),
		// HttpUtils.getInstance().getIpAddr(request)));
		// } while (false);

	}
}
