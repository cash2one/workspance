package com.ast.feiliao91.service.company.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.JudgeDto;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.company.JudgeDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.persist.goods.OrdersDao;
import com.ast.feiliao91.service.company.JudgeService;
import com.zz91.util.datetime.DateUtil;

@Component("judgeService")
public class JudgeServiceImpl implements JudgeService {

	@Resource
	private JudgeDao judgeDao;
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private OrdersDao ordersDao;
	public Integer getTradeNum(Integer companyId) {
		if (companyId == null || companyId < 1) {
			return 0;
		}
		Integer i = judgeDao.countTradeNum(companyId);
		if (i == null || i < 1) {
			return 0;
		}
		return i;
	}

	@Override
	public Map<String, String> getAvgStar(Integer companyId, Integer month) {
		if (companyId == null || companyId < 1) {
			return null;
		}
		Map<String, String> map = judgeDao.avgByCondition(companyId, month);
		Map<String, String> resultMap = new HashMap<String, String>();
		for (String obj : map.keySet()) {
			String value = String.valueOf(map.get(obj));
			Float a = 0f;
			try {
				a = Float.parseFloat(value);
			} catch (Exception e) {
			}
			BigDecimal b = new BigDecimal(a);
			a = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			resultMap.put(obj, "" + a);
		}
		return resultMap;
	}

	@Override
	public Map<String, Integer> countJudgeNumByCondition(Integer goodId) {
		if (goodId == null || goodId < 1) {
			return null;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer i = judgeDao.countJudgeNumByCondition(goodId, GOOD_TYPE);
		if (i == null) {
			i = 0;
		}
		map.put("goodNum", i);
		i = judgeDao.countJudgeNumByCondition(goodId, ORDINARY_TYPE);
		if (i == null) {
			i = 0;
		}
		map.put("ordinaryNum", i);
		i = judgeDao.countJudgeNumByCondition(goodId, BAD_TYPE);
		if (i == null) {
			i = 0;
		}
		map.put("badNum", i);
		i = judgeDao.countJudgeNumByCondition(goodId, PIC_TYPE);
		if (i == null) {
			i = 0;
		}
		map.put("picNum", i);
		return map;
	}

	public PageDto<JudgeDto> pageJudgeByCondition(PageDto<JudgeDto> page,
			Integer type, Integer companyId, Integer targetId, Integer goodId) {
		if (targetId == null && companyId == null) {
			// 产品最终页
			List<Judge> list = judgeDao.queryJudgeByCondition(page, type, null,
					null, goodId);
			List<JudgeDto> listResult = new ArrayList<JudgeDto>();
			for (Judge jg : list) {
				JudgeDto dto = new JudgeDto();
				dto.setJudge(jg);
				// 公司信息
				CompanyInfo info = companyInfoDao.queryById(jg.getCompanyId());
				if (info != null) {
					dto.setInfo(info);
				}
				// 图片信息
				List<String> listImg = new ArrayList<String>();
				if (StringUtils.isNotEmpty(jg.getPicAddress())) {
					String[] picAdd = jg.getPicAddress().split(",");
					for (String pic : picAdd) {
						listImg.add(pic);
					}
				}
				// 发布时间
				String postTime = DateUtil.toString(jg.getGmtCreated(),
						"yyyy年MM月dd日 HH:mm");
				dto.setPostTime(postTime);
				listResult.add(dto);
			}
			page.setRecords(listResult);
		}
		page.setTotalRecords(judgeDao.countJudgeNumByCondition(type, null,
				null, goodId));
		return page;
	}

	@Override
	public String avgGoodStar(Integer companyId) {
		return judgeDao.avgGoodStar(companyId);
	}

	@Override
	public Integer getcount(Integer companyId) {
		return judgeDao.getcount(companyId);
	}

	@Override
	public Integer getEvaluation(Integer month, Integer level, Integer companyId) {
		if (month.equals(1000)) {
			return judgeDao.getEvaluationTwo(month, level, companyId);
		}
		return judgeDao.getEvaluation(month, level, companyId);
	}

	@Override
	public PageDto<JudgeDto> pageJudgeByAll(PageDto<JudgeDto> page,
			Integer type, Integer companyId, Integer type2) {
		if (type2 == null) {
			type2=0;
		}
		if (companyId != null) {
			List<Judge> list = judgeDao.pageJudgeByAll(page, type, companyId,type2);
			List<JudgeDto> listResult = new ArrayList<JudgeDto>();
			for (Judge gp : list) {
				JudgeDto dto = new JudgeDto();
				if(null==gp){
					continue;
				}
				dto.setJudge(gp);
				
				Orders oder=ordersDao.selectById(gp.getOrderId());
				Float fl=0f;
				if(oder!=null){
				if(oder.getBuyPricePay()>0){
				   fl= oder.getBuyPricePay();
				}
				}
				dto.setPrice(fl);
				
				CompanyInfo info = companyInfoDao.queryById(gp.getTargetId());
				if (info != null) {
					dto.setInfo(info);
				}
				// 获取信用积分
				Integer integral = judgeDao.countTradeNum(gp.getTargetId());
				dto.setSellCred(integral);
				
				CompanyInfo info2 = companyInfoDao.queryById(gp.getCompanyId());
				if (info2 != null) {
					dto.setBuyInfo(info2);
				}
				Goods goods = goodsDao.queryGoodById(gp.getGoodId());
				if(goods!=null){
					dto.setGoods(goods);
				}
				// 图片信息
				List<String> listImg = new ArrayList<String>();
				if (StringUtils.isNotEmpty(gp.getPicAddress())) {
					String[] picAdd = gp.getPicAddress().split(",");
					for (String pic : picAdd) {
						listImg.add(pic);
					}
					dto.setPicAddress(listImg);
				}
				// 发布时间
				String postTime = DateUtil.toString(gp.getGmtCreated(),
						"yyyy年MM月dd日 HH:mm");
				dto.setPostTime(postTime);
				listResult.add(dto);
			}
			page.setRecords(listResult);
		}
		page.setTotalRecords(judgeDao.pageJudgeByAllcount(type, companyId,type2));
		return page;
	}

	@Override
	public Integer countTradeNum(Integer compangyId) {
		return judgeDao.countTradeNum(compangyId);
	}

	@Override
	public PageDto<JudgeDto> pageJudgeBySellAll(PageDto<JudgeDto> page,
			Integer type, Integer companyId, Integer type2) {
		if (type2 == null) {
			type2=0;
		}
		if (companyId != null) {
			List<Judge> list = judgeDao.pageJudgeBySellAll(page, type, companyId,type2);
			List<JudgeDto> listResult = new ArrayList<JudgeDto>();
			for (Judge gp : list) {
				JudgeDto dto = new JudgeDto();
				if(null==gp){
					continue;
				}
				dto.setJudge(gp);
				
				Orders oder=ordersDao.selectById(gp.getOrderId());
				Float fl=0f;
				if(oder!=null){
				if(oder.getBuyPricePay()>0){
//				   DecimalFormat df = new DecimalFormat("0.00");
//				   String po=df.format(fl);
				   fl= oder.getBuyPricePay();
				}
				}
				dto.setPrice(fl);
				
				CompanyInfo info = companyInfoDao.queryById(gp.getTargetId());
				if (info != null) {
					dto.setInfo(info);
				}
				
				// 获取信用积分
				Integer integral = judgeDao.countTradeNum(gp.getTargetId());
				dto.setSellCred(integral);
				
				CompanyInfo info2 = companyInfoDao.queryById(gp.getCompanyId());
				
				if (info2 != null) {
					dto.setBuyInfo(info2);
				}
				Goods goods = goodsDao.queryGoodById(gp.getGoodId());
				if(goods!=null){
					dto.setGoods(goods);
				}
				// 图片信息
				List<String> listImg = new ArrayList<String>();
				if (StringUtils.isNotEmpty(gp.getPicAddress())) {
					String[] picAdd = gp.getPicAddress().split(",");
					for (String pic : picAdd) {
						listImg.add(pic);
					}
					dto.setPicAddress(listImg);
				}
				// 发布时间
				String postTime = DateUtil.toString(gp.getGmtCreated(),
						"yyyy年MM月dd日 HH:mm");
				dto.setPostTime(postTime);
				listResult.add(dto);
			}
			page.setRecords(listResult);
		}
		page.setTotalRecords(judgeDao.pageJudgeBySellAllCount(type, companyId,type2));
		return page;
	}

	@Override
	public boolean hasJudgeByOrderId(Integer orderId) {
		if (orderId==null||orderId<0) {
			return false;
		}
		Judge judge = judgeDao.queryByOrderId(orderId);
		if (judge!=null) {
			return true;
		}
		return false;
	}

	@Override
	public Integer insert(Judge judge) {
		return judgeDao.insert(judge);
	}



}
