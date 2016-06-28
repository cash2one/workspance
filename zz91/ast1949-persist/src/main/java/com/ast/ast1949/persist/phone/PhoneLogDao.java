package com.ast.ast1949.persist.phone;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneLogDto;

/**
 * author:kongsj date:2013-7-13
 */
public interface PhoneLogDao {
	public PhoneLog queryByCallSn(String callSn);

	public Integer insert(PhoneLog phoneLog);

	public List<PhoneLog> queryList(PhoneLog phoneLog, PageDto<PhoneLog> page);

	public Integer queryListCount(PhoneLog phoneLog);
	
	public String countCallFee(String tel,Integer companyId);
	
	public List<PhoneLogDto> queryDtoList(PhoneLog phoneLog,PageDto<PhoneLogDto> page);
	
	public List<PhoneLogDto> exportPhoneLog(String tel,Date from,Date to,PageDto<PhoneLogDto> page);
	
	public String countCallFeeByTime(PhoneLog phoneLog);
	
	public Integer queryPhoneLogListCount(PhoneLog phoneLog);
	
	public String countAllCallFee();
	
	public String countCallFeeByCallSn(PhoneLog phoneLog);
	
	public Integer queryPhoneLogIsCount(PhoneLog phoneLog);

	public Integer queryDtoListCount(PhoneLog phoneLog);
	//统计未接电话个数，去重复，去自己
	public Integer queryDtoListCounts(PhoneLog phoneLog,String mobile);
	
	/**
	 * 计算来电宝分机成本月租
	 * **/
	public Integer queryCountTelRentByPhoneLog(PhoneLog phoneLog);

	public String countEveCallFee(PhoneLog phoneLog);
	/**
	 * 获取某400客户未解来电清单
	 */
	public List<PhoneLog> queryListByTel(String tel,PageDto<PhoneLog> page);
	/**
	 * 统计获取某400客户未解来电清单的总数
	 */
	public Integer countListByTel(String tel);
	/**
	 * 根据callSn得到callerId
	 */
	public PhoneLog queryPhoneLogByCallSn(String callSn);
	/**
	 * 搜索一段时间内的已接电话的companyId
	 */
	public Integer queryYJCompanyBytime(String from,String to);
	/**
	 * 搜索一段时间内的未接电话的companyId
	 */
	public Integer queryWJCompanyBytime(String from,String to);
	/**
	 * 搜索某个公司一段时间内的已接电话量
	 */
	public Integer countYJCompanyBytime(Integer companyId,String from,String to);
	/**
	 * 搜索某个公司一段时间内的未接电话量
	 */
	public Integer countWJCompanyBytime(Integer companyId,String from,String to);

	/**
	 * 统计今天之前所有的电话消费
	 * @param tel
	 * @return
	 */
	String countCallFeeWithOutToday(String tel,Integer companyId);
   /**统计符合条件的所有记录
    * @param from
    * @param to
    * @param page
    */
	public List<PhoneLogDto> exportAllPhoneLog(Date from,Date to,PageDto<PhoneLogDto> page);
	/**
	 * 根据Id更新CallFee为0，来黑来电时用
	 * @author zhujq
	 * @param id
	 * @return
	 */
	public Integer updateCallFeeById(Integer id);
	
	/**
	 * 前台通话明细不显示未接
	 * @author zhujq
	 * @param phoneLog
	 * @param page
	 * @return
	 */
	public List<PhoneLogDto> queryDtoListForFront(PhoneLog phoneLog,PageDto<PhoneLogDto> page);
}
