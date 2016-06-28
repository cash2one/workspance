package com.ast.ast1949.service.analysis.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisPhoneOptimization;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPhoneOptimizationDto;
import com.ast.ast1949.dto.analysis.AnalysisSerchDto;
import com.ast.ast1949.persist.analysis.AnalysisPhoneOptimizationDao;
import com.ast.ast1949.service.analysis.AnalysisPhoneOptimizationService;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.lang.StringUtils;

@Component("AnalysisPhoneOptimizationService")
public class AnalysisPhoneOptimizationServiceimpl implements AnalysisPhoneOptimizationService{
   @Resource
   private AnalysisPhoneOptimizationDao analysisPhoneOptimizationDao;
	@Override
	public Integer createOneRecord(
			AnalysisPhoneOptimization analysisPhoneOptimization) {
		analysisPhoneOptimization.setPhoneLogId(0);
		analysisPhoneOptimization.setIsValid(0);
//		Integer isfirst=analysisPhoneOptimizationDao.selectOneRecord(analysisPhoneOptimization);
//		if(isfirst.intValue()>0){
//			 return 0;
//		}
		analysisPhoneOptimization.setIsFirst((int)analysisPhoneOptimizationDao.selectIp(analysisPhoneOptimization));
		return analysisPhoneOptimizationDao.createOneRecord(analysisPhoneOptimization);
	}
	@Override
	public Integer createOneRecord(String ip, String utmSource, String utmTerm, String utmContent, String utmCampaign,String pageFirst) {
		AnalysisPhoneOptimization obj = new AnalysisPhoneOptimization();
		if (StringUtils.isEmpty(ip)) {
			return 0;
		}
		obj.setIp(ip);
		
		// 获取地址
		final String area[] =new String[]{""};
		String sql = "SELECT area FROM  `ip_area` WHERE inet_aton(ip) <=  inet_aton('"+ip+"') AND inet_aton(ip2) >=  inet_aton('"+ip+"') order by id desc limit 1 ";
		DBUtils.select("zzother", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					area[0] = rs.getString(1);
				}
			}
		});
		obj.setArea(area[0]);
		obj.setUtmSource(utmSource);
		obj.setUtmTerm(utmTerm);
		obj.setUtmCampaign(utmCampaign);
		obj.setUtmContent(utmContent);
		obj.setPageFirst(pageFirst);
		return createOneRecord(obj);
	}

	@Override
	public PageDto<AnalysisPhoneOptimizationDto> selectByAnalysisPhone(
			PageDto<AnalysisPhoneOptimizationDto> page,
			AnalysisSerchDto analysisSerchDto) {
		page.setSort("id");
		page.setDir("desc");
		List<AnalysisPhoneOptimizationDto> list = new ArrayList<AnalysisPhoneOptimizationDto>();
		List<AnalysisPhoneOptimization> analysisPhoneOptimization=(List<AnalysisPhoneOptimization>) analysisPhoneOptimizationDao.selectByAnalysisPhone(analysisSerchDto,page);
		for(int i=0;i<analysisPhoneOptimization.size();i++){
			PhoneLog phone=analysisPhoneOptimizationDao.selectSize(analysisPhoneOptimization.get(i).getPhoneLogId());
			if (phone==null) {
				phone = new PhoneLog();
			}
			list.add(new AnalysisPhoneOptimizationDto(analysisPhoneOptimization.get(i),phone));
			
		}
		Integer totalRecords=analysisPhoneOptimizationDao.selectByAnalysisPhoneSzie(analysisSerchDto);
		page.setTotalRecords(totalRecords);
		page.setRecords(list);		
		return page;
	}

}
