package com.ast.ast1949.service.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisProductTypeCode;
import com.ast.ast1949.dto.analysis.AnalysisProductTypeCodeDto;
import com.ast.ast1949.persist.analysis.AnalysisProductTypeCodeDao;
import com.ast.ast1949.service.analysis.AnalysisProductTypeCodeService;

/**
@author	kongsj
@date	2012-9-11
 */
@Component("analysisProductTypeCodeService")
public class AnalysisProductTypeCodeServiceImpl implements AnalysisProductTypeCodeService{
	@Resource
	private AnalysisProductTypeCodeDao analysisProductTypeCodeDao;
	@Override
	public List<AnalysisProductTypeCode> queryAnalysisProductTypeCode(
			String account, String start, String end) {
		if("汇总".equals(account)){
			account = "";
		}
		return analysisProductTypeCodeDao.queryAnalysisProductTypeCode(account, start, end);
	}
	
	public List<AnalysisProductTypeCodeDto> domainToDto(AnalysisProductTypeCode obj) {
		List<AnalysisProductTypeCodeDto> list = new ArrayList<AnalysisProductTypeCodeDto>();
		AnalysisProductTypeCodeDto dtoY = new AnalysisProductTypeCodeDto();
		AnalysisProductTypeCodeDto dtoN = new AnalysisProductTypeCodeDto();
		AnalysisProductTypeCodeDto dtoZ = new AnalysisProductTypeCodeDto();
		AnalysisProductTypeCodeDto dtoF = new AnalysisProductTypeCodeDto();
		
		dtoY.setAnalysisType("通过");
		dtoY.setBoli(obj.getBoliY()==null?0:obj.getBoliY());
		dtoY.setDianzidianqi(obj.getDianzidianqiY()==null?0:obj.getDianzidianqiY());
		dtoY.setErshoushebei(obj.getErshoushebeiY()==null?0:obj.getErshoushebeiY());
		dtoY.setFangzhipin(obj.getFangzhipinY()==null?0:obj.getFangzhipinY());
		dtoY.setFuwu(obj.getFuwuY()==null?0:obj.getFuwuY());
		dtoY.setJinshu(obj.getJinshuY()==null?0:obj.getJinshuY());
		dtoY.setXiangjiao(obj.getXiangjiaoY()==null?0:obj.getXiangjiaoY());
		dtoY.setQitafeiliao(obj.getQitafeiliaoY()==null?0:obj.getQitafeiliaoY());
		dtoY.setSuliao(obj.getSuliaoY()==null?0:obj.getSuliaoY());
		dtoY.setZhi(obj.getZhiY()==null?0:obj.getZhiY());
		dtoY.setPige(obj.getPigeY()==null?0:obj.getPigeY());
		dtoY.setLuntai(obj.getLuntaiY()==null?0:obj.getLuntaiY());
		
		dtoN.setAnalysisType("不通过");
		dtoN.setBoli(obj.getBoliN()==null?0:obj.getBoliN());
		dtoN.setDianzidianqi(obj.getDianzidianqiN()==null?0:obj.getDianzidianqiN());
		dtoN.setErshoushebei(obj.getErshoushebeiN()==null?0:obj.getErshoushebeiN());
		dtoN.setFangzhipin(obj.getFangzhipinN()==null?0:obj.getFangzhipinN());
		dtoN.setFuwu(obj.getFuwuN()==null?0:obj.getFuwuN());
		dtoN.setJinshu(obj.getJinshuN()==null?0:obj.getJinshuN());
		dtoN.setXiangjiao(obj.getXiangjiaoN()==null?0:obj.getXiangjiaoN());
		dtoN.setQitafeiliao(obj.getQitafeiliaoN()==null?0:obj.getQitafeiliaoN());
		dtoN.setSuliao(obj.getSuliaoN()==null?0:obj.getSuliaoN());
		dtoN.setZhi(obj.getZhiN()==null?0:obj.getZhiN());
		dtoN.setPige(obj.getPigeN()==null?0:obj.getPigeN());
		dtoN.setLuntai(obj.getLuntaiN()==null?0:obj.getLuntaiN());
		
		dtoZ.setAnalysisType("总数");
		dtoZ.setBoli(dtoY.getBoli()+dtoN.getBoli());
		dtoZ.setDianzidianqi(dtoY.getDianzidianqi()+dtoN.getDianzidianqi());
		dtoZ.setErshoushebei(dtoY.getErshoushebei()+dtoN.getErshoushebei());
		dtoZ.setFangzhipin(dtoY.getFangzhipin()+dtoN.getFangzhipin());
		dtoZ.setFuwu(dtoY.getFuwu()+dtoN.getFuwu());
		dtoZ.setJinshu(dtoY.getJinshu()+dtoN.getJinshu());
		dtoZ.setXiangjiao(dtoY.getXiangjiao()+dtoN.getXiangjiao());
		dtoZ.setQitafeiliao(dtoY.getQitafeiliao()+dtoN.getQitafeiliao());
		dtoZ.setSuliao(dtoY.getSuliao()+dtoN.getSuliao());
		dtoZ.setZhi(dtoY.getZhi()+dtoN.getZhi());
		dtoZ.setPige(dtoY.getPige()+dtoN.getPige());
		dtoZ.setLuntai(dtoY.getLuntai()+dtoN.getLuntai());
		
		dtoF.setAnalysisType("通过率");
		dtoF.setBoli(toBaiFenBi(Float.valueOf(dtoY.getBoli()), Float.valueOf(dtoZ.getBoli())));
		dtoF.setDianzidianqi(toBaiFenBi(Float.valueOf(dtoY.getDianzidianqi()), Float.valueOf(dtoZ.getDianzidianqi())));
		dtoF.setErshoushebei(toBaiFenBi(Float.valueOf(dtoY.getErshoushebei()), Float.valueOf(dtoZ.getErshoushebei())));
		dtoF.setFangzhipin(toBaiFenBi(Float.valueOf(dtoY.getFangzhipin()), Float.valueOf(dtoZ.getFangzhipin())));
		dtoF.setFuwu(toBaiFenBi(Float.valueOf(dtoY.getFuwu()), Float.valueOf(dtoZ.getFuwu())));
		dtoF.setJinshu(toBaiFenBi(Float.valueOf(dtoY.getJinshu()), Float.valueOf(dtoZ.getJinshu())));
		dtoF.setXiangjiao(toBaiFenBi(Float.valueOf(dtoY.getXiangjiao()), Float.valueOf(dtoZ.getXiangjiao())));
		dtoF.setQitafeiliao(toBaiFenBi(Float.valueOf(dtoY.getQitafeiliao()), Float.valueOf(dtoZ.getQitafeiliao())));
		dtoF.setSuliao(toBaiFenBi(Float.valueOf(dtoY.getSuliao()), Float.valueOf(dtoZ.getSuliao())));
		dtoF.setZhi(toBaiFenBi(Float.valueOf(dtoY.getZhi()), Float.valueOf(dtoZ.getZhi())));
		dtoF.setPige(toBaiFenBi(Float.valueOf(dtoY.getPige()), Float.valueOf(dtoZ.getPige())));
		dtoF.setLuntai(toBaiFenBi(Float.valueOf(dtoY.getLuntai()), Float.valueOf(dtoZ.getLuntai())));
		list.add(dtoY);
		list.add(dtoN);
		list.add(dtoZ);
		list.add(dtoF);
		return list;
	}
	
	private Integer toBaiFenBi(Float a,Float b) {
		if(b==0){
			return 0;
		}
		Float p = a/b;
		Float pp = p*100;
		String pa = pp.toString();
		pa = pa.substring(0, pa.indexOf("."));
		return Integer.valueOf(pa);
	}
}
