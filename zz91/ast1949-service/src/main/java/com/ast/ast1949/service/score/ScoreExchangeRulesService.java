package com.ast.ast1949.service.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.dto.PageDto;

public interface ScoreExchangeRulesService {
 
	static final String PRECODE_SERVICE = "service";
	
	public List<ScoreExchangeRulesDo> queryRulesByPreCode(String preCode);
	public PageDto<ScoreExchangeRulesDo> pageRules(PageDto<ScoreExchangeRulesDo> page);
}
 
