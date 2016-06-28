package com.ast.ast1949.front.controller.cn;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.site.WebBaseDataStatService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class StarController extends BaseController{
	
	@Resource
	private WebBaseDataStatService webBaseDataStatService;
	@Resource
	private CompanyService companyService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private BbsService bbsService;
	@Resource
	private InquiryService inquiryService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,PageDto<CompanyDto> page,Company company){
		// 网站数据总数
		webBaseDataStatService.indexTotal(out);
		//最新加入再生通会员
		page.setPageSize(17);
		page = companyService.pageCompanyBySearchEngine(company, page);
		out.put("page", page);
		out.put("topPost", bbsService.queryTopPost(5, 2));
		//询盘商机
		out.put("listInquiry", getInquiry());
		SeoUtil.getInstance().buildSeo("mingxing", out);
		return null;
	}
	
	private List<InquiryDto> getInquiry() {
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		return list;
	}

	@RequestMapping
	public ModelAndView qyft(Map<String, Object> out,PageDto<PostDto> page){
		page.setPageSize(36);
		page=bbsPostService.pageMorePostByType("11", page);
		out.put("page", page);
		out.put("listInquiry", getInquiry());
		SeoUtil.getInstance().buildSeo("fangtan", out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView hyjy(Map<String, Object> out,PageDto<PostDto> page,Integer code){
		if(code !=null){
			page.setPageSize(36);
			page=bbsPostService.pagePostByCategory(code,null, page);
			out.put("page", page);
			out.put("code", code);
			out.put("typeCodeName", CATEGORY_CODE.get(code));
		}
		out.put("listInquiry", getInquiry());
		SeoUtil.getInstance().buildSeo("jingying", out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,HttpServletRequest request,Integer id) throws UnsupportedEncodingException{
		SsoUser sessionUser = getCachedUser(request);
		out.put("sessionUser", sessionUser);
		BbsPostDO bbsPostDO=bbsPostService.queryPostById(id);
		out.put("bbsPostDO", bbsPostDO);
		// 查询上一篇文章
		BbsPostDO onBbs = bbsPostService.queryOnBbsPostById(id);
		if (onBbs != null && bbsPostDO.getPostType().equals(onBbs.getPostType())) {
			out.put("onBbs", onBbs);
		}
		// 查询下一篇文章
		BbsPostDO downBbs = bbsPostService.queryDownBbsPostById(id);
		if (downBbs != null && bbsPostDO.getPostType().equals(downBbs.getPostType())) {
			out.put("downBbs", downBbs);
		}
		out.put("typeCodeName", CATEGORY_CODE.get(bbsPostDO.getBbsPostCategoryId()));
		out.put("listInquiry", getInquiry());
		String content=StringUtils.removeHTML(bbsPostDO.getContent());
		if(StringUtils.isNotEmpty(content)){
			content=content.replaceAll(" ", "");
			content=content.replaceAll("&nbsp", "");
			content=content.replaceAll(";", "");
			if(content.length()>=120){
				content=content.substring(0, 120);
			}
		}
		SeoUtil.getInstance().buildSeo("starDetail", new String[] { bbsPostDO.getTitle() },
				null, new String[] { content }, out);
		return null;
	}
	
	final static Map<Integer, String> CATEGORY_CODE = new HashMap<Integer, String>();
	static {
		CATEGORY_CODE.put(5, "废金属人物");
		CATEGORY_CODE.put(6, "废塑料人物");
		CATEGORY_CODE.put(7, "废纸人物");
		CATEGORY_CODE.put(8, "二手设备人物");
		CATEGORY_CODE.put(9, "纺织废料人物");
		CATEGORY_CODE.put(10, "其他人物");
	}
	
	
}
