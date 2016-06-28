package com.ast.ast1949.service.sample.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinPrize;
import com.ast.ast1949.domain.sample.WeixinPrizelog;
import com.ast.ast1949.domain.sample.WeixinScoresall;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.sample.WeixinPrizeDao;
import com.ast.ast1949.persist.sample.WeixinPrizelogDao;
import com.ast.ast1949.persist.sample.WeixinScoreDao;
import com.ast.ast1949.persist.sample.WeixinScoresallDao;
import com.ast.ast1949.service.sample.WeixinPrizeService;

@Component("weixinPrizeService")
public class WeixinPrizeServiceImpl implements WeixinPrizeService {

	@Resource
	private WeixinPrizeDao weixinPrizeDao;
	
	@Resource
	private WeixinScoreDao weixinScoreDao;
	
	@Resource
	private WeixinScoresallDao weixinScoresallDao;
	
	@Resource
	private WeixinPrizelogDao weixinPrizelogDao;
	
	@Override
	public int insert(WeixinPrize record) {
		return weixinPrizeDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(WeixinPrize record) {
		return weixinPrizeDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinPrize record) {
		return weixinPrizeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public WeixinPrize selectByPrimaryKey(Integer id) {
		return weixinPrizeDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinPrizeDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageDto<WeixinPrize> queryListByFilter(PageDto<WeixinPrize> page, Map<String, Object> filterMap) {
		if (page.getSort() == null) {
			page.setSort("ord");
			page.setDir("asc");
		}

		filterMap.put("page", page);
		 page.setTotalRecords(weixinPrizeDao.queryListByFilterCount(filterMap));
		 page.setRecords(weixinPrizeDao.queryListByFilter(filterMap));
		return page;
	}

	
	/**
	 *   兑换申请
	 * @param id 奖品ID
	 * @param account 客户登录号码
	 * @param ischeck 是否需要审核  0：待审核  1：已审核通过
	 * @return -1： 奖品已兑换完   0：用户积分不足   1：兑奖申请成功
	 */
	@Override
	public int  prizeApply(Integer id, String account,Integer ischeck) {
		if(id==null || account==null )
			return -2;
		
		WeixinPrize   pz = weixinPrizeDao.selectByPrimaryKey(id);
		if(pz==null)
			return -2;
		
		// -1： 奖品已兑换完 
		if(pz.getNum()  <=  0)
			return -1;
		
		//可兑换的积分数
		Integer totalScore = weixinScoreDao.totalAvailableScore(account); 
		//已兑换的积分数
		Integer totalScoreEx = weixinPrizelogDao.totalConvertScore(account); 
		
		if((totalScore-totalScoreEx) >= pz.getScore()){
			
			/**
			 * 减奖品存量
			 */
			pz.setNum(pz.getNum()-1);//减存量
			weixinPrizeDao.updateByPrimaryKeySelective(pz);
			
			/**
			 * 减用户的总积分
			 */
			WeixinScoresall  scoreAll =weixinScoresallDao.selectByAccount( account);
			scoreAll.setScore(scoreAll.getScore()-pz.getScore());
			 weixinScoresallDao.updateByPrimaryKeySelective(scoreAll);
			
			/**
			 * 增加一条兑奖记录
			 */
			WeixinPrizelog prizeLog = new WeixinPrizelog();
			prizeLog.setAccount(account);
			prizeLog.setIscheck(ischeck);
			prizeLog.setPrizeid(id);
			prizeLog.setScore(pz.getScore());
			prizeLog.setType("2");// 1是手机,2是PC
			weixinPrizelogDao.insert(prizeLog);
			
		}else{
			// 0：用户积分不足 
			return 0;
		}
		
		return 1; //1：兑奖申请成功
	}
}
