package com.ast.ast1949.service.analysis;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPpcAdslogDto;

public interface AnalysisPpcAdslogService {
	/**
	 * 获取广告位各种处理过的数据
	 * @param AnalysisPpcAdslogDto
	 * @param from
	 * @param to
	 * @param page
	 * @param key
	 * @return
	 */
	public PageDto<AnalysisPpcAdslogDto> pageAdslogDto(PageDto<AnalysisPpcAdslogDto> page,String from,String to,List<String> key,String sort,String dir,String tel);
	/**
	 * 广告位总量统计
	 * @param sisPpcAdslogDto
	 * @param page
	 * @param key
	 */
	public AnalysisPpcAdslogDto queryAllCountByTime(List<String> key,String tel,String from,String to);
	 /**
	  *广告位平均每天的值统计 
	  * @param sisPpcAdslogDto
	  * @param page
	  * @param key
	  */
	 public AnalysisPpcAdslogDto getAveCount(AnalysisPpcAdslogDto dto,String from,String to);
	 /**
	  * 各坐标轴的点集
	  * @param page
	  */
	 public List<String> getPoint(PageDto<AnalysisPpcAdslogDto> page);
	 /**
	  * 广告位的获取 
	  */
	 public Map<String,Object> getAdposition();
	 /**
	  * 每天广告位各量统计
	  * @param AnalysisPpcAdslogDto
	  * @param from
	  * @param to
	  * @param page
	  * @param key
	  */
	 public PageDto<AnalysisPpcAdslogDto> pageAdslogTime(PageDto<AnalysisPpcAdslogDto> page,String from,String to,List<String> key,String sort,String dir,String tel);
	 /**
	  * 升序
	  */
	 public List<AnalysisPpcAdslogDto> getDesc(List<AnalysisPpcAdslogDto> ads,String sort);
	 /**
	  * 每广告位当然均值统计 
	  */
	 public AnalysisPpcAdslogDto getAvePositionCount(AnalysisPpcAdslogDto dto,List<String> key);
	 /**
	  * 柱状图各坐标的集合
	  */
	 public List<String> getBarPoint(PageDto<AnalysisPpcAdslogDto> page);
	 /**
	  * 某广告位下400用户的各统计量
	  */
	 public List<AnalysisPpcAdslogDto> findDataByAdATime(String id,String tel,String from,String to);
	/**
	 * 获取某400在某广告位的各统计量
	 * @param id
	 * @param companyId
	 * @param from
	 * @param to
	 * @return
	 */
	 public AnalysisPpcAdslogDto findDataByTimeAAAC(String id,Integer companyId,String from,String to);
	 /**
	  * 获取某广告位某段时间的各统计量
	  * @param id
	  * @param tel
	  * @param from
	  * @param to
	  * @return
	  */
	 public  List<AnalysisPpcAdslogDto> findTimeDateByAdATime(String id,String tel,String from,String to);
	 /**
	  * 获取某广告位某公司的当天的各统计量
	  * @param id
	  * @param companyId
	  * @param from
	  * @param to
	  * @return
	  */
	 public AnalysisPpcAdslogDto findTimeDataByTimeAAAC(String id,Integer companyId,String from,String to);
}
