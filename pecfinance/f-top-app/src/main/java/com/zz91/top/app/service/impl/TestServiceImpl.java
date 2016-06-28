/**
 * 
 */
package com.zz91.top.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.top.app.domain.Test;
import com.zz91.top.app.persist.TestMapper;
import com.zz91.top.app.service.TestService;

/**
 * @author mays
 *
 */
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
@Component("testService")
public class TestServiceImpl implements TestService {

	@Resource
	private TestMapper testMapper;
	
	@Override
	public Integer insert(Test test) {
		testMapper.insert(test);
		return test.getId();
	}

	@Override
	public List<Test> queryAll() {
		return testMapper.query();
	}

}
