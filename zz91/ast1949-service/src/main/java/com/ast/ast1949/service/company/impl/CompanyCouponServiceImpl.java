/**
 * @author kongsj
 * @date 2014年11月4日
 * 
 */
package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyCoupon;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCouponDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyCouponDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.service.company.CompanyCouponService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("companyCouponService")
public class CompanyCouponServiceImpl implements CompanyCouponService{
	@Resource
	private CompanyDAO companyDAO;

	@Resource
	private CompanyCouponDao companyCouponDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Override
	public Integer createOneCoupon(Integer companyId,Integer serviceId) {
		do {
			if (serviceId==null||companyId==null) {
				break;
			}
			CompanyCoupon companyCoupon = new CompanyCoupon();
			Integer type = null;
			if (serviceId==5) {
				companyCoupon = companyCouponDao.selectByCode(companyId, null, 2);
				type=2;
			}else{
				companyCoupon = companyCouponDao.selectByCode(companyId, null, 1);
				type=1;
			}
			if (companyCoupon!=null) {
				break;
			}
			companyCoupon = new CompanyCoupon();
			companyCoupon.setCompanyId(companyId);
			companyCoupon.setServiceName(COUPON_NAME_MAP.get(serviceId));
			companyCoupon.setReducePrice(REDUCE_MAP.get(serviceId));
			try {
				companyCoupon.setGmtEnd(DateUtil.getDate("2014-11-12", "yyyy-MM-dd"));
			} catch (ParseException e) {
				companyCoupon.setGmtEnd(new Date());
			}
			try {
				companyCoupon.setGmtStart(DateUtil.getDate("2014-11-11", "yyyy-MM-dd"));
			} catch (ParseException e) {
				companyCoupon.setGmtStart(new Date());
			}
			companyCoupon.setCode(UUID.randomUUID().toString());;
			companyCoupon.setType(type);
			companyCoupon.setStatus(STATUS_OPEN);
			
			return companyCouponDao.insert(companyCoupon);
		} while (false);
		return 0;
	}

	@Override
	public Integer closeOneCoupon(Integer companyId, Integer id) {
		return null;
	}

	@Override
	public List<CompanyCoupon> queryForMyrc(Integer companyId) {
		if (companyId==null) {
			return null;
		}
		return 	companyCouponDao.selectByCompanyId(companyId);
	}

	@Override
	public Integer validateCouponHaveOrNot(Integer companyId) {
		if (companyId==null) {
			return 3;
		}
		Integer i = 0;
		CompanyCoupon memberCoupon = companyCouponDao.selectByCode(companyId, null, 1);
		if (memberCoupon==null) {
			i = i + 1 ;
		}
		
		CompanyCoupon adCoupon = companyCouponDao.selectByCode(companyId, null, 2);
		if (adCoupon==null) {
			i = i + 2 ;
		}
		return i;
	}

	@Override
	public PageDto<CompanyCouponDto> queryCompanyCoupon(String email,PageDto<CompanyCouponDto> page) {
		page.setSort("id");
		page.setDir("desc");
		List<CompanyCouponDto> list=companyCouponDao.queryCompanyCoupon(email,page);
		if(list.size()>0){
			for(CompanyCouponDto companyCouponDto :list){
				if(companyCouponDto!=null&&companyCouponDto.getCompanyCoupon()!=null
						&&companyCouponDto.getCompanyCoupon().getCompanyId()!=null){
					String companyName=companyDAO.queryCompanyNameById(companyCouponDto.getCompanyCoupon().getCompanyId());
					companyCouponDto.setCompanyName(companyName);
					CompanyAccount companyAccount=companyAccountDao.queryAccountByCompanyId(companyCouponDto.getCompanyCoupon().getCompanyId());
					if(companyAccount!=null){
						companyCouponDto.setMobile(companyAccount.getMobile());
						companyCouponDto.setEmail(companyAccount.getEmail());
					}
				}
			}
		}
		page.setRecords(list);
		page.setTotalRecords(companyCouponDao.queryCompanyCouponCount(email));
		return page;
	}

	@Override
	public CompanyCoupon queryCompanyCouponById(Integer id) {
		return companyCouponDao.selectById(id);
	}

	@Override
	public Integer updateCompanyCoupon(CompanyCoupon companyCoupon) {
		Integer i=0;
		if(companyCoupon!=null&&companyCoupon.getId()!=null){
			i=companyCouponDao.updateCouponInfo(companyCoupon);
		}
		return i;
	}

	@Override
	public CompanyCoupon yzCoupon(Integer codeId,Integer companyId) {
		CompanyCoupon companyCoupon =new CompanyCoupon();
		if(codeId!=null&&companyId!=null){
			 companyCoupon=companyCouponDao.selectByServiceName(companyId, COUPON_NAME_MAP.get(codeId));
		}
		return companyCoupon;
	}

	@Override
	public CompanyCoupon yzCouponCode(Integer companyId, Integer codeId,String code) {
		CompanyCoupon companyCoupon =new CompanyCoupon();
		if(codeId!=null&&companyId!=null&&StringUtils.isNotEmpty(code)){
			companyCoupon=companyCouponDao.queryByNameCode(companyId, COUPON_NAME_MAP.get(codeId), code);
		}
		return companyCoupon;
	}

	@Override
	public List<CompanyCoupon> queryCouponByCompanyId(Integer companyId) {
		List<CompanyCoupon> list=new ArrayList<CompanyCoupon>();
		if(companyId!=null){
			list=companyCouponDao.selectByCompanyId(companyId);
		}
		return list;
	}

	@Override
	public Integer createCompanyCouponByStatus(Integer companyId,Integer serviceId) {
	do{
		if (serviceId==null||companyId==null) {
			break;
		}
		CompanyCoupon companyCoupon = new CompanyCoupon();
		Integer type = null;
		if (serviceId==5) {
			companyCoupon = companyCouponDao.selectByCode(companyId, null, 2);
			type=2;
		}else{
			companyCoupon = companyCouponDao.selectByCode(companyId, null, 1);
			type=1;
		}
		if (companyCoupon!=null) {
			break;
		}
		companyCoupon = new CompanyCoupon();
		companyCoupon.setCompanyId(companyId);
		companyCoupon.setServiceName(COUPON_NAME_MAP.get(serviceId));
		companyCoupon.setReducePrice(REDUCE_MAP.get(serviceId));
		try {
			companyCoupon.setGmtEnd(DateUtil.getDate("2014-11-12", "yyyy-MM-dd"));
		} catch (ParseException e) {
			companyCoupon.setGmtEnd(new Date());
		}
		try {
			companyCoupon.setGmtStart(DateUtil.getDate("2014-11-11", "yyyy-MM-dd"));
		} catch (ParseException e) {
			companyCoupon.setGmtStart(new Date());
		}
		companyCoupon.setCode(UUID.randomUUID().toString());;
		companyCoupon.setType(type);
		companyCoupon.setStatus(4);
		
		return companyCouponDao.insert(companyCoupon);
	} while (false);
		return 0;
		
	}
	
	@Override
	public Integer createOneCoupon(CompanyCoupon companyCoupon) {
		if (companyCoupon==null) {
			return 0;
		}
		if (companyCoupon.getCompanyId()==null) {
			return 0;
		}
		List<CompanyCoupon> list = companyCouponDao.selectByCompanyId(companyCoupon.getCompanyId(),null, FROM, TO);
		if (companyCoupon.getCode().indexOf("DJ")!=-1) {
			for (CompanyCoupon obj : list) {
				if (obj.getCode().indexOf("DJ")!=-1) {
					companyCoupon.setId(obj.getId());
					companyCouponDao.updateCouponInfo(companyCoupon);
					return 0;
				}
			}
		}else{
			for (CompanyCoupon obj : list) {
				if (obj.getCode().indexOf("YH")!=-1) {
					return 0;
				}
			}
		}
		
		return companyCouponDao.insert(companyCoupon);
	}

	@Override
	public Integer openCoupon(Integer id) {
		if (id==null) {
			return 0;
		}
		CompanyCoupon obj = companyCouponDao.selectById(id);
		if (obj==null) {
			return 0;
		}
		obj.setStatus(STATUS_OPEN);
		Integer i = companyCouponDao.updateStatus(obj);
		return i;
	}

	@Override
	public CompanyCoupon getCouponByCode(Integer companyId, String code) {
		if (StringUtils.isEmpty(code)||companyId==null) {
			return null;
		}
		CompanyCoupon obj = companyCouponDao.getCouponByCode(companyId, code);
		return obj;
	}

}
