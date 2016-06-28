package com.ast.ast1949.persist.tags.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.domain.tags.TagsStatistic;
import com.ast.ast1949.domain.tags.TagsStatisticDaily;
import com.ast.ast1949.domain.tags.TagsStatisticMonth;
import com.ast.ast1949.domain.tags.TagsStatisticYear;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.tags.TagsStatisticDao;

@Repository("tagsStatisticDao")
public class TagsStatisticDaoImpl extends BaseDaoSupport implements TagsStatisticDao {

	@Override
	public Integer insertTagsStatisticDaily(TagsStatisticDaily tagsStatisticDaily) {
		tagsStatisticDaily.setSqlKey("tagsStatistic.insertTagsStatisticDaily");
		return insert(tagsStatisticDaily);
	}

	@Override
	public Integer insertTagsStatisticMonth(TagsStatisticMonth tagsStatisticMonth) {
		tagsStatisticMonth.setSqlKey("tagsStatistic.insertTagsStatisticMonth");
		return insert(tagsStatisticMonth);
	}

	@Override
	public Integer insertTagsStatisticWeekly(TagsStatistic tagsStatistic) {
		tagsStatistic.setSqlKey("tagsStatistic.insertTagsStatisticWeekly");
		return insert(tagsStatistic);
	}

	@Override
	public Integer insertTagsStatisticYear(TagsStatisticYear tagsStatisticYear) {
		tagsStatisticYear.setSqlKey("tagsStatistic.insertTagsStatisticYear");
		return insert(tagsStatisticYear);
	}

	@Override
	public List<TagsInfoDO> queryTagListByStatCatAndArtCat(String statCategoryCode, String artCatCode,
			Integer topNum) {
		Map paramMap = new HashMap();
		paramMap.put("statCategoryCode", statCategoryCode);
		paramMap.put("artCatCode", artCatCode);
		paramMap.put("topNum", topNum);
		return getSqlMapClientTemplate().queryForList("tagsStatistic.queryTagListByStatCatAndArtCat",
				paramMap);
	}

	@Override
	public List<TagsInfoDO> queryWeeklyStatTagsByStatCat(String statCategoryCode, Integer topNum) {
		Map paramMap = new HashMap();
		paramMap.put("statCategoryCode", statCategoryCode);
		paramMap.put("topNum", topNum);
		return getSqlMapClientTemplate().queryForList("tagsStatistic.queryWeeklyStatTagsByStatCat",
				paramMap);
	}

}
