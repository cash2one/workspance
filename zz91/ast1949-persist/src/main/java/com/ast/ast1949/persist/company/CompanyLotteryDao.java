package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyLottery;
import com.ast.ast1949.dto.PageDto;

public interface CompanyLotteryDao {
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<CompanyLottery> query(CompanyLottery companyLottery,PageDto<CompanyLottery> page);

	public Integer queryCount(CompanyLottery companyLottery);

	public Integer insert(CompanyLottery companyLottery);

	public Integer update(CompanyLottery companyLottery);
	
	public Integer updateStatus(Integer id,String status);
	
	/**
	 * 获奖列表
	 * @param size
	 * @return
	 */
	public List<CompanyLottery> queryCompanyLotteryed(Integer size);

	/**
	 * 根据公司id搜索公司享有的抽奖次数
	 * @param companyId
	 * @return
	 */
	public Integer queryCountLotteryByCompanyId(Integer companyId);
	
	public CompanyLottery queryLotteryByCompanyId(Integer companyId);
	
}