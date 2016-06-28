package com.ast.ast1949.service.credit.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.domain.credit.CreditReferenceDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditReferenceDTO;
import com.ast.ast1949.persist.credit.CreditIntegralDetailsDao;
import com.ast.ast1949.persist.credit.CreditReferenceDao;
import com.ast.ast1949.service.credit.CreditReferenceService;
import com.ast.ast1949.util.Assert;

@Component("creditReferenceService")
public class CreditReferenceServiceImpl implements CreditReferenceService {

	@Autowired
	CreditReferenceDao creditReferenceDao;
	@Autowired
	CreditIntegralDetailsDao creditIntegralDetailsDao;

	final static String INTEGRAL_KEY = "credit_reference";
	final static Integer OPERATION_INTEGRAL =1;
	final static String CHECK_STATUS_TRUE="1";

	@Override
	public Integer countReferenceByCompany(Integer companyId,
			Boolean checkStatus) {
		Assert.notNull(companyId, "the companyId can not be null");
		String status = null;
		if (checkStatus != null) {
			if (checkStatus) {
				status = "1";
			} else {
				status = "0";
			}
		}
		Integer reference=creditReferenceDao.countReferenceByCompany(companyId, status);
		if(reference!=null){
			return reference;
		}
		return 0;
	}

	@Override
	public Integer deleteReferenceByCompany(Integer id, Integer companyId) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		creditIntegralDetailsDao.deleteIntegralByOperation(INTEGRAL_KEY, id,
				companyId);
		return creditReferenceDao.deleteReferenceByCompany(id, companyId);
	}

	@Override
	public Integer insertReferenceByCompany(CreditReferenceDo reference) {
		Assert.notNull(reference, "the reference can not be null");
		
		Integer num = creditReferenceDao.countReferenceByCompany(reference.getCompanyId(), null);
		if(num!=null && num.intValue()<10){
			return creditReferenceDao.insertReferenceByCompany(reference);
		}
		return 0; 
	}

	@Override
	public List<CreditReferenceDo> queryReferenceByCompany(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return creditReferenceDao.queryReferenceByCompany(companyId);
	}

	@Override
	public Integer updateCheckStatusByAdmin(Integer id, String status,
			String person, Integer companyId) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(status, "the status must not be null");
		Assert.notNull(companyId, "the companyId must not be null");
		
		Integer im = updateReferenceCheckStatusById(id, status, person);
		if(im.intValue()>0){
			//TODO:积分操作
			creditIntegralDetailsDao.deleteIntegralByOperation(INTEGRAL_KEY, id, companyId);
			CreditIntegralDetailsDo detail = new CreditIntegralDetailsDo();
			detail.setCompanyId(companyId);
			detail.setOperationKey(INTEGRAL_KEY);
			detail.setRelatedId(id);
			if(CHECK_STATUS_TRUE.equals(status)) {
				detail.setIntegral(OPERATION_INTEGRAL);
			} else {
				detail.setIntegral(0);
			}
			creditIntegralDetailsDao.insertIntegral(detail);
		}
		
		return im;
	}

	@Override
	public Integer updateReferenceByCompany(CreditReferenceDo reference) {
		Assert.notNull(reference, "the reference can not be null");
		reference.setCheckStatus("0");
		creditIntegralDetailsDao.deleteIntegralByOperation(INTEGRAL_KEY,
				reference.getId(), reference.getCompanyId());
		return creditReferenceDao.updateReferenceByCompany(reference);
	}

	@Override
	public CreditReferenceDo queryReferenceById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return creditReferenceDao.queryReferenceById(id);
	}

//	@Override
//	public Integer countReferenceByConditions(CreditReferenceDTO dto) {
//		return creditReferenceDao.countReferenceByConditions(dto);
//	}

//	@Override
//	public List<CreditReferenceDTO> queryReferenceByConditions(CreditReferenceDTO dto) {
//		return creditReferenceDao.queryReferenceByConditions(dto);
//	}

	@Override
	public Integer deleteReferenceById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return creditReferenceDao.deleteReferenceById(id);
	}

	@Override
	public Integer updateReferenceCheckStatusById(Integer id, String checkStatus, String checkPerson) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(checkStatus, "the checkStatus must not be null");
		
		return creditReferenceDao.updateReferenceCheckStatusById(id, checkStatus, checkPerson);
	}

	@Override
	public PageDto<CreditReferenceDTO> pageReferenceByConditions(
			CreditReferenceDTO dto, PageDto<CreditReferenceDTO> page) {
		page.setRecords(creditReferenceDao.queryReferenceByConditions(dto, page));
		page.setTotalRecords( creditReferenceDao.countReferenceByConditions(dto));
		return page;
	}

	// @Autowired
	// private CreditReferenceDao creditCompanyDao;
	// @Autowired
	// private CompanyDAO companyDAO;
	//	
	// public Boolean deleteCreditCompany(String ids) {
	// Assert.notNull(ids, "The ids cannt not be null");
	// String[] str = ids.split(",");
	// Integer[] i = new Integer[str.length];
	// for(int m=0; m<str.length; m++){
	// i[m] = Integer.valueOf(str[m]);
	// }
	// if(creditCompanyDao.deleteCreditCompany(i)>0){
	// return true;
	// }
	// return false;
	// }
	//
	// public List<CreditReferenceDo> selectCreditCompany(PageDto pageDto,
	// CreditReferenceDo creditCompany) {
	// if(pageDto.getSort()==null){
	// pageDto.setSort("a.id");
	// }
	// return creditCompanyDao.selectCreditCompany(
	// pageDto, creditCompany);
	// }
	//
	// public Integer selectCreditCompanyCount(PageDto pageDto,
	// CreditReferenceDo creditCompany) {
	// if(pageDto.getSort()==null){
	// pageDto.setSort("a.id");
	// }
	// return creditCompanyDao.selectCreditCompanyCount(
	// pageDto, creditCompany);
	// }
	//
	// public List<CreditReferenceDo> selectCreditCompanyByCompanyId(
	// Integer companyId) {
	// Assert.notNull(companyId, "The companyId can not be null");
	// return creditCompanyDao.selectCreditCompanyByCompanyId(companyId);
	// }
	//
	// public CreditReferenceDo selectCreditCompanyById(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return creditCompanyDao.selectCreditCompanyById(id);
	// }
	//
	// public Integer updateCreditCompany(CreditReferenceDo creditCompany) {
	// Assert.notNull(creditCompany, "The creditCompany can not be null");
	// return creditCompanyDao.updateCreditCompany(creditCompany);
	// }
	//
	// public Integer insertCreditCompany(CreditReferenceDo creditCompany) {
	// Assert.notNull(creditCompany, "The creditCompany can not be null");
	// CompanyDO company =
	// companyDAO.selectcompanyById(creditCompany.getCompanyId());
	// creditCompany.setCompanyName(company.getName());
	// List<CreditReferenceDo> list =
	// creditCompanyDao.selectCreditCompanyByCompanyId(creditCompany.getCompanyId());
	// if(list.size()<10){
	// return creditCompanyDao.insertCreditCompany(creditCompany);
	// }
	// return 0;
	// }

}
