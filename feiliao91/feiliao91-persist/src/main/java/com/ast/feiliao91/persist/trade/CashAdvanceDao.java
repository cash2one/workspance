package com.ast.feiliao91.persist.trade;

import java.util.List;

import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.domain.trade.CashAdvanceSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.CashAdvanceDto;

public interface CashAdvanceDao {
	/**
	 * 插入提现记录
	 * @param cashAdvance
	 * @return
	 */
	public Integer insertCashAdvance(CashAdvance cashAdvance);
	
	/**
	 * 根据id获得CashAdvance
	 * @param id
	 * @return
	 */
	public CashAdvance queryCashAdvanceById(Integer id);
	/**
	 * 根据条件搜索
	 * @param cashAdvance
	 * @return
	 */
	public List<CashAdvance> queryByCondition(CashAdvance cashAdvance);
	
	/**
	 * 后台提现记录列表搜索
	 * @param page
	 * @param search
	 * @return
	 */
	public List<CashAdvance> queryCashAdvanceList(PageDto<CashAdvanceDto> page,
			CashAdvanceSearch search);
	
	/**
	 * 统计搜索记录条数
	 * @param search
	 * @return
	 */
	public Integer countCashAdvanceList(CashAdvanceSearch search);
	
	/**
	 * 更新申请状态
	 * @param valueOf
	 * @param checkStatus
	 * @return
	 */
	public Integer updateStatus(Integer valueOf, Integer checkStatus,String checkPerson);

}
