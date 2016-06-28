/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.dto.company.MemberApplyDTO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface MemberApplyService {
	/**
	 * 添加一条申请记录
	 * @param memberApplyDO
	 *		<ol>memberApplyDO.userName 不能为空；</ol>
	 *		<ol>memberApplyDO.membershipCode 不能为空；</ol>
	 *		<ol>memberApplyDO.tel 不能为空；</ol>
	 *		<ol>memberApplyDO.mobile 不能为空；</ol>
	 *		<ol>memberApplyDO.email 不能为空；</ol>
	 *		<ol>memberApplyDO.gmtCreated初始为当前时间；</ol>
	 *		<ol>memberApplyDO.gmtModified初始为当前时间；</ol>
	 *		<ol>memberApplyDO.processStatus初始为未处理；</ol>
	 *		<ol>memberApplyDO.processPerson初始为空；</ol>
	 * @return
	 * 		成功，返回当前添加记录的编号（id）；</br>
	 * 		失败，返回null。
	 */
//	public Integer insertMemberApply(MemberApplyDO memberApplyDO);
	
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
	 * @param processStatus 状态 1已处理 0未处理
	 * @param processPerson 处理人
	 * @param remark 备注
	 * @param id 编号
	 * @return
	 */
	public Integer updateProcessStatusById(String processStatus,String processPerson,String remark,Integer id);
}
