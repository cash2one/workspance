/**
 * @author shiqp
 * @date 2015-07-22
 */
package com.ast.ast1949.service.trust.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustRelateSell;
import com.ast.ast1949.domain.trust.TrustSell;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.trust.TrustRelateSellDao;
import com.ast.ast1949.persist.trust.TrustSellDao;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.ast.ast1949.service.trust.TrustRelateSellService;

@Component("trustRelateSellService")
public class TrustRelateSellServiceImpl implements TrustRelateSellService {
	@Resource
	private TrustRelateSellDao trustRelateSellDao;
	@Resource
	private TrustSellDao trustSellDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private TrustBuyService trustBuyService;

	@Override
	public List<Company> queryCompanyByBuyId(Integer buyId,Integer companyId) {
		List<Company> list = new ArrayList<Company>();
		if (buyId!=null&&buyId>0) {
			//先获取已经采纳的供应id
			List<TrustRelateSell> listSell = trustRelateSellDao.querySellsByBuyId(buyId);
			for(TrustRelateSell ll : listSell){
				TrustSell sell = trustSellDao.queryById(ll.getSellId());
				if(sell!=null){
					Company c = companyDAO.queryCompanyById(sell.getCompanyId());
					if(c!=null){
						list.add(c);
					}
				}
			}
		}else if(companyId!=null&&companyId>0){
			// 获取该公司的所有供货下的所有公司
			List<TrustBuy> listBuy = trustBuyService.queryTrustByCompanyId(companyId);
			Map<Integer, Company> map = new HashMap<Integer, Company>();
			for (TrustBuy obj:listBuy) {
				List<TrustRelateSell> listSell = trustRelateSellDao.querySellsByBuyId(obj.getId());
				for(TrustRelateSell ll : listSell){
					TrustSell sell = trustSellDao.queryById(ll.getSellId());
					if(sell!=null){
						Company c = companyDAO.queryCompanyById(sell.getCompanyId());
						if(c!=null){
							map.put(c.getId(), c);
						}
					}
				}
			}
			for (Integer cid:map.keySet()) {
				list.add(map.get(cid));
			}
		}
		return list;
	}

}
