package com.ast.ast1949.service.analysis.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisOperate;
import com.ast.ast1949.persist.analysis.AnalysisOperateDao;
import com.ast.ast1949.service.analysis.AnalysisOperateService;

/**
 * @author kongsj
 * @date 2012-9-11
 */
@Component("analysisOperateService")
public class AnalysisOperateServiceImpl implements AnalysisOperateService {

	@Resource
	private AnalysisOperateDao analysisOperateDao;

	@Override
	public List<AnalysisOperate> queryAnalysisOperate(String account,
			String start, String end) {
		List<AnalysisOperate> list = analysisOperateDao.queryAnalysisOperate(account, start, end);
		AnalysisOperate ao = new AnalysisOperate();
		ao.setOperator("汇总");
		for(AnalysisOperate obj :list){
			ao.setBbsAdminPost(summary(ao.getBbsAdminPost(), obj.getBbsAdminPost()));
			ao.setBbsClientPostFailure(summary(ao.getBbsClientPostFailure(), obj.getBbsClientPostFailure()));
			ao.setBbsClientPostSuccess(summary(ao.getBbsClientPostSuccess(), obj.getBbsClientPostSuccess()));
			ao.setBbsReplyFailure(summary(ao.getBbsReplyFailure(), obj.getBbsReplyFailure()));
			ao.setBbsReplySuccess(summary(ao.getBbsReplySuccess(), obj.getBbsReplySuccess()));
			ao.setCheckComppriceFailure(summary(ao.getCheckComppriceFailure(), obj.getCheckComppriceFailure()));
			ao.setCheckComppriceSuccess(summary(ao.getCheckComppriceSuccess(), obj.getCheckComppriceSuccess()));
			ao.setCheckProductsFailure(summary(ao.getCheckProductsFailure(), obj.getCheckProductsFailure()));
			ao.setCheckProductsSuccess(summary(ao.getCheckProductsSuccess(), obj.getCheckProductsSuccess()));
			ao.setImportToProducts(summary(ao.getImportToProducts(), obj.getImportToProducts()));
			ao.setPostPrice(summary(ao.getPostPrice(), obj.getPostPrice()));
			ao.setPostPriceText(summary(ao.getPostPriceText(), obj.getPostPriceText()));
		}
		list.add(ao);
		return list;
	}
	
	private Integer summary(Integer i, Integer j) {
		if (i == null) {
			return j;
		}
		return i.intValue() + j.intValue();
	}
}
