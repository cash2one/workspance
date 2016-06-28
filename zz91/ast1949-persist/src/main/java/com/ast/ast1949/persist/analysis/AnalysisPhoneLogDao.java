package com.ast.ast1949.persist.analysis;

public interface AnalysisPhoneLogDao {

	public Integer queryTelCByCompanyIdATAA(String adposition,Integer companyId, String from, String to);

	public Integer queryTelCByCompanyIdAT(Integer companyId, String from,String to);

	public Integer queryTelCByAdpositionAT(String adposition, String from,String to);

}
