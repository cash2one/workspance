package com.ast.ast1949.persist.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisPhoneOptimization;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPhoneOptimizationDto;
import com.ast.ast1949.dto.analysis.AnalysisSerchDto;

public interface AnalysisPhoneOptimizationDao {
    public Integer createOneRecord(AnalysisPhoneOptimization analysisPhoneOptimization);
    public Integer selectOneRecord(AnalysisPhoneOptimization analysisPhoneOptimization);
    public Integer selectIp(AnalysisPhoneOptimization analysisPhoneOptimization);
	public List<AnalysisPhoneOptimization> selectByAnalysisPhone(
			AnalysisSerchDto analysisSerchDto,
			PageDto<AnalysisPhoneOptimizationDto> page);
	public PhoneLog selectSize(Integer id);
	public Integer selectByAnalysisPhoneSzie(AnalysisSerchDto analysisSerchDto);
}
