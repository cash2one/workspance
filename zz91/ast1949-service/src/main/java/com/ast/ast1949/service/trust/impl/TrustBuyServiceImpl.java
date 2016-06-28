/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.service.trust.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.trust.TrustBuyDao;
import com.ast.ast1949.persist.trust.TrustDealerDao;
import com.ast.ast1949.persist.trust.TrustRelateDealerDao;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("trustBuyService")
public class TrustBuyServiceImpl implements TrustBuyService{

	@Resource
	private TrustBuyDao trustBuyDao;
	@Resource
	private TrustRelateDealerDao trustRelateDealerDao;
	@Resource
	private TrustDealerDao trustDealerDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;
	
	@Override
	public Integer publishBuy(Integer companyId, String detail) {
		if (StringUtils.isEmpty(detail)) {
			return 0;
		}
		TrustBuy trustBuy = new TrustBuy();
		trustBuy.setCompanyId(companyId);
		trustBuy.setDetail(detail);
		// 创建流水号
		trustBuy.setBuyNo(createBuyNo());
		trustBuy.setStatus(STATUS_00);
		return trustBuyDao.insert(trustBuy);
	}
	
	@Override
	public Integer publishBuyWithoutLogin(String companyName,String companyContact,String mobile, String detail) {
		if (StringUtils.isEmpty(detail)) {
			return 0;
		}
		if (StringUtils.isEmpty(mobile)) {
			return 0;
		}
		TrustBuy trustBuy = new TrustBuy();
		trustBuy.setCompanyId(0);
		trustBuy.setDetail(detail);
		// 创建流水号
		trustBuy.setBuyNo(createBuyNo());
		trustBuy.setStatus(STATUS_00);
		// 写入未登陆填写的信息
		if (StringUtils.isEmpty(mobile)) {
			mobile = "";
		}
		trustBuy.setMobile(mobile.trim());
		trustBuy.setCompanyName(companyName);
		trustBuy.setCompanyContact(companyContact);
		return trustBuyDao.insert(trustBuy);
	}
	
	private String createBuyNo(){
		String buyNo =DateUtil.toString(new Date(), "yyMMdd");
		Integer mid=trustBuyDao.queryMaxId();
		if(mid==null){
			mid=0;
		}
		Integer maxId = mid + 1;
		return buyNo+maxId;
	}

	@Override
	public PageDto<TrustBuyDto> pageByCompamyId(TrustBuySearchDto searchDto,
			PageDto<TrustBuyDto> page) {
		page.setTotalRecords(trustBuyDao.queryCountByCondition(searchDto));
		List<TrustBuy> list = trustBuyDao.queryByCondition(searchDto, page);
		List<TrustBuyDto> nlist = new ArrayList<TrustBuyDto>();
		for (TrustBuy obj:list) {
			TrustBuyDto dto = new  TrustBuyDto();
			dto.setTrustBuy(obj);
			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public Integer editTrustByAdmin(TrustBuy trustBuy) {
		if (trustBuy.getId()==null||trustBuy.getId()==0) {
			return 0;
		}
		return trustBuyDao.update(trustBuy);
	}

	@Override
	public PageDto<TrustBuyDto> page(TrustBuySearchDto searchDto,PageDto<TrustBuyDto> page) {
		page.setTotalRecords(trustBuyDao.queryCountByCondition(searchDto));
		List<TrustBuy> list = trustBuyDao.queryByCondition(searchDto, page);
		List<TrustBuyDto> nlist = new ArrayList<TrustBuyDto>();
		for (TrustBuy obj:list) {
			TrustBuyDto dto = new TrustBuyDto();
			if(StringUtils.isNotEmpty(obj.getAreaCode())){
				dto.setArea(CategoryFacade.getInstance().getValue(obj.getAreaCode()));
			}
			// 前台数据判定如果没有单位添加单位， 如果有单位就不变
			if (searchDto!=null&&searchDto.getIsFront()!=null&&searchDto.getIsFront()) {
				String price = obj.getPrice();
				try {
					if (StringUtils.isNotEmpty(price)&&!StringUtils.isContainCNChar(price)) {
						obj.setPrice(price+"元/吨");
					}
				} catch (UnsupportedEncodingException e) {
				}
				String quantity = obj.getQuantity();
				try {
					if (StringUtils.isNotEmpty(quantity)&&!StringUtils.isContainCNChar(quantity)) {
						obj.setQuantity(quantity+"吨/月");
					}
				} catch (UnsupportedEncodingException e) {
				}
			}
			
			dto.setTrustBuy(obj);
			// 获取交易员id
			if(StringUtils.isNotEmpty(obj.getBuyNo())){
				Integer dealerId=trustRelateDealerDao.queryRelateDealerByBuyNo(obj.getBuyNo());
				//交易员详情
				TrustDealer dealer=trustDealerDao.queryById(dealerId);
				if(dealer!=null){
					dto.setTrustDealer(dealer);
				}else{
					dto.setTrustDealer(new TrustDealer());
				}
			}
			//获取公司名称
			Company company=companyDAO.queryCompanyById(obj.getCompanyId());
			if(company!=null){
				dto.setCompany(company);
				//获取帐号信息
				CompanyAccount account=companyAccountDao.queryAccountByCompanyId(obj.getCompanyId());
				if(account!=null){
					dto.setAccount(account);
				}
			}else{
//				{name:"company_name",mapping:"company.name"},
//				{name:"company_id",mapping:"company.id"},
//				{name:"contact",mapping:"account.contact"},
//				{name:"tel",mapping:"account.mobile"},
				company = new Company();
				CompanyAccount account = new CompanyAccount();
				company.setName(obj.getCompanyName());
				company.setId(0);
				account.setContact(obj.getCompanyContact());
				account.setMobile(obj.getMobile());
				dto.setCompany(company);
				dto.setAccount(account);
			}
			if(StringUtils.isNotEmpty(dto.getTrustBuy().getCode())){
				dto.setCategoryName(CategoryProductsFacade.getInstance().getValue(dto.getTrustBuy().getCode()));
			}
			//获取发布状态
			dto.setIsPuase(obj.getIsPause());
			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public TrustBuyDto showTrustBuy(Integer id) {
		if (id==null) {
			return null;
		}
		TrustBuy trustBuy = trustBuyDao.queryById(id);
		TrustBuyDto dto = new  TrustBuyDto();
		dto.setTrustBuy(trustBuy);
		// 获取交易员信息
		return dto;
	}

	@Override
	public Integer updateStateByAdmin(Integer id, String status) {
		if (id==null||id<=0||StringUtils.isEmpty(status)) {
			return 0;
		}
		return trustBuyDao.updateStatusByAdmin(id, status);
	}

	@Override
	public Integer countByCompanyId(Integer companyId) {
		if (companyId==null||companyId<=0) {
			return 0;
		}
		TrustBuySearchDto trustBuySearchDto =new TrustBuySearchDto();
		trustBuySearchDto.setCompanyId(companyId);
		return trustBuyDao.queryCountByCondition(trustBuySearchDto);
	}

	@Override
	public Integer batchRefresh(String ids) {
		if(StringUtils.isEmpty(ids)){
			return 0;
		}
		Integer [] arrayIds = StringUtils.StringToIntegerArray(ids);
		return 	trustBuyDao.batchRefresh(arrayIds);
	}

	@Override
	public Integer createBuyByAdmin(TrustBuy trustBuy) {
		trustBuy.setCompanyId(0);
		trustBuy.setBuyNo(createBuyNo());
		if (StringUtils.isNotEmpty(trustBuy.getMobile())) {
			trustBuy.setMobile(trustBuy.getMobile().trim());
		}
		return trustBuyDao.insert(trustBuy);
	}
	
	@Override
	public Integer deleteById(Integer id){
		if (id==null||id==0) {
			return 0;
		}
		return trustBuyDao.updateIsDelByAdmin(id);
	}

	@Override
	public List<TrustBuy> queryTrustByCompanyId(Integer companyId) {
		if (companyId==null) {
			return null;
		}
		TrustBuySearchDto searchDto = new TrustBuySearchDto();
		searchDto.setCompanyId(companyId);
		return trustBuyDao.queryByCondition(searchDto, new PageDto<TrustBuyDto>());
	}

	@Override
	public TrustBuy queryTrustById(Integer id) {
		if (id==null) {
			return null;
		}
		return trustBuyDao.queryById(id);
	}

	@Override
	public Integer relateCompanyByMobile(Integer companyId, String mobile) {
		if (companyId==null||companyId==0) {
			return 0;
		}
		if (StringUtils.isEmpty(mobile)) {
			return 0;
		}
		// 手机号码， 必须与存在公司库的手机号码一致才可关联
		CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(companyId);
		if (ca==null||StringUtils.isEmpty(ca.getMobile())||!ca.getMobile().equals(mobile)) {
			return 0;
		}
		return trustBuyDao.relateCompanyByMobile(companyId,mobile);
	}

	@Override
	public Integer pauseBuy(String ids) {
		// 空，返回
		if (StringUtils.isEmpty(ids)) {
			return 0;
		}
		Integer[] result = null;
		// 判断是否为多个id，以 “,”分隔
		if (ids.indexOf(",")!=-1) {
			result = StringUtils.StringToIntegerArray(ids);
		}
		// 如果为单个id，则数组化，放入更新方法
		if (StringUtils.isNumber(ids)) {
			result = new Integer[]{Integer.valueOf(ids)};
		}
		return trustBuyDao.batchUpdatePauseById(result, IS_PAUSE);
	}

	@Override
	public Integer pubBuy(String ids) {
		// 空，返回
		if (StringUtils.isEmpty(ids)) {
			return 0;
		}
		Integer[] result = null;
		// 判断是否为多个id，以 “,”分隔
		if (ids.indexOf(",")!=-1) {
			result = StringUtils.StringToIntegerArray(ids);
		}
		// 如果为单个id，则数组化，放入更新方法
		if (StringUtils.isNumber(ids)) {
			result = new Integer[]{Integer.valueOf(ids)};
		}
		return trustBuyDao.batchUpdatePauseById(result, IS_PUB);
	}

	@Override
	public Integer batchUpdatePauseById(String ids,Integer status) {
		if(StringUtils.isEmpty(ids)){
			return 0;
		}
		Integer [] arrayIds = StringUtils.StringToIntegerArray(ids);
		return 	trustBuyDao.batchUpdatePauseById(arrayIds, status);
	}

	@Override
	public PageDto<TrustBuyDto> pageSimple(TrustBuySearchDto searchDto, PageDto<TrustBuyDto> page) {
		page.setTotalRecords(trustBuyDao.queryCountByCondition(searchDto));
		List<TrustBuy> list = trustBuyDao.queryByCondition(searchDto, page);
		List<TrustBuyDto> nlist = new ArrayList<TrustBuyDto>();
		for (TrustBuy obj:list) {
			TrustBuyDto dto = new TrustBuyDto();
			if(StringUtils.isNotEmpty(obj.getAreaCode())){
				dto.setArea(CategoryFacade.getInstance().getValue(obj.getAreaCode()));
			}
			// 前台数据判定如果没有单位添加单位， 如果有单位就不变
			if (searchDto!=null&&searchDto.getIsFront()!=null&&searchDto.getIsFront()) {
				String price = obj.getPrice();
				try {
					if (StringUtils.isNotEmpty(price)&&!StringUtils.isContainCNChar(price)) {
						obj.setPrice(price+"元/吨");
					}
				} catch (UnsupportedEncodingException e) {
				}
				String quantity = obj.getQuantity();
				try {
					if (StringUtils.isNotEmpty(quantity)&&!StringUtils.isContainCNChar(quantity)) {
						obj.setQuantity(quantity+"吨/月");
					}
				} catch (UnsupportedEncodingException e) {
				}
			}
			
			dto.setTrustBuy(obj);
			//	获取交易员id
			if(StringUtils.isNotEmpty(obj.getBuyNo())){
				Integer dealerId=trustRelateDealerDao.queryRelateDealerByBuyNo(obj.getBuyNo());
				//交易员详情
				TrustDealer dealer=trustDealerDao.queryById(dealerId);
				if(dealer!=null){
					dto.setTrustDealer(dealer);
				}else{
					dto.setTrustDealer(new TrustDealer());
				}
			}
			//获取公司名称
//			Company company=companyDAO.queryCompanyById(obj.getCompanyId());
//			if(company!=null){
//				dto.setCompany(company);
//				//获取帐号信息
//				CompanyAccount account=companyAccountDao.queryAccountByCompanyId(obj.getCompanyId());
//				if(account!=null){
//					dto.setAccount(account);
//				}
//			}else{
//				company = new Company();
//				CompanyAccount account = new CompanyAccount();
//				company.setName(obj.getCompanyName());
//				company.setId(0);
//				account.setContact(obj.getCompanyContact());
//				account.setMobile(obj.getMobile());
//				dto.setCompany(company);
//				dto.setAccount(account);
//			}
//			if(StringUtils.isNotEmpty(dto.getTrustBuy().getCode())){
//				dto.setCategoryName(CategoryProductsFacade.getInstance().getValue(dto.getTrustBuy().getCode()));
//			}
			//获取发布状态
//			dto.setIsPuase(obj.getIsPause());
			// 获取中文时间
			try {
				dto.setCnDate(DateUtil.getCNDate(obj.getGmtRefresh()));
			} catch (ParseException e) {
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}

	

}