package com.ast.ast1949.service.company.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAttestDto;
import com.ast.ast1949.persist.company.CompanyAttestDAO;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * 公司工商或个人注册信息
 * 
 * @author zhouzk
 * 
 */
@Component("companyAttestService")
public class CompanyAttestServiceImpl implements CompanyAttestService {

    @Resource
    private CompanyAttestDAO companyAttestDAO;
    @Resource
    private CrmCompanySvrService crmCompanySvrService;
    @Resource
    private CompanyService companyService;
    
    @Override
    public PageDto<CompanyAttestDto> pageCompanyAttest(
            CompanyAttest companyAttest, String compName,  PageDto<CompanyAttestDto> page) {
    	List<CompanyAttestDto> list = companyAttestDAO.queryCompanyAttest(companyAttest,compName, page);
    	for ( CompanyAttestDto obj : list ) {
    		if (obj==null||obj.getCompanyAttest()==null||obj.getCompanyAttest().getCompanyId()==null) {
				continue;
			}
    		Company company = companyService.queryCompanyById(obj.getCompanyAttest().getCompanyId());
    		if (company==null) {
				continue;
			}
    		obj.setMembershipCode(company.getMembershipCode());
		}
        page.setRecords(list);
        page.setTotalRecords(companyAttestDAO.queryCompanyAttestCount(companyAttest , compName));
        return page;
    }

    @Override
    public CompanyAttest queryOneInfo(CompanyAttest companyAttest) {
        companyAttest = companyAttestDAO.queryByCondition(companyAttest);
        if (companyAttest != null) {
            CompanyAttest attest = splitShow(companyAttest.getShowStatus());
            companyAttest.setShowIdNumber(attest.getShowIdNumber());
            companyAttest.setShowInspection(attest.getShowInspection());
            companyAttest.setShowOrg(attest.getShowOrg());
            companyAttest.setShowCapital(attest.getShowCapital());
            companyAttest.setShowLegal(attest.getShowLegal());
            companyAttest.setShowCode(attest.getShowCode());
        }
        return companyAttest;
    }
    
    @Override
    public CompanyAttest queryOneInfoForFeiliao91(Integer companyId) {
    	CompanyAttest companyAttest =new CompanyAttest();
    	companyAttest.setCompanyId(companyId);
        companyAttest = companyAttestDAO.queryByCondition(companyAttest);
        return companyAttest;
    }

    @Override
    public Integer addOneInfo(CompanyAttest companyAttest) {
        Assert.notNull(companyAttest, "CompanyAttest must not be null!");
        companyAttest.setShowStatus(buildShow(companyAttest));
        return companyAttestDAO.insertCompanyAttest(companyAttest);
    }
    @Override
    public CompanyAttest queryAttestByCid (Integer companyId) {
    	 Assert.notNull(companyId, "companyId must not be null!");
    	 return companyAttestDAO.queryAttestByCid(companyId);
    }
    @Override
    public CompanyAttest queryAttestById (Integer id) {
    	 Assert.notNull(id, "id must not be null!");
    	 CompanyAttest companyAttest = companyAttestDAO.queryAttestById(id);
         if (companyAttest != null) {
             CompanyAttest attest = splitShow(companyAttest.getShowStatus());
             companyAttest.setShowIdNumber(attest.getShowIdNumber());
             companyAttest.setShowInspection(attest.getShowInspection());
             companyAttest.setShowOrg(attest.getShowOrg());
             companyAttest.setShowCapital(attest.getShowCapital());
             companyAttest.setShowLegal(attest.getShowLegal());
             companyAttest.setShowCode(attest.getShowCode());
         }
    	 return companyAttest;
    }
    @Override
    public Integer updateInfoByFront(CompanyAttest companyAttest) {
        Assert.notNull(companyAttest, "CompanyAttest must not be null!");
        if (StringUtils.isNotEmpty(companyAttest.getCheckStatus()) && !"0".equals(companyAttest.getCheckStatus())) {
            companyAttest.setCheckStatus("0");
        }
        if (StringUtils.isNotEmpty(companyAttest.getCheckPerson())) {
            companyAttest.setCheckPerson("");
        }
        companyAttest.setShowStatus(buildShow(companyAttest));
        return companyAttestDAO.updateCompanyAttest(companyAttest);
    }

    @Override
    public Integer updateInfoByAdmin(CompanyAttest companyAttest) {
        Assert.notNull(companyAttest, "CompanyAttest must not be null!");
        companyAttest.setCheckTime(new Date());
        companyAttest.setShowStatus(buildShow(companyAttest));
        return  companyAttestDAO.updateCompanyAttest(companyAttest);
    }

    @Override
    public Integer updateCheckStatus (Integer id, String checkStatus, String checkPerson) {
    	Assert.notNull(id, "id must not be null!");
    	Assert.notNull(checkStatus, "checkStatus must not be null!");
    	Assert.notNull(checkPerson, "checkPerson must not be null!");
    	return companyAttestDAO.updateCheckStatusById(id, checkStatus, checkPerson);
    }
    @Override
    public Integer deleteCompanyAttest(Integer id) {
        Assert.notNull(id, "id must not be null!");
        return companyAttestDAO.deleteCompanyAttest(id);
    }

    /**
    * 0：表示不显示   1：表示显示     000000表示6个不同内容显示状态
    * 个位：表示注册号是否显示
    * 十位：表示法定代表人是否显示
    * 百位：表示注册资本是否显示
    * 千位：表示登记机关是否显示
    * 万位：表示年检时间是否显示
    * 十万位：表示身份证号码是否显示
    * 例如：000001   在门市部只显示注册号，其他都隐藏
    * 
    */
    @Override
    public String buildShow(CompanyAttest companyAttest) {
        StringBuffer sb = new StringBuffer();
        if ("1".equals(companyAttest.getAttestType())) {
            //因为工商时，身份证号码为null
            sb.append("0");
            sb.append(companyAttest.getShowInspection());
            sb.append(companyAttest.getShowOrg());
            sb.append(companyAttest.getShowCapital());
            sb.append(companyAttest.getShowLegal());
            sb.append(companyAttest.getShowCode());
        } else {
            sb.append(companyAttest.getShowIdNumber());
            //因为个人时，其他都为null
            sb.append("00000");
        }
        return sb.toString();
    }

    @Override
    public CompanyAttest splitShow(String showStatus) {
        CompanyAttest companyAttest = new CompanyAttest();
        companyAttest.setShowIdNumber(String.valueOf(showStatus.charAt(0)));
        companyAttest.setShowInspection(String.valueOf(showStatus.charAt(1)));
        companyAttest.setShowOrg(String.valueOf(showStatus.charAt(2)));
        companyAttest.setShowCapital(String.valueOf(showStatus.charAt(3)));
        companyAttest.setShowLegal(String.valueOf(showStatus.charAt(4)));
        companyAttest.setShowCode(String.valueOf(showStatus.charAt(5)));
        return companyAttest;
    }
    
    @Override
    public String replaceStar(String str) {
    	String tmpStr = "";
    	for (int i = 0 ; i < str.length() ; i++ ) {
    		tmpStr += "*";
    	}
    	return tmpStr;
    }

	@Override
	public boolean validatePassOrNot(Integer companyId) {
		do {
			
			if (companyId==null|| companyId <=0) {
				break;
			}
			
			// 高会 逻辑
			Company company = companyService.queryCompanyById(companyId);
			if (!"10051000".equals(company.getMembershipCode())) {
				return true;
			}
			
			// 百度优化 逻辑
			if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.BAIDU_CODE)){
				return true;
			}
			
			// 普会 逻辑
			CompanyAttest ca = queryAttestByCid(companyId);
			if (ca==null) {
				break;
			}
			if (!STATE_PASS.equals(ca.getCheckStatus())) {
				break;
			}
			return true;
			} while (false);
		return false;
	}

}
