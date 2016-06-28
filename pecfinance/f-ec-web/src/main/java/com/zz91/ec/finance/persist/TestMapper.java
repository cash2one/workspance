package com.zz91.ec.finance.persist;

import java.util.List;

import com.zz91.ec.finance.domain.Test;


public interface TestMapper {

	public Integer insert(Test test);
	
	public List<Test> query();
	
}
