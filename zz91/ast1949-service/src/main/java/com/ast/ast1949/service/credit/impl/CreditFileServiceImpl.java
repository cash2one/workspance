package com.ast.ast1949.service.credit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditFileDTO;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.credit.CreditFileDao;
import com.ast.ast1949.persist.credit.CreditIntegralDetailsDao;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.util.Assert;

@Component("creditFileService")
public class CreditFileServiceImpl implements CreditFileService {

	final static String INTEGRAL_KEY = "credit_file";
	final static Integer OPERATION_INTEGRAL = 1;
	
	@Autowired
	CreditFileDao creditFileDao;
	@Autowired
	CreditIntegralDetailsDao creditIntegralDetailsDao;
	@Resource
	private CompanyDAO companyDAO;

	@Override
	public Integer deleteFileById(Integer id, Integer companyId) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		creditIntegralDetailsDao.deleteIntegralByOperation(INTEGRAL_KEY, id,
				companyId);
		Integer i = creditFileDao.deleteFileById(id);
		// TODO 同时删除已经上传了的文件
		return i;
	}

	@Override
	public Integer insertFileByCompany(CreditFileDo creditFile) {
		Assert.notNull(creditFile, "the object creditFile can not be null");
		creditFile.setCheckStatus("0");
		return creditFileDao.insertFileByCompany(creditFile);
	}

	@Override
	public List<CreditFileDo> queryFileByCompany(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return creditFileDao.queryFileByCompany(companyId);
	}

	@Override
	public Integer updateCheckStatusByAdmin(Integer id, String checkStatus,
			String checkPerson,Integer companyId) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(checkStatus, "the checkStatus must not be null");
		Assert.notNull(companyId, "the companyId must not be null");
		
		Integer im = creditFileDao.updateCreditFileCheckStatusById(id, checkStatus, checkPerson);
		//积分操作
		if(im.intValue()>0) {
			creditIntegralDetailsDao.deleteIntegralByOperation(INTEGRAL_KEY, id, companyId);
			CreditIntegralDetailsDo detail = new CreditIntegralDetailsDo();
			detail.setCompanyId(companyId);
			detail.setOperationKey(INTEGRAL_KEY);
			detail.setRelatedId(id);
			if("1".equals(checkPerson)){
				detail.setIntegral(OPERATION_INTEGRAL);
				creditIntegralDetailsDao.insertIntegral(detail);
			}
		}
		return im;
	}

	@Override
	public Integer updateFileById(CreditFileDo creditFile) {
		Assert.notNull(creditFile, "the object creditFile can not be null");
		Assert.notNull(creditFile.getId(), "the creditFile.id can not be null");
		//TODO 更新时先删除原上传的图片
		creditFile.setCheckStatus("0");
		return creditFileDao.updateFileById(creditFile);
	}

	@Override
	public CreditFileDo queryFileById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return creditFileDao.queryFileById(id);
	}


	@Override
	public PageDto<CreditFileDTO> pageCriditFileByCondition(CreditFileDTO dto,
			PageDto<CreditFileDTO> page) {
		List<CreditFileDTO> list = creditFileDao.queryCreditFileByConditions(dto, page);
		for (CreditFileDTO obj : list) {
			if (obj==null||obj.getCreditFile()==null||obj.getCreditFile().getCompanyId()==null) {
				continue;
			}
			Company company = companyDAO.queryCompanyById(obj.getCreditFile().getCompanyId());
			if (company==null) {
				continue;
			}
			obj.setMembershipCode(company.getMembershipCode());
		}
		page.setRecords(list);
		page.setTotalRecords(creditFileDao.countCreditFileByConditions(dto));
		return page;
	}
	@Override
	public Integer updateFileByAdmin(CreditFileDo creditFile) {
		Assert.notNull(creditFile, "the object creditFile can not be null");
		Assert.notNull(creditFile.getId(), "the creditFile.id can not be null");
		return creditFileDao.updateFileByAdmin(creditFile);
	}

	@Override
	public String selectpicNameByCompanyId(CreditFileDo creditFile) {
	
		return  creditFileDao.selectpicNameByCompanyId(creditFile);
	}

}
