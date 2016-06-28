package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsScoreDto;

public interface BbsScoreDao {
	
	public Integer insert(BbsScore bbsScore);
	
	public List<BbsScore> query(BbsScoreDto bbsScoreDto,PageDto<BbsScoreDto> page);
	
	public Integer queryCount(BbsScoreDto bbsScoreDto);
	
	public Integer sumScore(BbsScore bbsScore);
	//根据id（问答或者帖子）查记录
	public BbsScore querybyId(BbsScore bbsScore);
}
