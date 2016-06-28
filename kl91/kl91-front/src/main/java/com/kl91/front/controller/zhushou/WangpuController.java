package com.kl91.front.controller.zhushou;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.Company;
import com.kl91.domain.company.CreditFile;
import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.domain.dto.company.CreditFileSearchDto;
import com.kl91.domain.dto.company.EsiteFriendlinkSearchDto;
import com.kl91.front.controller.BaseController;
import com.kl91.service.company.CompanyService;
import com.kl91.service.company.CreditFileService;
import com.kl91.service.company.EsiteFriendlinkService;
import com.zz91.util.lang.StringUtils;

@Controller
public class WangpuController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private CreditFileService creditFileService;
	@Resource
	private EsiteFriendlinkService esiteFriendlinkService;

	@RequestMapping
	public void dangan(HttpServletRequest request, Map<String, Object> out,
			CreditFileSearchDto searchDto) {
		Integer cid = getSessionUser(request).getCompanyId();
		out.put("companyId", cid);
		searchDto.setCid(cid);
		out.put("list", creditFileService.queryFile(searchDto));
	}

	@RequestMapping
	public ModelAndView createDangan(Map<String, Object> out,
			CreditFile creditFile) {
		do {
			Integer i = creditFileService.createByUser(creditFile,
					creditFile.getCid());
			if (i > 0) {
				out.put("result", 1);
			} else {
				out.put("result", 2);
			}
		} while (false);
		return new ModelAndView("forward:dangan.htm");
	}

	@RequestMapping
	public ModelAndView doDeleteFile(Map<String, Object> out, Integer id) {
		do {
			if (id == null) {
				break;
			}
			Integer i = creditFileService.deleteById(id);
			if (i > 0) {
				out.put("result", 3);
			} else {
				out.put("result", 4);
			}
		} while (false);
		return new ModelAndView("forward:dangan.htm");
	}

	@RequestMapping
	public void jieshao(HttpServletRequest request, Map<String, Object> out) {
		Company company = companyService.queryById(getSessionUser(request)
				.getCompanyId());
		out.put("company", company);
	}

	@RequestMapping
	public void lianjie(HttpServletRequest request, Map<String, Object> out,
			EsiteFriendlinkSearchDto searchDto) {
		Integer cid = getSessionUser(request).getCompanyId();
		searchDto.setCid(cid);
		List<EsiteFriendlink> list = esiteFriendlinkService
				.queryFriendlink(searchDto);
		out.put("list", list);
		out.put("cid", cid);
	}

	@RequestMapping
	public void yuming(HttpServletRequest request, Map<String, Object> out) {
		Company company = companyService.queryById(getSessionUser(request)
				.getCompanyId());
		out.put("company", company);
	}

	@RequestMapping
	public ModelAndView doEditIntrodu(Map<String, Object> out, Company company) {
		doUpdate(out, company);
		return new ModelAndView("forward:jieshao.htm");
	}

	@RequestMapping
	public ModelAndView doEditDomain(Map<String, Object> out, Company company) {
		// 域名是否存在，存在即是修改过，仅修改一次
		if (StringUtils.isEmpty(companyService.queryById(company.getId())
				.getDomain())) {
			doUpdate(out, company);
		}
		return new ModelAndView("forward:yuming.htm");
	}

	@RequestMapping
	public ModelAndView createFriendLink(Map<String, Object> out,
			EsiteFriendlink esiteFriendlink) {
		do {
			Integer i = esiteFriendlinkService
					.createFriendlink(esiteFriendlink);
			if (i > 0) {
				out.put("result", 1);
			} else {
				out.put("result", 2);
			}
		} while (false);
		return new ModelAndView("forward:lianjie.htm");
	}

	@RequestMapping
	public ModelAndView doDeleteById(Map<String, Object> out, Integer id) {
		do {
			Integer i = esiteFriendlinkService.deleteById(id);
			if (i > 0) {
				out.put("result", 3);
			} else {
				out.put("result", 4);
			}
		} while (false);
		return new ModelAndView("forward:lianjie.htm");
	}

	private void doUpdate(Map<String, Object> out, Company company) {
		do {
			if (company.getId() == null) {
				out.put("result", 2);
				break;
			}
			Integer i = companyService.editCompany(company);
			if (i > 0) {
				// 修改成功
				out.put("result", 1);
			} else {
				out.put("result", 2);
			}
		} while (false);
	}

}
