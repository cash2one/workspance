package com.ast.ast1949.myrc.controller.credit;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.param.ParamUtils;

/**
 * @author zhouzk
 * 
 *         created on 2013-1-2
 */
@Controller
public class CreditBaseInfoController extends BaseController{
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyAttestService companyAttestService;
    @Resource
    private CreditFileService creditFileService;
    @Resource
    private CrmCsService crmCsService;
    @Resource
	private MyrcService myrcService;
    @RequestMapping
    public ModelAndView index(HttpServletRequest request,
            Map<String, Object> out ,String flag) { 
        SsoUser ssoUser = getCachedUser(request);
        // 查询是否开通商铺服务
     	myrcService.initMyrc(out, ssoUser.getCompanyId());
        CompanyAttest companyAttest = new CompanyAttest();
        companyAttest.setCompanyId(ssoUser.getCompanyId());
        //认证信息未认证数据填充
        if (companyAttestService.queryOneInfo(companyAttest) == null) {
            CompanyDto compDto = companyService.queryCompanyWithAccountById(ssoUser.getCompanyId());
            compDto.setIndustryLabel(CategoryFacade.getInstance().getValue(compDto.getCompany().getIndustryCode()));
            compDto.setServiceLabel(CategoryFacade.getInstance().getValue(compDto.getCompany().getServiceCode()));
            out.put("pageFlg", 0);
            out.put("compDto", compDto);
        } else {
            companyAttest = companyAttestService.queryOneInfo(companyAttest);
            if ("0".equals(companyAttest.getAttestType())) {
                String[] str = companyAttest.getTel().split("-");
                //将电话号码拆分成“国家区号”，“城市区号”,"电话"
                if (str.length >= 1) {
                    companyAttest.setTelNum(str[str.length-1]);
                }
                if (str.length >= 2) {
                    companyAttest.setTelAreaCode(str[str.length-2]);
                }
                if(str.length >= 3) {
                    companyAttest.setTelCountryCode(str[str.length-3]);
                }
            }
            out.put("companyAttest", companyAttest);
            if ("1".equals(flag)) {
                return new ModelAndView("redirect:indexQualification.htm");
            }
        }
        return null;
    }
    
    @RequestMapping
    public ModelAndView attestSubmit(HttpServletRequest request, Map<String, Object> out,CompanyAttest companyAttest , String attestType) throws ParseException {
        SsoUser sessionUser = getCachedUser(request);
        //图片链接处理
        if(StringUtils.isNotEmpty(companyAttest.getPicAddress())){
        	companyAttest.setPicAddress(companyAttest.getPicAddress().replace(",",""));
        }
        companyAttest.setCompanyId(sessionUser.getCompanyId());
        companyAttest.setAttestType(attestType);
      //负责该客户的cs电话
        CrmCs cs=crmCsService.queryCsOfCompany(sessionUser.getCompanyId());
		if(cs!=null && StringUtils.isNotEmpty(cs.getCsAccount())) {
			
			String csinfo=ParamUtils.getInstance().getValue("cs_info", cs.getCsAccount());
			if(csinfo!=null){
				String[] info=csinfo.split(",");
				if(info.length>=2){
					out.put("cs_phone", info[1]);
				}
			}
		}
        if ("0".equals(companyAttest.getAttestType())) {
        	//组装电话号码
        	String telCode = "";
        	if (companyAttest.getTelCountryCode() != null) {
        		telCode += companyAttest.getTelCountryCode();
        	}
        	if (companyAttest.getTelAreaCode() != null) {
        		if (StringUtils.isEmpty(telCode)) {
        			telCode += companyAttest.getTelAreaCode();
        		} else {
        			telCode += "-" + companyAttest.getTelAreaCode();
        		}
        	}
        	if (companyAttest.getTelNum() != null) {
        		if (StringUtils.isEmpty(telCode)) {
        			telCode += companyAttest.getTelNum();
        		} else {
        			telCode += "-" + companyAttest.getTelNum();
        		}
        	}
        	companyAttest.setTel(telCode);
        } else {
        	 if (companyAttest.getEstablishTimeStr() != null) {
             	companyAttest.setEstablishTime(DateUtil.getDate(companyAttest.getEstablishTimeStr(), "yyyy-MM-dd"));
             }
             if (companyAttest.getStartTimeStr() != null) {
             	companyAttest.setStartTime(DateUtil.getDate(companyAttest.getStartTimeStr(), "yyyy-MM-dd"));
             }
             if (companyAttest.getEndTimeStr() != null) {
             	 companyAttest.setEndTime(DateUtil.getDate(companyAttest.getEndTimeStr(), "yyyy-MM-dd"));
             }
            if (companyAttest.getInspectionTimeStr() != null) {
         	   companyAttest.setInspectionTime(DateUtil.getDate(companyAttest.getInspectionTimeStr(), "yyyy-MM-dd"));
            }
        }
        //判断客户是否已发布认证信息
        CompanyAttest attest = companyAttestService.queryAttestByCid(sessionUser.getCompanyId());
        if (attest != null) {
        	companyAttest.setId(attest.getId());
        	//更新后，审核状态变为未审核
        	companyAttest.setCheckStatus("0");
        	Integer j = companyAttestService.updateInfoByFront(companyAttest);
        	 if (j != null && j.intValue()>0) {
                 return null;
             } else {
                 return new ModelAndView("redirect:index.htm");
             }
        } else {
        	 Integer i = companyAttestService.addOneInfo(companyAttest);
        	 if (i != null && i.intValue()>0) {
                 return null;
             } else {
                 return new ModelAndView("redirect:index.htm");
             }
        }
    }

    @RequestMapping
    public ModelAndView indexQualification(HttpServletRequest request,
            Map<String, Object> out) {
        out.put(FrontConst.MYRC_SUBTITLE, "企业自传资质");
        SsoUser sessionUser = getCachedUser(request);
        CompanyAttest companyAttest = new CompanyAttest();
        companyAttest.setCompanyId(sessionUser.getCompanyId());
        companyAttest = companyAttestService.queryOneInfo(companyAttest);
        //认证信息状态
        if (companyAttest != null) {
            out.put("checkStatus", companyAttest.getCheckStatus());
        }
        List<CreditFileDo> credit = creditFileService.queryFileByCompany(sessionUser.getCompanyId());
        if (credit.size() <= 0) {
            out.put("emptyFlg", "0");
        }
        out.put("fileList", credit);

        //获取二级域名，用于从生意管家进入门市部
        Company company = companyService.queryDomainOfCompany(sessionUser.getCompanyId());
        if (company != null) {
        	out.put("domainZZ91", company.getDomainZz91());
        }
        //诚信积分
        /*out.put("integral", creditIntegralDetailsService
                .countIntegralByOperationKey(sessionUser.getCompanyId(),
                        FILE_OPERATION_KEY));*/

        return null;
    }
    
    @RequestMapping
    public ModelAndView createQualification(HttpServletRequest request,
            Map<String, Object> out, String code) {
        SsoUser sessionUser = getCachedUser(request);
        CompanyAttest companyAttest = new CompanyAttest();
        companyAttest.setCompanyId(sessionUser.getCompanyId());
        companyAttest = companyAttestService.queryOneInfo(companyAttest);
        //认证信息状态
        if (companyAttest != null) {
            out.put("checkStatus", companyAttest.getCheckStatus());
        }
        return null;
    }
    
    @RequestMapping
    public ModelAndView insertQualification(HttpServletRequest request,
            Map<String, Object> out, CreditFileDo file, String startTimeStr,
            String endTimeStr) throws IOException {
        ExtResult result = new ExtResult();
        SsoUser sessionUser = getCachedUser(request);
        CompanyAttest companyAttest = new CompanyAttest();
        companyAttest.setCompanyId(sessionUser.getCompanyId());
        companyAttest = companyAttestService.queryOneInfo(companyAttest);
        //认证信息状态
        if (companyAttest != null) {
            out.put("checkStatus", companyAttest.getCheckStatus());
        }
        //负责该客户的cs电话
        CrmCs cs=crmCsService.queryCsOfCompany(sessionUser.getCompanyId());
		if(cs!=null && StringUtils.isNotEmpty(cs.getCsAccount())) {
			
			String csinfo=ParamUtils.getInstance().getValue("cs_info", cs.getCsAccount());
			if(csinfo!=null){
				String[] info=csinfo.split(",");
				if(info.length>=2){
					out.put("cs_phone", info[1]);
				}
			}
		}
        file.setCompanyId(sessionUser.getCompanyId());
        file.setAccount(sessionUser.getAccount());
        try {
            file.setStartTime(DateUtil.getDate(startTimeStr,
                    AstConst.DATE_FORMATE_DEFAULT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            file.setEndTime(DateUtil.getDate(endTimeStr,
                    AstConst.DATE_FORMATE_DEFAULT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        file.setCategoryCode("10041000");
        Integer i = creditFileService.insertFileByCompany(file);
        if (i > 0) {
            result.setSuccess(true);
            return null;
        }
        return printJson(result, out);
    }
    
    @RequestMapping
    public ModelAndView editQualification(HttpServletRequest request,
            Map<String, Object> out, Integer id) {
        SsoUser sessionUser = getCachedUser(request);
        out.put(FrontConst.MYRC_SUBTITLE, "修改 企业自传资质");
        CompanyAttest companyAttest = new CompanyAttest();
        companyAttest.setCompanyId(sessionUser.getCompanyId());
        companyAttest = companyAttestService.queryOneInfo(companyAttest);
        //认证信息状态
        if (companyAttest != null) {
            out.put("checkStatus", companyAttest.getCheckStatus());
        }
        out.put("creditFile", creditFileService.queryFileById(id));
        return null;
    }

    @RequestMapping
    public ModelAndView updateQualification(HttpServletRequest request,
            Map<String, Object> out, CreditFileDo file, String startTimeStr,
            String endTimeStr) throws IOException {
        ExtResult result = new ExtResult();
        file.setCompanyId(getCachedUser(request).getCompanyId());
        try {
            file.setStartTime(DateUtil.getDate(startTimeStr,
                    AstConst.DATE_FORMATE_DEFAULT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            file.setEndTime(DateUtil.getDate(endTimeStr,
                    AstConst.DATE_FORMATE_DEFAULT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //更新后，审核状态变为未审核
        file.setCheckStatus("0");
        Integer i = creditFileService.updateFileById(file);
        if (i > 0) {
            result.setSuccess(true);
            return new ModelAndView("redirect:indexQualification.htm");
        }
        return printJson(result, out);
    }

    @RequestMapping
    public ModelAndView deleteQualification(HttpServletRequest request,
            Map<String, Object> out, Integer id) {
        creditFileService.deleteFileById(id, getCachedUser(request).getCompanyId());
        return new ModelAndView(new RedirectView("indexQualification.htm"));
    }
}
