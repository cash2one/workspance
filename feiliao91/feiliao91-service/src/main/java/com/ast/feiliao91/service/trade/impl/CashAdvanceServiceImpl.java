package com.ast.feiliao91.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.domain.trade.CashAdvanceSearch;
import com.ast.feiliao91.domain.trade.TradeLog;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.CashAdvanceDto;
import com.ast.feiliao91.persist.company.CompanyAccountDao;
import com.ast.feiliao91.persist.trade.CashAdvanceDao;
import com.ast.feiliao91.persist.trade.TradeLogDao;
import com.ast.feiliao91.service.trade.CashAdvanceService;
import com.zz91.util.lang.StringUtils;

@Component("cashAdvanceeService")
@Transactional
public class CashAdvanceServiceImpl implements CashAdvanceService {

	@Resource
	private CashAdvanceDao cashAdvanceDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private TradeLogDao tradeLogDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Integer updateCashAdvanceTransactional(CashAdvance cashAdvance) {
		//spring事务配置
		if(cashAdvance == null){
			return null;
		}
		Integer i =  cashAdvanceDao.insertCashAdvance(cashAdvance);
		if (i>0) {
			CompanyAccount companyAccount = new CompanyAccount();
			companyAccount.setAccount("zhuzhuzhu");
//			companyAccount.setCompanyId(20);
			companyAccount.setMobile("15067114177");
			companyAccountDao.insert(companyAccount);
		}
		return i;
	}
	
	
	@Override
	public Integer insertCashAdvance(CashAdvance cashAdvance) {
		if(cashAdvance == null){
			return null;
		}
		return cashAdvanceDao.insertCashAdvance(cashAdvance);
	}
	@Override
	public List<CashAdvance> queryByCondition(CashAdvance cashAdvance){
		if(cashAdvance == null){
			return null;
		}
		return cashAdvanceDao.queryByCondition(cashAdvance);
	}
	@Override
	public CashAdvance queryCashAdvanceById(Integer id){
		if(id == null){
			return null;
		}
		return cashAdvanceDao.queryCashAdvanceById(id);
	}
	@Override
	public PageDto<CashAdvanceDto> pageBySearch(PageDto<CashAdvanceDto> page,CashAdvanceSearch search){
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_modified");
		}
		page.setDir("desc");
		List<CashAdvance> list = cashAdvanceDao.queryCashAdvanceList(page, search);
		List<CashAdvanceDto> resultList = new ArrayList<CashAdvanceDto>();
		for (CashAdvance cashAdvance : list) {
			CashAdvanceDto dto =new CashAdvanceDto();
			CompanyAccount account = companyAccountDao.queryByCompanyId(cashAdvance.getCompanyId());
			if(account == null){
				account = new CompanyAccount();
			}
			dto.setCompanyAccount(account);
			dto.setCashAdvance(cashAdvance);
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(cashAdvanceDao.countCashAdvanceList(search));
		return page;
	}
	@SuppressWarnings("unused")
	@Override
	public String updateStatus(String ids, Integer checkStatus,String checkPerson){
		if (checkStatus.equals(2)) {
			//当审核不通过，要向账户余额里退钱，并且插入流水小计
			StringBuffer sb = new StringBuffer();
			String[] str = ids.split(",");
			for (String s : str) {
				//根据id获得cashAdvance类
				CashAdvance cashAdvance = cashAdvanceDao.queryCashAdvanceById(Integer.valueOf(s));
				if(cashAdvance == null || cashAdvance.getCompanyId() == null){
					continue;
				}
				//根据company_id获得账户类
				CompanyAccount companyAccount = companyAccountDao.queryByCompanyId(cashAdvance.getCompanyId());
				if (companyAccount == null) {
					continue;
				}
				//判断余额的大小
				if(cashAdvance.getMoney() <= 0 || companyAccount.getSumMoney() <= 0 || (companyAccount.getSumMoney()-cashAdvance.getMoney()<0)){
					continue;
				}
				//改余额
				Integer j =companyAccountDao.updateSumMoney(cashAdvance.getCompanyId(),companyAccount.getSumMoney()+cashAdvance.getMoney());
				//添加流水小计
				Integer k = insertCashAdvanceReturn(cashAdvance.getCompanyId(),cashAdvance.getId(),cashAdvance.getMoney());
				//更新提现状态
				Integer i = cashAdvanceDao.updateStatus(Integer.valueOf(s),checkStatus, checkPerson);
				if (i == 1 && j == 1 && k>0) {
					sb.append(s);
				}
			}
			if (sb == null) {
				return null;
			}
			return sb.toString();
		}else {
			StringBuffer sb = new StringBuffer();
			String[] str = ids.split(",");
			for (String s : str) {
				//根据id获得cashAdvance类
				CashAdvance cashAdvance = cashAdvanceDao.queryCashAdvanceById(Integer.valueOf(s));
				if(cashAdvance == null || cashAdvance.getCompanyId() == null){
					continue;
				}
				//根据company_id获得账户类
				CompanyAccount companyAccount = companyAccountDao.queryByCompanyId(cashAdvance.getCompanyId());
				if (companyAccount == null) {
					continue;
				}
				//判断余额的大小
				if(cashAdvance.getMoney() <= 0 || companyAccount.getSumMoney() <= 0 || (companyAccount.getSumMoney()-cashAdvance.getMoney()<0)){
					continue;
				}
				//改余额
				Integer j =companyAccountDao.updateSumMoney(cashAdvance.getCompanyId(),companyAccount.getSumMoney()-cashAdvance.getMoney());
				//更新提现状态
				Integer i = cashAdvanceDao.updateStatus(Integer.valueOf(s),checkStatus, checkPerson);
				if (i == 1 && j == 1) {
					sb.append(s);
				}
			}
			if (sb == null) {
				return null;
			}
			return sb.toString();
		}
	}
	
	public Integer insertCashAdvanceReturn(Integer companyId,Integer cashAdvanceId,Float money) {
		TradeLog tradeLog = new TradeLog();
		tradeLog.setCompanyId(companyId);
		tradeLog.setMoney(money);
		tradeLog.setStatus(1); // 进帐
		JSONObject js = new JSONObject();
		js.put("cashAdvanceId", cashAdvanceId);
		tradeLog.setRemark(js.toString());
		tradeLog.setType(3);//设置流水的交易类型为3（提现流水）
		return tradeLogDao.insert(tradeLog);
	}
}
