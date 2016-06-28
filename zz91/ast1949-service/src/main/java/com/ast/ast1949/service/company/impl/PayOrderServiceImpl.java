/**
 * @author kongsj
 * @date 2014年11月12日
 * 
 */
package com.ast.ast1949.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.PayOrder;
import com.ast.ast1949.persist.company.PayOrderDao;
import com.ast.ast1949.service.company.PayOrderService;
import com.zz91.util.lang.StringUtils;

@Component("payOrderService")
public class PayOrderServiceImpl implements PayOrderService{

	@Resource
	private PayOrderDao payOrderDao;
	
	@Override
	public Integer createOrder(String noOrder,String dtOrder,Integer companyId) {
		Integer i = 0;
		do {
			if (StringUtils.isEmpty(noOrder)) {
				break;
			}
			
			PayOrder po = payOrderDao.queryByNoOrder(noOrder);
			// 存在
			if (po!=null) {
				break;
			}
			po = new PayOrder();
			po.setCompanyId(companyId);
			po.setDtOrder(dtOrder);
			po.setNoOrder(noOrder);
			i = payOrderDao.insert(po);
		} while (false);
		return i;
	}
}
