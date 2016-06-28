package com.ast.ast1949.service.sample.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.ContactClickLog;
import com.ast.ast1949.domain.sample.WeixinLookcontactlog;
import com.ast.ast1949.paychannel.ChannelConst;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.sample.ContactClickLogDao;
import com.ast.ast1949.persist.sample.WeixinLookcontactlogDao;
import com.ast.ast1949.persist.sample.WeixinPrizelogDao;
import com.ast.ast1949.service.sample.ContactClickLogService;

@Component("contactClickLogService")
public class ContactClickLogServiceImpl implements ContactClickLogService {

	@Resource
	private ContactClickLogDao contactClickLogDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private WeixinPrizelogDao weixinPrizelogDao;
	@Resource
	private WeixinLookcontactlogDao weixinLookcontactlogDao;

	@Override
	public boolean scoreCvtViewContact(Integer companyId, String account, Integer targetComId) {
		if (companyId == null || account == null || targetComId == null) {
			return false;
		}
		//System.out.println("--------------------------companyId:"+companyId+"  account:"+account+"  targetComId:" +targetComId);
		boolean viewAble = false;
		try {
			ContactClickLog ccl = contactClickLogDao.queryById(companyId, targetComId);
			WeixinLookcontactlog wxl = weixinLookcontactlogDao.queryById(account, targetComId);
			if (ccl != null || wxl != null) {
				viewAble = true;
			} else {
				Integer countCvt = weixinPrizelogDao.totalCountConvertScoreByPrizeid(account, ChannelConst.PRIZEID_VIEW_CONTACT);
				Integer countUsed = contactClickLogDao.countClick(companyId); // 电脑端查看联系方式
				//Integer countWeixinUsed = weixinLookcontactlogDao.countClick(account); // 手机端查看联系方式个数
				//System.out.println("--------------------------countCvt:"+countCvt+"  countUsed:"+countUsed+"  countWeixinUsed:" +countWeixinUsed);
				//Integer countAble = countCvt - countUsed - countWeixinUsed;
				Integer countAble = countCvt - countUsed;
				if (countAble >= 1) {
					ContactClickLog cc = new ContactClickLog();
					cc.setCompanyId(companyId);
					cc.setTargetId(targetComId);
					cc.setClickScore(ChannelConst.SCORE_VIEW_CONTACT);
					cc.setClickFee(0f);
					int i = contactClickLogDao.insert(cc);
					if (i > 0) {
						viewAble = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return viewAble;
	}
}
