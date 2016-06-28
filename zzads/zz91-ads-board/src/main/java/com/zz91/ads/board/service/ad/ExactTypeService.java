/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import java.util.List;

import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.Pager;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface ExactTypeService {

	/**
	 * 保存一个精确投放类型 
	 * 注：先判断该名称是否已经存在，若不存则添加一条记录， 并返回新增记录的Id值；若已存在，则返回该记录的id值。
	 * exactName不能为空，且不可重复。
	 */
	public Integer saveExactType(ExactType exactType);

	/**
	 * 删除一个精确投放类型
	 */
	public Integer deleteExactTypeById(Integer id);

	/**
	 * 根据编号读取一个精确投放类型
	 */
	public ExactType queryExactTypeById(Integer id);

	/**
	 * 根据名称读取一个精确投放类型
	 */
	public ExactType queryExactTypeByExactName(String name);

	/**
	 * 根据广告位编号读取所有精确投放类型
	 */
	public List<ExactType> queryExactTypeByAdPositionId(Integer id);

	/**
	 * 读取所有精确投放条件
	 */
	public Pager<ExactType> pageExactType(Pager<ExactType> pager);
}
