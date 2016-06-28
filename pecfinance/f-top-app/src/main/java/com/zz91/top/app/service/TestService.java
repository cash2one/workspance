/**
 * 
 */
package com.zz91.top.app.service;

import java.util.List;

import com.zz91.top.app.domain.Test;

/**
 * @author mays
 *
 */
public interface TestService {

	public Integer insert(Test test);
	public List<Test> queryAll();
}
