/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17
 */
package com.ast.ast1949.persist.company;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.MemberApplyDO;
import com.ast.ast1949.dto.company.MemberApplyDTO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface MemberApplyDAO {
	/**
	 * 添加一条申请记录
	 * @param memberApplyDO
	 *		<ol>memberApplyDO.userName 不能为空；</ol>
	 *		<ol>memberApplyDO.membershipCode 不能为空；</ol>
	 *		<ol>memberApplyDO.tel 不能为空；</ol>
	 *		<ol>memberApplyDO.mobile 不能为空；</ol>
	 *		<ol>memberApplyDO.email 不能为空；</ol>
	 * @return
	 * 		成功，返回当前添加记录的编号（id）；</br>
	 * 		失败，返回null。
	 */
	public Integer insertMemberApply(MemberApplyDO memberApplyDO);
	
	/**
	 * 读取申请人列表
	 * @param memberApplyDTO
	 * @return
	 */
	public List<MemberApplyDTO> queryMemberApplyList(MemberApplyDTO memberApplyDTO);
	
	/**
	 * 读取申请人总数
	 * @param memberApplyDTO
	 * @return
	 */
	public Integer countMemberApplyList(MemberApplyDTO memberApplyDTO);
	
	/**
	 * 更新状态
	 * @param param 参数：<br/>
	 * 		processStatus 状态 1已处理 0未处理
	 * 		processPerson 处理人
	 * 		remark 备注
	 * 		id 编号
	 * @return
	 */
	public Integer updateProcessStatusById(Map<String, Object> param);
}
