package com.ast.ast1949.service.analysis;

import com.ast.ast1949.domain.analysis.AnalysisPhoneOptimization;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPhoneOptimizationDto;
import com.ast.ast1949.dto.analysis.AnalysisSerchDto;

public interface AnalysisPhoneOptimizationService {
	
	/** 1. 方法名： createOneRecord
	 * 插入一条全新的访问记录 
	 * 以ip 和 gmt_target时间 作为 唯一性判断的标准 ： 【暂时取消该需求】
	 * 具体参数状况
	 * `phone_log_id` :0 ,
		`ip` VARCHAR(15) NOT NULL ,
		`utm_source` ：根据参数传入的值设置 ,
		`utm_term` ：根据参数传入的值设置 ,
		`utm_content` ：根据参数传入的值设置,
		`utm_campaign` ：根据参数传入的值设置,
		`is_valid` ：0 ,
		`is_first` ：0 ,判定该ip是否第一次出现，如果是，则为1；否则为0
		`gmt_target` 当前时间 格式为 ”yyyy-MM-dd“,
		`gmt_created` :当前时间 格式为 ”yyyy-MM-dd HH:mm:ss“,
		`gmt_modified` :当前时间 格式为 ”yyyy-MM-dd HH:mm:ss“,
	 */
	public Integer createOneRecord(AnalysisPhoneOptimization analysisPhoneOptimization);

	public Integer createOneRecord(String ip,String utmSource,String utmTerm,String utmContent,String utmCampaign,String pageFirst);
    /**
     * 
     * @param page 分页
     * @param analysisSerchDto 查询Dto
     * @return 分页查询结果
     */
	PageDto<AnalysisPhoneOptimizationDto> selectByAnalysisPhone(PageDto<AnalysisPhoneOptimizationDto> page,AnalysisSerchDto analysisSerchDto);
}
