package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccessView;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.persist.company.CompanyAccessViewDao;
import com.ast.ast1949.persist.company.InquiryDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.service.company.CompanyAccessViewService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.zz91.util.datetime.DateUtil;

@Component("companyAccessViewService")
public class CompanyAccessViewServiceImpl implements CompanyAccessViewService{

	@Resource
	private CompanyAccessViewDao companyAccessViewDao;
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private InquiryDao inquiryDao;
	
	@Override
	public Integer insert(Integer companyId,Integer targetId,String account){
		Integer i = companyAccessViewDao.queryCountByCondition(companyId, targetId, account, DateUtil.toString(new Date(), "yyyy-MM-dd"));
		if(i==null||i==0){
			CompanyAccessView companyAccessView = new CompanyAccessView();
			companyAccessView.setCompanyAccount(account);
			companyAccessView.setCompanyId(companyId);
			companyAccessView.setTargetId(targetId);
			try {
				companyAccessView.setGmtTarget(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				companyAccessView.setGmtTarget(new Date());
			}
			return companyAccessViewDao.insert(companyAccessView);
		}
		return 0;
	}
	
	@Override
	public Integer validateIsRefresh(Integer companyId, String account) {
		Integer result = null;
		do {
			// 验证是否微信绑定
			OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
			if (oa == null) {
				result = 2;
				break;
			}
			// 绑定有效期7天
			if (DateUtil.getDateAfterDays(oa.getGmtCreated(), 7).getTime() < new Date().getTime()) {
				result = 3;
				break;
			}
			
			result = 1;
		} while (false);
		return result;
	}
	
	@Override
	public Integer validateIsExists(Integer companyId, Integer targetId,String account) {
		Integer result = 0;
		do {
			// 验证是否微信绑定 
			OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
			if(oa==null){
				result = 4;
				break;
			}
			// 绑定有效期7天
			if(DateUtil.getDateAfterDays(oa.getGmtCreated(), 7).getTime()<new Date().getTime()){
				result = 5;
				break;
			}
			Date date = new Date();
			// 今天是否已经点击查看过该targetId查看过可继续查看
			Integer i = companyAccessViewDao.queryCountByCondition(companyId, targetId, account, DateUtil.toString(date, "yyyy-MM-dd"));
			if(i!=null&&i>0){
				result = 1;
				break;
			}
			// 统计今日查看总个数
			i = companyAccessViewDao.queryCountByCondition(companyId, null, account, DateUtil.toString(date, "yyyy-MM-dd"));
			// 小于10个	可以查看
			if(i==null||i<10){
				result = 1;
				break;
			}
			
			// 今日查看超过15个	不可以查看
			if(i>=15){
				result = 3;
				break;
			}
			// 今日 是否 发布5条供求 或者 发了10家公司 询盘
			boolean isOpen = false;
			String from = DateUtil.toString(new Date(), "yyyy-MM-dd");
			String to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1),"yyyy-MM-dd");
			Integer countPub = productsDAO.countProuductsByTitleAndAccount(null,null, account, from, to);
			Integer countInquiry = inquiryDao.countInquiryByCondition(from, to, account);
			if(countPub>=5||countInquiry>=10){
				isOpen = true;
			}
			// 大于等于10个 解绑	可以查看
			if(i>=10&&isOpen){
				result = 1;
				break;
			}
			// 大于等于10个 没有解绑	不可以查看
			if(i>=10&&!isOpen){
				result = 2;
				break;
			}
		} while (false);
		return result;
	}
	
}
