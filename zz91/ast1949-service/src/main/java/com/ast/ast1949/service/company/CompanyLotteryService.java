package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyLottery;
import com.ast.ast1949.dto.PageDto;
/**
 * 2013年3月份促销活动抽奖 记录service
 * @author sj
 * @date 2013-3-1
 */
public interface CompanyLotteryService {
	
	/**
	 * 分页获取抽奖记录
	 * @param companyLottery
	 * @param page
	 * @return
	 */
	public PageDto<CompanyLottery> pageCompanyLottery(CompanyLottery companyLottery,PageDto<CompanyLottery> page);
	
	/**
	 * 根据公司id搜索一条抽奖记录
	 * @param companyId
	 * @return
	 */
	public CompanyLottery queryLotteryByCompanyId(Integer companyId);
	
	/**
	 * 获取中奖记录 列表
	 * @param companyLottery
	 * @param size :limit 最大条数
	 * @return
	 */
	public List<CompanyLottery> queryCompanyLotteryed(Integer size);
	
	/**
	 * 创建一个关闭的抽奖记录
	 * @param companyId
	 * @param lotteryCode
	 * @param companyAccount
	 * @param csAccount
	 * @return
	 */
	public Integer addOne(Integer companyId,String lotteryCode,String companyAccount,String csAccount);
	
	/**
	 * 激活一个抽奖记录
	 * @param companyLottery
	 * @return
	 */
	public Integer openOneLottery(Integer id);
	
	/**
	 * 关闭一个抽奖记录
	 * @return
	 */
	public Integer closeOneLottery(Integer id);
	
	/**
	 * 更新一条抽奖记录为 已抽过奖 状态
	 * @return
	 */
	public Integer sucOneLottery(Integer id);
	
	/**
	 * 根据companyId检索抽奖次数
	 * @param companyId
	 * @return
	 */
	public Integer queryCountLotteryByCompanyId(Integer companyId);
	
	/**
	 * 更新获取的奖品
	 * @param lottery
	 * @param id
	 * @return
	 */
	public Integer updateLottery(String lottery,Integer id);
}
