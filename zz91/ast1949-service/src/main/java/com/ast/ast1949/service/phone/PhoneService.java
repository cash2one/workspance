package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.dto.PageDto;

/**
 *	author:kongsj
 *	date:2013-7-3
 */
public interface PhoneService {
	/**
	 * 根据id检索一条记录
	 * @param id
	 * @return
	 */
	public Phone queryById(Integer id);

	/**
	 * 新增一条记录
	 * @param phone
	 * @return
	 */
	public Integer insert(Phone phone);

	/**
	 * 更新一条记录
	 * @param phone
	 * @return
	 */
	public Integer update(Phone phone);

	/**
	 * 分页显示数据
	 * @param phone
	 * @param page
	 * @return
	 */
	public PageDto<Phone> pageList(Phone phone, PageDto<Phone> page);
	
	public Integer delete(Integer id);
	
	
	public Phone queryByCompanyId(Integer companyId);
	
	public Phone queryByTel(String tel);

}
