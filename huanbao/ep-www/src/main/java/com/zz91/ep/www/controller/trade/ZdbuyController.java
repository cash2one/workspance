/*
 * 文件名称：ZdbuyController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.trade;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.trade.IbdCompany;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompanyNormDto;
import com.zz91.ep.service.trade.IbdCompanyService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：行业买家库
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class ZdbuyController extends BaseController {
    
	@Resource
	private IbdCompanyService ibdCompanyService;
	/**
	 * 函数名称：list
	 * 功能描述：行业买家库信息列表页初始化
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView list(Map<String, Object> out, HttpServletRequest request, String keywords, PageDto<IbdCompany> page,String categoryCode) {
		
    	if (StringUtils.isNotEmpty(keywords)) {
			out.put("keywords", keywords);
		}
    	if (StringUtils.isNotEmpty(categoryCode)) {
    			out.put("categoryCode", categoryCode);
    		}
    		page = ibdCompanyService.pageCompanyByCategoryAndKewords(categoryCode, keywords, page);
    		out.put("page", page);
    		// SEO
    		if (StringUtils.isEmpty(categoryCode)) {
    			SeoUtil.getInstance().buildSeo("tradeZdbuy", null, null, null, out);
    		} else {
    			SeoUtil.getInstance().buildSeo("tradeZdbuylist", null, null, null, out);
    		}
    	return null;
    }
    
	/**
	 * 函数名称：detail
	 * 功能描述：行业买家库信息最终页初始化
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView detail(Map<String, Object> out, HttpServletRequest request, Integer id) {
    	IbdCompany company = ibdCompanyService.queryIbdCompanyById(id);
    	if (company == null) {
    		return new ModelAndView("redirect:"+AddressTool.getAddress("trade"));
		}
    	out.put("company", company);
    	//SEO
    	String[] titleParam = {company.getName(),company.getIndustry()};
    	String[] keywordsParam = {company.getIndustry()};
    	String[] descriptionParam = {company.getName(),company.getIndustry()};
        SeoUtil.getInstance().buildSeo("tradeZdbuydetail", titleParam, keywordsParam, descriptionParam, out);
    	return null;
    }
    
    /**
	 * 函数名称：viewBuyContacts
	 * 功能描述：查看联系方式
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView viewBuyContacts(Map<String, Object> out,
			HttpServletRequest request, Integer id) throws SolrServerException {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			// 会员信息
			String memberCode = EpAuthUtils.getInstance().getEpAuthUser(request, null) == null ? "" : EpAuthUtils.getInstance().getEpAuthUser(request, null).getMemberCode();
			if (memberCode != null && memberCode.equals("10011001")) {
				result.setData(ibdCompanyService.queryContactByCid(id));
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

}