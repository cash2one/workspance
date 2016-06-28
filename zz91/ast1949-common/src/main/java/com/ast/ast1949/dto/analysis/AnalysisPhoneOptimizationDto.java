package com.ast.ast1949.dto.analysis;

import com.ast.ast1949.domain.analysis.AnalysisPhoneOptimization;
import com.ast.ast1949.domain.phone.PhoneLog;

public class AnalysisPhoneOptimizationDto {
	private AnalysisPhoneOptimization analysisPhoneOptimiza;
	private PhoneLog phonelog;
	
	private String area;
	
	public AnalysisPhoneOptimizationDto() {
	}
	public AnalysisPhoneOptimizationDto(
			AnalysisPhoneOptimization analysisPhoneOptimization) {
	}
	
	public AnalysisPhoneOptimizationDto(
			AnalysisPhoneOptimization analysisPhoneOptimiza, PhoneLog phonelog) {
		this.analysisPhoneOptimiza = analysisPhoneOptimiza;
		this.phonelog = phonelog;
	}
	public AnalysisPhoneOptimization getAnalysisPhoneOptimiza() {
		return analysisPhoneOptimiza;
	}
	public void setAnalysisPhoneOptimiza(
			AnalysisPhoneOptimization analysisPhoneOptimiza) {
		this.analysisPhoneOptimiza = analysisPhoneOptimiza;
	}
	public PhoneLog getPhonelog() {
		return phonelog;
	}
	public void setPhonelog(PhoneLog phonelog) {
		this.phonelog = phonelog;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

}
