package com.ast.ast1949.persist.analysis;

import java.util.List;


public interface AnalysisPpcAdslogDao {
   /**
    * 某段时间某广告位某公司的展现量
    */
	public Integer queryShowCByCompanyIdATAA(String adposition,Integer companyId,String from,String to);
   /**
    * 某段时间某广告位的展现量
    */
	public Integer queryShowCByAdpositionAT(String adposition,String from,String to);
	/**
	 * 某段时间某公司的展现量
	 */
	public Integer queryShowCByCompanyIdAT(Integer companyId,String from,String to);
	/**
	 * 某段时间某广告位某公司的点击量
	 */
	public Integer queryCheckCByCompanyIdATAA(String adposition,Integer companyId,String from,String to);
	/**
	 * 某段时间某广告位的点击量
	 */
	public Integer queryCheckCByAdpositionAT(String adposition,String from,String to);
	/**
	 * 某段时间某广告位某公司的点击量
	 */
	public Integer queryCheckCByCompanyIdAT(Integer companyId,String from,String to);
	/**
	 * 根据广告位id搜索出该广告位下的所有公司id
	 */
	public List<Integer> queryCompanyIdById(Integer id);
}
