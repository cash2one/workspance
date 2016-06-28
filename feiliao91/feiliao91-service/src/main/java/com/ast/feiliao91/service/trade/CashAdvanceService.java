package com.ast.feiliao91.service.trade;

import java.util.List;

import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.domain.trade.CashAdvanceSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.CashAdvanceDto;

public interface CashAdvanceService {
	/**
	 * 插入提现记录
	 * @param cashAdvance
	 * @return
	 */
	public Integer insertCashAdvance(CashAdvance cashAdvance);
	
	/**
	 * 根据条件搜索
	 * @param cashAdvance
	 * @return List<CashAdvance>
	 */
	public List<CashAdvance> queryByCondition(CashAdvance cashAdvance);
	
	/**
	 *根据id获得CashAdvance
	 * @param id
	 * @return
	 */
	public CashAdvance queryCashAdvanceById(Integer id);
	/**
	 * 后台提现搜索
	 * @param page
	 * @param search
	 * @return
	 */
	public PageDto<CashAdvanceDto> pageBySearch(PageDto<CashAdvanceDto> page,CashAdvanceSearch search);
	
	/**
	 * 更新申请状态
	 * @param ids
	 * @param checkStatus
	 * @return
	 */
	public String updateStatus(String ids, Integer checkStatus, String checkPerson);

	public Integer updateCashAdvanceTransactional(CashAdvance cashAdvance);
}
