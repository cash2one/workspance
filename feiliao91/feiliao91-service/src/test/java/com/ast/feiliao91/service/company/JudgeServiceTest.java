package com.ast.feiliao91.service.company;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.service.BaseServiceTestCase;

public class JudgeServiceTest extends BaseServiceTestCase{
	@Autowired
	private JudgeService judgeService;
	
	@Test
	public void test_avg(){
		Map<String, String> map = judgeService.getAvgStar(1,null);
		System.out.println(map);
	}
	
	@Test
	public void test_count(){
		System.out.println(judgeService.countJudgeNumByCondition(4));
	}
}
