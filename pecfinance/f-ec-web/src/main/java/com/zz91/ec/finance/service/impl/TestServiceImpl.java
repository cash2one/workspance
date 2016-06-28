/**
 * 
 */
package com.zz91.ec.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ec.finance.domain.Test;
import com.zz91.ec.finance.persist.TestMapper;
import com.zz91.ec.finance.service.TestService;

/**
 * @author mays
 *
 */
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
