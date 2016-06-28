package com.ast.ast1949.persist.tags;

import java.util.List;

import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.domain.tags.TagsStatistic;
import com.ast.ast1949.domain.tags.TagsStatisticDaily;
import com.ast.ast1949.domain.tags.TagsStatisticMonth;
import com.ast.ast1949.domain.tags.TagsStatisticYear;

public interface TagsStatisticDao {
	
	Integer insertTagsStatisticDaily(TagsStatisticDaily tagsStatisticDaily);
	
	Integer insertTagsStatisticWeekly(TagsStatistic tagsStatistic);

	Integer insertTagsStatisticMonth(TagsStatisticMonth tagsStatisticMonth);
	
	Integer insertTagsStatisticYear(TagsStatisticYear tagsStatisticYear);
	
	/**
	 * 根据统计类别查询每日统计标签信息
	 */
	List<TagsInfoDO> queryTagListByStatCatAndArtCat(String statCategoryCode, String artCatCode,
			Integer topNum);

	/**
	 * 根据统计类别查询周统计标签信息
	 */
	List<TagsInfoDO> queryWeeklyStatTagsByStatCat(String statCategoryCode,Integer topNum);

	
	
}
