package com.kl91.wangpu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.Company;
import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.domain.dto.ExtResult;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.company.CreditFileSearchDto;
import com.kl91.domain.dto.company.EsiteFriendlinkSearchDto;
import com.kl91.domain.dto.products.ProductsSolrDto;
import com.kl91.domain.inquiry.Inquiry;
import com.kl91.domain.products.Products;
import com.kl91.service.company.CompanyService;
import com.kl91.service.company.CreditFileService;
import com.kl91.service.company.EsiteFriendlinkService;
import com.kl91.service.inquiry.InquiryService;
import com.kl91.service.products.ProductsService;
import com.kl91.service.products.ProductsSolrService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class WangpuController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private EsiteFriendlinkService esiteFriendlinkService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private CreditFileService creditFileService;
	@Resource
	private ProductsService productsService;
	@Resource
	private ProductsSolrService productsSolrService;

	@RequestMapping
	public void lxfs(HttpServletRequest request, Map<String, Object> out,
			String account) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			SeoUtil.getInstance().buildSeo("lxfs", out);
			initCompany(out, account);
		} while (false);
	}

	@RequestMapping
	public void index(HttpServletRequest request, Map<String, Object> out,
			String account, PageDto<ProductsSolrDto> page) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			SeoUtil.getInstance().buildSeo("index", out);
			initCompany(out, account);
			Company company = (Company) out.get("company");
			page.setPageSize(5);
			page.setRecords(productsSolrService.querySolrProductsByCompanyId(
					company.getId(), page));
			out.put("page", page);
		} while (false);

	}

	@RequestMapping
	public void liuyan(HttpServletRequest request, Map<String, Object> out,
			String account, Integer pid) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			if (pid != null) {
				out.put("pid", pid);
				out.put("title","我对您的"+productsService.queryById(pid).getTitle()+"产品感兴趣！");
			}
			SeoUtil.getInstance().buildSeo("liuyan", out);
			initCompany(out, account);
		} while (false);
	}

	@RequestMapping
	public ModelAndView doLiuyan(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String account, String title, String content, Integer cid,
			Integer pid) {
		do {
			if (cid == null) {
				// 未登录,无法留言
				out.put("result", 1);
				break;
			}
			ExtResult result = new ExtResult();
			Company company = companyService.queryByAccount(account);
			Inquiry inquiry = new Inquiry();
			inquiry.setContent(content);
			inquiry.setTitle(title);
			inquiry.setToCid(company.getId());
			inquiry.setFromCid(cid);
			inquiry.setIsToTrash(0);
			inquiry.setIsFromTrash(0);
			// 供求留言 or 公司留言
			if (pid != null) {
				inquiry.setTargetType(InquiryService.INQUIRY_FOR_PRODUCTS);
				inquiry.setTargetId(pid);
			} else {
				inquiry.setTargetType(InquiryService.INQUIRY_FOR_COMPANY);
				inquiry.setTargetId(company.getId());
			}
			Integer data = inquiryService.createInquiry(inquiry);
			if (data > 0) {
				result.setSuccess(true);
			}
			return new ModelAndView("redirect:success.htm");
		} while (false);
		return new ModelAndView("forward:liuyan.htm");
	}

	@RequestMapping
	public void success(Map<String, Object> out, String account) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			SeoUtil.getInstance().buildSeo("success", out);
			initCompany(out, account);
		} while (false);
	}

	@RequestMapping
	public void dangan(Map<String, Object> out, String account,
			CreditFileSearchDto searchDto) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			SeoUtil.getInstance().buildSeo("dangan", out);
			initCompany(out, account);

			Company company = (Company) out.get("company");
			if (company == null) {
				break;
			}
			searchDto.setCid(company.getId());
			out.put("list", creditFileService.queryFile(searchDto));
		} while (false);
	}

	@RequestMapping
	public void jieshao(Map<String, Object> out, String account) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			SeoUtil.getInstance().buildSeo("jieshao", out);
			initCompany(out, account);
		} while (false);
	}

	@RequestMapping
	public void products(Map<String, Object> out, String account,
			PageDto<ProductsSolrDto> page) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			SeoUtil.getInstance().buildSeo("products", out);

			initCompany(out, account);
			Company company = (Company) out.get("company");
			if (company == null) {
				break;
			}
			page.setPageSize(3);
			page.setRecords(productsSolrService.querySolrProductsByCompanyId(
					company.getId(), page));
			out.put("page", page);
		} while (false);
	}

	@RequestMapping
	public void productsDetail(Map<String, Object> out, Integer id,
			String account) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			// seo data
			Products product = productsService.queryById(id);
			SeoUtil.getInstance().buildSeo("productsDetail",
					new String[] { product.getTitle() },
					new String[] { product.getTitle() }, null, out);
			initCompany(out, account);

			out.put("product", product);
		} while (false);
	}

	private void initCompany(Map<String, Object> out, String account) {
		// 公司详细信息
		Company company = companyService.queryByAccount(account);
		out.put("company", company);

		// 友情链接
		Integer cid = company.getId();
		EsiteFriendlinkSearchDto esiteFriendlinkSearchDto = new EsiteFriendlinkSearchDto();
		esiteFriendlinkSearchDto.setCid(cid);
		List<EsiteFriendlink> esiteFriendlink = esiteFriendlinkService
				.queryFriendlink(esiteFriendlinkSearchDto);
		out.put("esiteFriendlink", esiteFriendlink);

	}

}
