package com.ast.ast1949.service.trust.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustBuyLog;
import com.ast.ast1949.domain.trust.TrustCompanyLog;
import com.ast.ast1949.domain.trust.TrustCrm;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCrmDto;
import com.ast.ast1949.dto.trust.TrustCrmSearchDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CrmCsDao;
import com.ast.ast1949.persist.trust.TrustBuyDao;
import com.ast.ast1949.persist.trust.TrustBuyLogDao;
import com.ast.ast1949.persist.trust.TrustCompanyLogDao;
import com.ast.ast1949.persist.trust.TrustCrmDao;
import com.ast.ast1949.persist.trust.TrustSellDao;
import com.ast.ast1949.service.trust.TrustCrmService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("trustCrmService")
public class TrustCrmServiceImpl implements TrustCrmService {

	@Resource
	private TrustCrmDao trustCrmDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private TrustBuyDao trustBuyDao;
	@Resource
	private TrustSellDao trustSellDao;
	@Resource
	private TrustBuyLogDao trustBuyLogDao;
	@Resource
	private CrmCsDao crmCsDao;
	@Resource
	private TrustCompanyLogDao trustCompanyLogDao;
	
	@Override
	public Integer create(Integer companyId, String crmAccount) {
		if (companyId ==0||companyId ==null||StringUtils.isEmpty(crmAccount)) {
			return 0;
		}
		TrustCrm obj =  trustCrmDao.queryByCompanyId(companyId);
		if (obj!=null) {
			return 0;
		}
		TrustCrm trustCrm = new TrustCrm();
		trustCrm.setCompanyId(companyId);
		trustCrm.setCrmAccount(crmAccount);
		trustCrm.setIsPublic(NO_PUBLIC);
		trustCrm.setIsRubbish(NO_RUBBISH);
		return trustCrmDao.insert(trustCrm);
	}

	@Override
	public PageDto<TrustCrmDto> page(TrustCrmSearchDto searchDto, PageDto<TrustCrmDto> page) {
		// 默认状态
		if (searchDto.getIsPublic()==null&&searchDto.getIsRubbish()==null) {
			searchDto.setIsDeafult(1);
		}else{
			searchDto.setCrmAccount("");
		}
		
		page.setTotalRecords(trustCrmDao.queryCountByCondition(searchDto));
		List<TrustCrm> list = trustCrmDao.queryByCondition(searchDto, page);
		List<TrustCrmDto> nlist = new ArrayList<TrustCrmDto>(); 
		if (list==null) {
			list = new ArrayList<TrustCrm>();
		}
		for (TrustCrm obj:list) {
			TrustCrmDto dto = new TrustCrmDto();
			dto.setTrustCrm(obj);
			// 获取帐号信息
			CompanyAccount account = companyAccountDao.queryAccountByCompanyId(obj.getCompanyId());
			if (account!=null) {
				dto.setAccount(account);
			}else{
				dto.setAccount(new CompanyAccount());
			}
			// 获取本月联系次数
			dto.setNumVisitMonth(trustBuyLogDao.countByCompanyIdForOneMonth(obj.getCompanyId())+trustCompanyLogDao.countByCompanyIdForOneMonth(obj.getCompanyId()));;
			
			// 获取采购单数
			dto.setBuyNum(trustBuyDao.countByCompanyId(obj.getCompanyId()));
			// 获取供货单数
			dto.setSellNum(trustSellDao.countByCompanyId(obj.getCompanyId()));
			// 获取客服帐号
			CrmCs cc =  crmCsDao.queryCsOfCompany(obj.getCompanyId());
			if (cc!=null) {
				dto.setCsAccount(cc.getCsAccount());
			}else{
				dto.setCsAccount("暂无");
			}
			// 获取公司名
			Company c = companyDAO.queryCompanyById(obj.getCompanyId());
			if (c!=null&&StringUtils.isNotEmpty(c.getName())) {
				dto.setCompanyName(c.getName());
			}else{
				dto.setCompanyName("");
			}
			// 获取公司会员类型
			if (c!=null&&StringUtils.isNotEmpty(c.getMembershipCode())) {
				dto.setMembershipCode(c.getMembershipCode());
			}else{
				dto.setMembershipCode("");
			}

			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public Integer assignAccount(Integer companyId, String crmAccount) {
		if (companyId==null||StringUtils.isEmpty(crmAccount)) {
			return 0;
		}
		//获取公司信息
		TrustCrm obj = trustCrmDao.queryByCompanyId(companyId);
		if (obj==null) {
			return 0;
		}
		
		if (obj==null || obj.getIsRubbish()==null || obj.getIsRubbish().equals(1)) {
			return 0;
		}
		
		/*
		 * 1. "" 	可
		 * 2. null 	可
		 * 3. 自己	可
		 * 4. 非自己	不可
		 * */ 
		if (StringUtils.isNotEmpty(obj.getCrmAccount())&&!obj.getCrmAccount().equals(crmAccount)) {
			return 0;
		}
		
		Integer i = trustCrmDao.updateStatus(companyId, NO_PUBLIC, null); // 取消公海状态
		if (i>0) {
			return trustCrmDao.updateTrustAccount(companyId, crmAccount);
		}
		return 0;
	}
	
	@Override
	public Integer assignAccountByRight(Integer companyId, String crmAccount) {
		if (companyId==null||StringUtils.isEmpty(crmAccount)) {
			return 0;
		}
		//获取公司信息
		TrustCrm obj = trustCrmDao.queryByCompanyId(companyId);
		if (obj==null) {
			return 0;
		}
		
		if (obj==null || obj.getIsRubbish()==null || obj.getIsRubbish().equals(1)) {
			return 0;
		}
		
		Integer i = trustCrmDao.updateStatus(companyId, NO_PUBLIC, null); // 取消公海状态
		if (i>0) {
			return trustCrmDao.updateTrustAccount(companyId, crmAccount);
		}
		return 0;
	}

	@Override
	public Integer lost(Integer companyId) {
		// 丢公海
		Integer i = trustCrmDao.updateStatus(companyId, IS_PUBLIC, null);
		// 更新帐号为空 
		i = trustCrmDao.updateTrustAccount(companyId, "");
		return i;
	}

	@Override
	public Integer destory(Integer companyId) {
		// 丢公海
		Integer i = trustCrmDao.updateStatus(companyId, null, IS_RUBBISH);
		// 更新帐号为空 
		i = trustCrmDao.updateTrustAccount(companyId, "");
		return i;
	}

	@Override
	public Integer updateAfterContact(Integer companyId, Integer star, Date gmtNextVisit) {
		if (companyId==null) {
			return 0;
		}
		Integer i = trustCrmDao.updateStar(companyId, star);
		i = trustCrmDao.updateContact(companyId, gmtNextVisit);
		return i;
	}

	@Override
	public TrustCrm queryByCompanyId(Integer companyId) {
		if (companyId==null) {
			return null;
		}
		return trustCrmDao.queryByCompanyId(companyId);
	}

	@Override
	public Integer importToCrm(Integer companyId) {
		if (companyId ==null||companyId==0) {
			return 0;
		}
		TrustCrm obj =  trustCrmDao.queryByCompanyId(companyId);
		if (obj!=null) {
			return 0;
		}
		TrustCrm trustCrm = new TrustCrm();
		trustCrm.setCompanyId(companyId);
		trustCrm.setIsPublic(IS_PUBLIC);
		trustCrm.setIsRubbish(NO_RUBBISH);
		return trustCrmDao.insert(trustCrm);
	}

	@Override
	public PageDto<TrustCsLogDto> pageLog(TrustCsLogSearchDto searchDto, PageDto<TrustCsLogDto> page) {
		List<TrustCsLogDto> resultList = new ArrayList<TrustCsLogDto>();
		if ("C".equals(searchDto.getFlag())) {
			List<TrustCompanyLog> list = trustCompanyLogDao.queryByConditionByAdmin(searchDto, page);
			for (TrustCompanyLog obj : list) {
				TrustCsLogDto dto = new TrustCsLogDto();
				dto.setStar(obj.getStar()); // 星级
				dto.setContent(obj.getContent()); // 内容
				dto.setGmtVisit(obj.getGmtCreated()); // 联系日期
				dto.setTrustAccount(obj.getTrustAccount()); // 小计创建工作人员
				// 公司id为空，跳过
				if (obj.getCompanyId()==null||obj.getCompanyId()==0) {
					continue;
				}
				dto.setCompanyId(obj.getCompanyId());
				dto.setTargetId(obj.getCompanyId());
				Company c = companyDAO.queryCompanyById(dto.getCompanyId());
				// 公司不存在，跳过
				if (c==null) {
					continue;
				}
				dto.setCompanyName(c.getName()); // 设置公司名
				dto.setTargetName(c.getName());
				dto.setGmtLastLogin(getGmtLastLogin(obj.getCompanyId()));
				resultList.add(dto);
			}
			page.setTotalRecords(trustCompanyLogDao.queryCountByConditionByAdmin(searchDto)); // 总记录数
		}else{
			List<TrustBuyLog> list = trustBuyLogDao.queryByConditionByAdmin(searchDto, page);
			for (TrustBuyLog obj : list) {
				TrustCsLogDto dto = new TrustCsLogDto();
				dto.setStar(obj.getStar()); // 星级
				dto.setContent(obj.getContent()); // 内容
				dto.setGmtVisit(obj.getGmtCreated()); // 联系日期
				dto.setTrustAccount(obj.getTrustAccount()); // 小计创建工作人员
				// 根据 trustid 获取公司id
				if (obj.getBuyId()==null) {
					continue;
				}
				TrustBuy tb = trustBuyDao.queryById(obj.getBuyId());
				if (tb==null) {
					continue;
				}
				dto.setTargetId(tb.getId());
				dto.setCompanyId(trustBuyDao.queryById(obj.getBuyId()).getCompanyId());
				dto.setGmtLastLogin(getGmtLastLogin(dto.getCompanyId()));
				Company c = companyDAO.queryCompanyById(dto.getCompanyId());
				// 公司不存在，跳过
				if (c==null) {
					dto.setTargetName(tb.getCompanyName());
				}else{
					dto.setTargetName(c.getName());
				}
				resultList.add(dto);
			}
			page.setTotalRecords(trustBuyLogDao.queryCountByConditionByAdmin(searchDto)); // 总记录数
		}
		page.setRecords(resultList);
		return page;
	}
	
	private Date getGmtLastLogin(Integer companyId){
		if (companyId==null||companyId<=0) {
			return null;
		}
		CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(companyId);
		if (ca==null) {
			return null;
		}
		return ca.getGmtLastLogin();
	}
	
	@Override
	public List<Map<String, Object>> queryMonthLog(Integer year,Integer month,String account){
		if (year==null||year<0) {
			year = DateUtil.getYearForDate(new Date());
		}
		if (month == null||month<0||month>12) {
			month = DateUtil.getMonthForDate(new Date());
		}
		Date targetDate;
		try {
			targetDate = DateUtil.getDate(year+"-"+month+"-"+"01", "yyyy-MM-dd");
		} catch (ParseException e) {
			targetDate = new Date();
		}
		// 开始时间 结束时间
		String from  = DateUtil.toString(targetDate, "yyyy-MM-dd");
		String to = DateUtil.toString(DateUtil.getDateAfterMonths(targetDate, 1), "yyyy-MM-dd");
		
		// 结果list
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		// 首日为星期天，需要补相应个数的空数据，让日历对齐
		Integer i = DateUtil.getDayOfWeekForDate(targetDate);
		for (; i > 1; i--) {
			resultList.add(new HashMap<String, Object>());
		}
		TrustCsLogSearchDto searchDto = new TrustCsLogSearchDto();
		PageDto<TrustCsLogDto> page = new PageDto<TrustCsLogDto>();
		page.setPageSize(1000); // 设置获取 1000条 每个工作日 50个联系量
		searchDto.setFrom(from); //开始时间
		searchDto.setTo(to); //结束时间
		searchDto.setTrustAccount(account); //客服 account
		// 检索采购小计
		List<TrustBuyLog> buyList =  trustBuyLogDao.queryByConditionByAdmin(searchDto, page);
		Map<String, Integer[]> buyMap = new LinkedHashMap<String, Integer[]>();
		Set<String> reLogMap= new HashSet<String>();
		for (TrustBuyLog obj : buyList) {
			if (obj.getStar()==null||obj.getStar()<=0||obj.getStar()>5) {
				continue;
			}
			String key = DateUtil.toString(obj.getGmtCreated(), "M-d");
			Integer[] tempArray = buyMap.get(key);
			if (tempArray==null||tempArray.length<3) {
				tempArray = new Integer[]{0,0,0};
			}
			// 验证是否今天已经存在小计了，存在了，则小计次数不相加
			if(reLogMap.contains(key + "-" + obj.getBuyId()+"-"+obj.getOfferCompanyId())){
			}else{
				reLogMap.add(key + "-" + obj.getBuyId()+"-"+obj.getOfferCompanyId());
				tempArray[0]++;
			}
			
			if (obj.getStar()==4) {
				// 4星
				if(validateFirst(obj.getStar(),null,obj.getBuyId(),obj.getId())){
					// 没有四星 或者以上 属于第一次开发
					tempArray[1]++;
				}
			}else if(obj.getStar()==5){
				//五星
				if(validateFirst(obj.getStar(),null,obj.getBuyId(),obj.getId())){
					// 没有五星 或者以上 属于第一次开发
					tempArray[2]++;
				}
			}
			buyMap.put(key, tempArray);
		}
		// 检索公司小计
		List<TrustCompanyLog> companyList = trustCompanyLogDao.queryByConditionByAdmin(searchDto, page);
		Map<String, Integer[]> companyMap = new LinkedHashMap<String, Integer[]>();
		reLogMap= new HashSet<String>();
		for (TrustCompanyLog obj : companyList) {
			if (obj.getStar()==null||obj.getStar()<=0||obj.getStar()>5) {
				continue;
			}
			String key = DateUtil.toString(obj.getGmtCreated(), "M-d");
			Integer[] tempArray = companyMap.get(key);
			if (tempArray==null||tempArray.length<3) {
				tempArray = new Integer[]{0,0,0};
			}
			// 验证公司小计是否重复
			if(reLogMap.contains(key + "-" + obj.getCompanyId())){
			}else{
				reLogMap.add(key + "-" + obj.getCompanyId());
				tempArray[0]++;
			}
			
			if (obj.getStar()==4) {
				// 4星
				if(validateFirst(obj.getStar(),obj.getCompanyId(),null,obj.getId())){
					// 没有四星 或者以上 属于第一次开发
					tempArray[1]++;
				}
			}else if(obj.getStar()==5){
				//五星
				if(validateFirst(obj.getStar(),obj.getCompanyId(),null,obj.getId())){
					// 没有五星 或者以上 属于第一次开发
					tempArray[2]++;
				}
			}
			companyMap.put(key, tempArray);
		}

		// 循环一个月的时间。把所有数据完善
		for (int j = 1; j <= 31; j++) {
			String key = month+"-"+j;
			Integer [] resultArray = new Integer[]{0,0,0}; // 0 今日总数；1 第一次4星数；第一次5星
			Integer [] buyArray = buyMap.get(key);
			if (buyArray==null) {
				buyArray = new Integer[]{0,0,0};
			}
			Integer [] companyArray = companyMap.get(key);
			if (companyArray==null) {
				companyArray = new Integer[]{0,0,0};
			}
			resultArray[0] = buyArray[0] + companyArray[0]; //所有联系量
			resultArray[1] = buyArray[1] + companyArray[1]; // 4星开发
			resultArray[2] = buyArray[2] + companyArray[2]; // 5星开发
			Map<String, Object> addMap = new HashMap<String, Object>();
			addMap.put("total", resultArray[0]);
			addMap.put("4star", resultArray[1]);
			addMap.put("5star", resultArray[2]);
			resultList.add(addMap);
		}
		
		return resultList;
	}
	
	/**
	 * 判定是否为 一个高星开发 
	 * @param star
	 * @param companyId
	 * @param buyId
	 * @return
	 */
	private boolean validateFirst(Integer star,Integer companyId,Integer buyId,Integer id){
		if (star==null) {
			return false;
		}
		Integer i = 0;
		if (buyId!=null) {
			i = trustBuyLogDao.queryRecentStar(buyId,id);
		}
		if (i==null||(i!=null&&i!=0&&i<star)) {
			return true;
		}
		if (companyId!=null) {
			i = trustCompanyLogDao.queryRecentStar(companyId, id);
		}
		if (i==null||(i!=null&&i!=0&&i<star)) {
			return true;
		}
		return false;
	}
	
//	public static void main(String[] args) throws ParseException {
////		Date date = DateUtil.getDate(new Date(), "yyyy-MM");
//		Date date = DateUtil.getDate("2015-10-01", "yyyy-MM-dd");
//		System.out.println(DateUtil.getDayOfWeekForDate(date));
//		Integer i = DateUtil.getDayOfWeekForDate(date);
//		List<Integer[]> resultList = new ArrayList<Integer[]>();
//		for (; i > 1; i--) {
//			resultList.add(new Integer[]{});
//		}
//		for (Integer[] a:resultList) {
//			System.out.println(a.length);
//		}
//	}

}
