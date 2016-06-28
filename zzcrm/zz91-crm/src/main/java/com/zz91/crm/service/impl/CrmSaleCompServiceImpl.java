package com.zz91.crm.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.zookeeper.server.quorum.Election;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmLogDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.domain.CrmContactStatistics;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.CrmStatistics;
import com.zz91.crm.domain.CrmTurnStarStatistics;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmContactStatisticsDto;
import com.zz91.crm.dto.CrmSaleDataDto;
import com.zz91.crm.dto.CrmSaleStatisticsDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDataDto;
import com.zz91.crm.exception.LogicException;
import com.zz91.crm.service.CrmCompanyService;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
@Component("crmSaleCompService")
public class CrmSaleCompServiceImpl implements CrmSaleCompService {
	
	@Resource
	private CrmSaleCompDao crmSaleCompDao;
	@Resource
	private CrmLogDao crmLogDao;
	@Resource
	private CrmCompanyDao crmCompanyDao;

	/**
	 * ctype=3 为高会库,不允许更改高会库为个人库
	 */
	@Override
	@Transactional
	public Integer createCrmSaleComp(CrmSaleComp crmSaleComp,Short ctype) throws LogicException{
		Integer count1 = crmSaleCompDao.createCrmSaleComp(crmSaleComp);
		if (ctype!=null && ctype!=3){
			Integer count2 = crmCompanyDao.updateCtypeById(crmSaleComp.getCid(), CrmCompanyDao.CTYPE_SELF);
			Integer count3 = crmCompanyDao.updateSaleCompIdById(crmSaleComp.getCid(), count1);
			if (count1 == 0 || count2 == 0 || count3==0) {
				throw new LogicException("没有同时插入和更新!");
			}
		}
		return count1;
	}
	
	@Override
	public boolean isExsitCrmSale(Integer cid) {
		Integer count = crmSaleCompDao.queryCountByCidAndStatus(cid,CrmSaleCompService.STATUS_ABLE);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	@Transactional
	public Integer reSetCrmSaleComp(CrmSaleComp crmSaleComp) throws LogicException{
		Assert.notNull(crmSaleComp.getId(), "id is not null");
		Integer count1=0;
		if (crmSaleComp.getCid()==null){
			count1=1;
		}else {
			count1 = crmSaleCompDao.updateStatus(crmSaleComp.getCid(), STATUS_DISABLE);
		}
		Integer count2 = crmSaleCompDao.createCrmSaleComp(crmSaleComp);
		Integer count3 = crmCompanyDao.updateSaleCompIdById(crmSaleComp.getCid(), count1);
		if (count1 == 0 || count2 == 0 || count3==0) {
			throw new LogicException("数据同步出错,分配失败!");
		}
		return count1;
	}

	@Override
	@Transactional
	public Integer updateStatus(Integer cid, Short type) throws LogicException{
		Integer count1=crmSaleCompDao.updateStatus(cid, type);
		Integer count2=crmCompanyDao.updateCtypeById(cid, CrmCompanyDao.CTYPE_PUBLIC);
		if (count1==0 || count2==0){
			throw new LogicException("没有同时更新!");
		}
		return count1;
	}

	@Override
	public Integer updateCompanyType(Integer id, Short type) {
		return crmSaleCompDao.updateCompanyType(id, type);
	}

	@Override
	public Integer updateDisableStatus(Integer id) {
		return crmSaleCompDao.updateDisableStatus(id, DISABLE_STATUS_CHECK);
	}

	@Override
	@Transactional
	public Integer checkDisableStatus(Integer cid, Short flag) {
		Integer result = 0;
		if (flag == 1) {
			result = crmCompanyDao.updateCtypeById(cid, CrmCompanyDao.CTYPE_DISABLE);
			Integer count = crmSaleCompDao.updateStatus(cid, null);
			if (result == 0 || count==0) {
				throw new LogicException("没有同时更新!");
			}
		} else {
			result = crmSaleCompDao.updateDisableStatus(cid, DISABLE_STATUS_UNCHECK);
		}
		return result;
	}

	@Override
	public List<CrmSaleComp> queryCrmSaleCompByCid(Integer cid) {
		return crmSaleCompDao.queryCrmSaleCompByCid(cid);
	}

	@Override
	public List<CrmContactStatisticsDto> queryContactData(String account,
			String deptCode, String start, String end, Short group) {
		Assert.notNull(deptCode, "deptCode is not null");
		List<CrmContactStatisticsDto> list =crmSaleCompDao.queryContactData(account,deptCode,start,end,group);
		for (CrmContactStatisticsDto dto : list) {
			//有效总计
			dto.setAbleTotal(
			 (double)(dto.getStatistics().getStar1Able()
			+dto.getStatistics().getStar2Able()+dto.getStatistics().getStar3Able()
			+dto.getStatistics().getStar4Able()+dto.getStatistics().getStar5Able()));
			//无效总计
			dto.setDisableTotal(
			 (double)(dto.getStatistics().getStar1Disable()
			+dto.getStatistics().getStar2Disable()+dto.getStatistics().getStar3Disable()
			+dto.getStatistics().getStar4Disable()+dto.getStatistics().getStar5Disable()));
			//有效联系率
			Double ableTotal=dto.getAbleTotal();
			Double disableTotal=dto.getDisableTotal();
			double a=(ableTotal/(ableTotal+disableTotal));
			DecimalFormat df = new DecimalFormat("##.00");
			a = Double.parseDouble(df.format(a));
			dto.setAbleRate(a);
			if (group != null && group == 1) {
				if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end)) {
					dto.setDateStartEnd("全部");
				} else if (StringUtils.isEmpty(start) && StringUtils.isNotEmpty(end) ) {
					dto.setDateStartEnd("..."+end);
				}else if (StringUtils.isNotEmpty(start) && StringUtils.isEmpty(end) ) {
					dto.setDateStartEnd(start+"...");
				} else {
					dto.setDateStartEnd(start+"-"+end);
				}
			} else {
				dto.setDateStartEnd(DateUtil.toString(dto.getStatistics().getGmtTarget(), "yyyy-MM-dd"));
			}
		}
		return list;
	}

	@Override
	public List<CrmSaleStatisticsDto> queryRegisterData(String start, String end,
			Short group) {
		List<CrmSaleStatisticsDto> list=crmSaleCompDao.queryRegisterData(start,end,group);
		for (CrmSaleStatisticsDto dto : list) {
			if (group != null && group == 1) {
				if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end)) {
					dto.setDateStartEnd("全部");
				} else if (StringUtils.isEmpty(start) && StringUtils.isNotEmpty(end) ) {
					dto.setDateStartEnd("..."+end);
				}else if (StringUtils.isNotEmpty(start) && StringUtils.isEmpty(end) ) {
					dto.setDateStartEnd(start+"...");
				} else {
					dto.setDateStartEnd(start+"-"+end);
				}
			} else {
				dto.setDateStartEnd(DateUtil.toString(dto.getCrmSaleStatistics().getGmtTarget(), "yyyy-MM-dd"));
			}
			dto.setTotals(dto.getCrmSaleStatistics().getBeijing()+dto.getCrmSaleStatistics().getZhejiang()
					+dto.getCrmSaleStatistics().getGuangdong()+dto.getCrmSaleStatistics().getHebei()
					+dto.getCrmSaleStatistics().getJiangsu()+dto.getCrmSaleStatistics().getOther()
					+dto.getCrmSaleStatistics().getShandong()+dto.getCrmSaleStatistics().getShanghai());
		}
		return list;
	}

	@Override
	public List<SaleCompanyDataDto> querySaleCompanyData(String account,
			String deptCode) {
		Assert.notNull(deptCode, "deptCode is not null");
		List<SaleCompanyDataDto> list = new ArrayList<SaleCompanyDataDto>();
		List<CrmSaleDataDto> crmSaleDataDto = crmSaleCompDao.querySales(account, deptCode);
		for (CrmSaleDataDto saleDto:crmSaleDataDto) {
			SaleCompanyDataDto sale = new SaleCompanyDataDto();
			sale.setSaleAccount(saleDto.getSaleAccount());
			sale.setSaleName(saleDto.getSaleName());
			sale.setSaleDept(saleDto.getSaleDept());
			List<CrmSaleDataDto> counts = crmSaleCompDao.querySaleCompanyData(sale.getSaleAccount(),sale.getSaleDept());
			for (CrmSaleDataDto count:counts) {
				if (count.getStar()==1) {
					sale.setStart1(count.getCount());
				} else if(count.getStar()==2) {
					sale.setStart2(count.getCount());
				} else if(count.getStar()==3) {
					sale.setStart3(count.getCount());
				} else if(count.getStar()==4) {
					sale.setStart4(count.getCount());
				} else if(count.getStar()==5) {
					sale.setStart5(count.getCount());
				} else {
					sale.setUncontact(count.getCount());
				}
			}
			
			CompanySearchDto search=new CompanySearchDto();
			search.setSaleAccount(saleDto.getSaleAccount());
			
			if (sale.getNewUnContact()==null){
				publicProperty(search);
				search.setContactStatus((short)1);
				search.setContactFlag((short)0);
				search.setContactCount(0);
				search.setBlockCount((short)0);
				
				sale.setNewUnContact(crmCompanyDao.queryMyCompanyCount(search));
			}
			if (sale.getSeaUnContact()==null){
				publicProperty(search);
				search.setContactStatus((short)1);
				search.setContactFlag((short)0);
				search.setContactCount(0);
				search.setBlockCount((short)5);//这里5用于比较真实值,并非是真实值
				
				sale.setSeaUnContact(crmCompanyDao.queryMyCompanyCount(search));
			}
			if (sale.getLostComp()==null){
				publicProperty(search);
				search.setContactStatus(null);
				search.setContactFlag(null);
				search.setContactCount(null);
				search.setBlockCount(null);
				search.setGmtNextContactStart(null);
				search.setGmtNextContactEnd(DateUtil.toString(new Date(), "yyyy-MM-dd"));
				
				sale.setLostComp(crmCompanyDao.queryMyCompanyCount(search));
			}
			if (sale.getDragDestroy()==null){
				publicProperty(search);
				search.setContactStatus(null);
				search.setContactFlag(null);
				search.setContactCount(null);
				search.setBlockCount(null);
				search.setGmtNextContactEnd(null);
				search.setDragDestryStatus(CrmSaleCompService.DRAG_DESTORY_THREE);
				
				sale.setDragDestroy(crmCompanyDao.queryMyCompanyCount(search));
			}
			if (sale.getTotalsComp()==null){
				publicProperty(search);
				search.setDragDestryStatus(null);
				sale.setTotalsComp(crmCompanyDao.queryMyCompanyCount(search));
			}
			sale.setTomContact(crmSaleCompDao.queryTomContact(sale.getSaleAccount(),sale.getSaleDept()));
			sale.setTodContact(crmSaleCompDao.queryTodContact(sale.getSaleAccount(),sale.getSaleDept()));
			list.add(sale);
		}
		return list;
	}
	
	public void publicProperty(CompanySearchDto search){
		search.setRegistStatus(CrmCompanyService.REGIST_STATUS_CHECK);
		search.setSaleType(CrmSaleCompDao.SALE_TYPE1);
		search.setSaleDept("");
		search.setStatus(CrmCompanyDao.STATUS_ABLE);
		search.setRepeatId(0);
	}
	
	@Override
	public CrmContactStatisticsDto queryMyContactDataByToday(String account, String deptCode) {
		List<CrmLog> list=crmSaleCompDao.queryContactDataByToday(account,deptCode);
		String saleAccount = "";
		String saleName = "";
		String saleDept = "";
		int star1_able = 0;
		int star1_disable = 0;
		int star2_able = 0;
		int star2_disable = 0;
		int star3_able = 0;
		int star3_disable = 0;
		int star4_able = 0;
		int star4_disable = 0;
		int star5_able = 0;
		int star5_disable = 0;
		int drag_count = 0;
		int destroy_count = 0;
		int seo_count=0;
		int add_renew_count=0;
		for (CrmLog cl : list) {
			if(StringUtils.isNotEmpty(cl.getSaleAccount())){
				saleAccount=cl.getSaleAccount();
			}
			if(StringUtils.isNotEmpty(cl.getSaleDept())){
				saleDept=cl.getSaleDept();
			}
			if(StringUtils.isNotEmpty(cl.getSaleName())){
				saleName=cl.getSaleName();
			}
			if (cl.getStar()==1) {
				if (cl.getSituation()==0) {
					star1_able++;
				} else {
					star1_disable++;
				}
			} else if(cl.getStar()==2) {
				if (cl.getSituation()==0) {
					star2_able++;
				} else {
					star2_disable++;
				}
			} else if(cl.getStar()==3) {
				if (cl.getSituation()==0) {
					star3_able++;
				} else {
					star3_disable++;
				}
			} else if(cl.getStar()==4) {
				if (cl.getSituation()==0) {
					star4_able++;
				} else {
					star4_disable++;
				}
			} else if(cl.getStar()==5) {
				if (cl.getSituation()==0) {
					star5_able++;
				} else {
					star5_disable++;
				}
			}
			if (cl.getStarOld()==5) {
				if (cl.getStar()==5) {
					drag_count++;
				} else {
					destroy_count++;
				}
			}
			if (cl.getContactType()==2){
				seo_count++;
			}
			if (cl.getContactType()==3){
				add_renew_count++;
			}
		}
		CrmContactStatisticsDto dto=new CrmContactStatisticsDto();
		CrmContactStatistics bean = new CrmContactStatistics();
		bean.setStar1Able(star1_able);
		bean.setStar1Disable(star1_disable);
		bean.setStar2Able(star2_able);
		bean.setStar2Disable(star2_disable);
		bean.setStar3Able(star3_able);
		bean.setStar3Disable(star3_disable);
		bean.setStar4Able(star4_able);
		bean.setStar4Disable(star4_disable);
		bean.setStar5Able(star5_able);
		bean.setStar5Disable(star5_disable);
		bean.setSaleAccount(saleAccount);
		bean.setSaleName(saleName);
		bean.setSaleDept(saleDept);
		bean.setSeoCount(seo_count);
		bean.setAddRenewCount(add_renew_count);
		dto.setDateStartEnd(DateUtil.toString(new Date(), "yyyy-MM-dd"));
		dto.setStatistics(bean);
		//有效总计
		dto.setAbleTotal(
		 (double)(dto.getStatistics().getStar1Able()
		+dto.getStatistics().getStar2Able()+dto.getStatistics().getStar3Able()
		+dto.getStatistics().getStar4Able()+dto.getStatistics().getStar5Able()));
		//无效总计
		dto.setDisableTotal(
		 (double)(dto.getStatistics().getStar1Disable()
		+dto.getStatistics().getStar2Disable()+dto.getStatistics().getStar3Disable()
		+dto.getStatistics().getStar4Disable()+dto.getStatistics().getStar5Disable()));
		//有效联系率
		Double ableTotal=dto.getAbleTotal();
		Double disableTotal=dto.getDisableTotal();
		double a= 0;
		if ((ableTotal+disableTotal)>0){
			 a=(ableTotal/(ableTotal+disableTotal));
		}
		DecimalFormat df = new DecimalFormat("##.00");
		a = Double.parseDouble(df.format(a));
		dto.setAbleRate(a);
		dto.setTotal(dto.getAbleTotal()+dto.getDisableTotal());
		dto.setTomContactCount(crmLogDao.querytomContactCount(account));
		return dto;
	}

	@Override
	public List<CrmContactStatisticsDto> queryDeptContactDataByToday(
			String deptCode) {
		List<CrmContactStatisticsDto> list=new ArrayList<CrmContactStatisticsDto>();
		List<CrmLog> log=crmLogDao.queryAccountByDeptCode(deptCode);
		
		//查询今天做小记的人
		for (CrmLog crmLog : log) {
			String account = crmLog.getSaleAccount();
			if(StringUtils.isNotEmpty(account)){
				CrmContactStatisticsDto dto = queryMyContactDataByToday(account,deptCode);
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public PageDto<CrmStatistics> pageCrmStatistics(
			PageDto<CrmStatistics> page) {
		if (page.getLimit()==null){
			page.setLimit(CrmCompanyService.DEFAULT_LIMIT);
		}
		if (page.getSort()==null){
			page.setSort("gmt_target");
		}
		if (page.getDir()==null){
			page.setDir("desc");
		}
		
		List<CrmStatistics> list=crmSaleCompDao.queryCrmStatistics(page);
		List<CrmSaleDataDto> list2=crmSaleCompDao.queryCrmCount();
		int totals = 0;
		int selfCount = 0;
		int seaCount = 0;
		int wasteCount = 0;
		int noActiveCount = 0;
		for (CrmSaleDataDto dto : list2) {
			totals = totals + dto.getCount();
			if(dto.getCtype()==1){
				selfCount=dto.getCount();
			}else if (dto.getCtype()==2) {
				seaCount=dto.getCount();
			}else if (dto.getCtype()==4) {
				wasteCount=dto.getCount();
			}else if (dto.getCtype()==5) {
				noActiveCount=dto.getCount();
			}
		}
		CrmStatistics statistics=new CrmStatistics();
		statistics.setGmtTarget(new Date());
		statistics.setTotals(totals);
		statistics.setSeaCount(seaCount);
		statistics.setNoActiveCount(noActiveCount);
		statistics.setSelfCount(selfCount);
		statistics.setWasteCount(wasteCount);
		statistics.setRepeatCount(crmSaleCompDao.queryTodayRepeatCount());
		statistics.setTodayAssignCount(crmSaleCompDao.queryTodayAssignCount());
		statistics.setTodayChooseCount(crmSaleCompDao.queryTodayChooseSeaCount());
		statistics.setTodayPutCount(crmSaleCompDao.queryTodaySeaCount());
		statistics.setGmtCreated(null);
		list.add(0, statistics);
		
		page.setRecords(list);
		page.setTotals(crmSaleCompDao.queryCrmStatisticsCount());
		return page;
	}

	@Override
	public List<Date> queryGmtTarget() {
		return crmSaleCompDao.queryGmtTarget();
	}

	@Override
	public Integer updateStatusByCid(Integer cid) {
		return crmSaleCompDao.updateStatus(cid, null);
	}

	@Override
	public PageDto<CrmTurnStarStatistics> pageFourOrFiveStar(String start,
			String end, PageDto<CrmTurnStarStatistics> page) {
		
		List<CrmTurnStarStatistics> list=crmSaleCompDao.queryFourOrFiveStar(start,end,page);
		List<CrmLog> crmLogList = crmLogDao.queryTurnFourOrFiveAccountByToday();
		CrmTurnStarStatistics statistics =null;
		List<CrmLog> crmLogs= null;
		Integer index=-1;
		for (CrmLog crmLog : crmLogList) {
			statistics = new  CrmTurnStarStatistics();
			 crmLogs=crmLogDao.queryFourOrFiveBySaleAccountToday(crmLog.getSaleAccount());
			 int star4 = 0;
			 int star5 = 0;
			 for (CrmLog cl : crmLogs) {
				statistics.setId(cl.getId());
				statistics.setSaleAccount(cl.getSaleAccount());
				statistics.setSaleName(cl.getSaleName());
				statistics.setSaleDept(cl.getSaleDept());
				if(cl.getStarOld()!= 4 && cl.getStarOld()!=5 && cl.getStar()==4) {
					star4++;
				}
				if(cl.getStarOld()!=5 && cl.getStar()==5) {
					star5++;
				}
				statistics.setStar4(star4);
				statistics.setStar5(star5);
			}
			
			statistics.setGmtTarget(new Date());
			index++;
			list.add(index,statistics);
		}
		
		page.setRecords(list);
		page.setTotals(crmSaleCompDao.queryFourOrFiveStarCount(start,end));
		return page;
	}

	@Override
	public CrmSaleDataDto querySaleNameAndSaleDept(String account) {
		Assert.notNull(account, "the account is not be null");
		return crmSaleCompDao.querySaleNameAndSaleDeptByAccount(account);
	}

	@Override
	public Integer updateSaleDept(String account, String oldDept, String newDept) {
		Assert.notNull(account, "the account is not be null");
		Assert.notNull(oldDept, "the oldDept is not be null");
		Assert.notNull(newDept, "the newDept is not be null");
		return crmSaleCompDao.updateSaleDeptByAccountAndDept(account,oldDept,newDept);
	}
}
