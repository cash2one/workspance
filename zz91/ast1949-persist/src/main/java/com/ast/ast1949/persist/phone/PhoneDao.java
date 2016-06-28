package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.dto.PageDto;

/**
 * author:kongsj date:2013-7-3
 */
public interface PhoneDao {
	public Phone queryById(Integer id);

	public Integer insert(Phone phone);

	public Integer update(Phone phone);

	public List<Phone> queryList(Phone phone, PageDto<Phone> page);

	public Integer queryListCount(Phone phone);
	
	public Integer updateAmountAndBalance(Integer id,String amount,String balance);
	
	public Integer countByAccount(String account);
	
	public Integer countByCompanyId(Integer companyId);
	
	public Integer deleteById(Integer id);
	
	public Phone queryByCompanyId(Integer companyId);
	
	public Phone queryByTel(String tel);
}
