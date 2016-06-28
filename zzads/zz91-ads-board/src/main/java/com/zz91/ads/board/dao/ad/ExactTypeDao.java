/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.dao.ad;

import java.util.List;

import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.Pager;

/**
 * 精确投放类型接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface ExactTypeDao {

	/**
	 * 添加一个精确投放类型 
	 * 注：exactName不能为空，且不可重复。
	 */
	public Integer insertExactType(ExactType exactType);

	/**
	 * 删除一个精确投放类型
	 */
	public Integer deleteExactTypeById(Integer id);

	/**
	 * 根据编号读取一个精确投放类型
	 */
	public ExactType queryExactTypeById(Integer id);

	/**
	 * 根据广告位编号读取所有精确投放类型
	 */
	public List<ExactType> queryExactTypeByAdPositionId(Integer id);

	/**
	 * 读取所有精确投放条件
	 */
	public List<ExactType> queryExactType(Pager<ExactType> pager);

	/**
	 * 统计所有精确投放条件记录总数
	 */
	public abstract Integer queryExactTypeCount();
	
	/**
	 * 根据名称读取一个精确投放类型
	 */
	public ExactType queryExactTypeByExactName(String name);
}
