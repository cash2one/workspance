package com.zz91.top.app.persist;

import java.util.List;

import com.zz91.top.app.domain.Test;


public interface TestMapper {

	public Integer insert(Test test);
	
	public List<Test> query();
	
}
