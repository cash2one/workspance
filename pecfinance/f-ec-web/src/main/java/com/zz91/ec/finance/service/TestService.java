/**
 * 
 */
package com.zz91.ec.finance.service;

import java.util.List;

import com.zz91.ec.finance.domain.Test;

/**
 * @author mays
 *
 */
public interface TestService {

	public Integer insert(Test test);
	public List<Test> queryAll();
}
