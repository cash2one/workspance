package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.LdbLevel;
import com.ast.ast1949.persist.phone.LdbLevelDao;
import com.ast.ast1949.service.phone.LdbLevelService;
@Component("ldbLevelService")
public class LdbLevelServiceImpl implements LdbLevelService {
	@Resource
	private LdbLevelDao ldbLevelDao;

	@Override
	public void resetLevel(Integer companyId,double fee) {
		double allFee=0;
		//检索来电宝等级
		LdbLevel ldbLevel=ldbLevelDao.queryLdbLevelByCompanyId(companyId);
		if(ldbLevel!=null){
			allFee=ldbLevel.getPhoneCost()+fee;
			if(allFee>=Math.pow(2,(ldbLevel.getLevel()+1)*1000)){
				ldbLevel.setLevel(ldbLevel.getLevel()+1);
				ldbLevel.setPhoneCost(allFee);
				ldbLevelDao.updateLevelByCompanyId(ldbLevel);
			}else{
				ldbLevel.setPhoneCost(allFee);
				ldbLevelDao.updateLevelByCompanyId(ldbLevel);
			}
		}else{
			ldbLevel=new LdbLevel();
			ldbLevel.setCompanyId(companyId);
			ldbLevel.setPhoneCost(fee);
			ldbLevel.setLevel(1);
			ldbLevelDao.insertLdbLevel(ldbLevel);
		}
	}

	@Override
	public LdbLevel queryByCompanyId(Integer companyId) {
		if(companyId==null){
			return null;
		}
		LdbLevel lb = ldbLevelDao.queryLdbLevelByCompanyId(companyId);
		if (lb!=null) {
			return lb;
		}
		return null;
	}
}
