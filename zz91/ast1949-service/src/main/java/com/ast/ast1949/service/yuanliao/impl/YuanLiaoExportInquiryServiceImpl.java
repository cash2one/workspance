package com.ast.ast1949.service.yuanliao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.InquiryDao;
import com.ast.ast1949.persist.yuanliao.YuanLiaoExportInquiryDao;
import com.ast.ast1949.persist.yuanliao.YuanliaoDao;
import com.ast.ast1949.service.yuanliao.YuanLiaoExportInquiryService;
import com.zz91.util.datetime.DateUtil;

@Component("yuanLiaoExportInquiryService")
public class YuanLiaoExportInquiryServiceImpl implements
		YuanLiaoExportInquiryService {
	@Resource
	private YuanLiaoExportInquiryDao yuanLiaoExportInquiryDao;
	@Resource
	private YuanliaoDao yuanliaoDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private InquiryDao inquiryDao;
	
	@Override
	public Integer countByYuanLiaoId(Integer yuanLiaoId) {
		return yuanLiaoExportInquiryDao.countByYuanLiaoId(yuanLiaoId);
	}
	
	@Override
	public List<YuanLiaoExportInquiry> queryByYuanLiaoId(Integer id){
		return yuanLiaoExportInquiryDao.queryByYuanLiaoId(id);
	}
	
	@Override
	public PageDto<YuanliaoDto> pageYuanLiaoExport(Integer yuanLiaoId,Integer companyId, PageDto<YuanliaoDto> page){
		List<YuanLiaoExportInquiry> list = yuanLiaoExportInquiryDao.queryList(yuanLiaoId,companyId,page);
		List<YuanliaoDto> nlist = new ArrayList<YuanliaoDto>();
		String fromTitle = "";
//		Yuanliao yuanliaoDO = new Yuanliao();
		for (YuanLiaoExportInquiry obj :list) {
			YuanliaoDto dto = new YuanliaoDto();
			Yuanliao yuanliao =  yuanliaoDao.queryYuanliaoById(obj.getTargetId());
			Company company =  companyDAO.queryCompanyById(obj.getToCompanyId());
			
			// 设置该供求 title
			if(yuanLiaoId!=null){
				Yuanliao yuanliaoDO1 = yuanliaoDao.queryYuanliaoById(yuanLiaoId);
				fromTitle = yuanliaoDO1.getTitle();
			
				dto.setFromTitle(fromTitle);
				Inquiry inquiry =  inquiryDao.queryExportInquiryFromProduct(yuanliaoDO1.getAccount(), obj.getTargetId());
				if (inquiry!=null && inquiry.getContent()!=null) {
					yuanliao.setDescription(inquiry.getContent());
				}
			}
			
			dto.setYuanliao(yuanliao);
			dto.setCompany(company);
			if(obj.getGmtCreated()!=null){
				dto.setGmtInquiryStr(DateUtil.toString(obj.getGmtCreated(), "yyyy-MM-dd"));
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(yuanLiaoExportInquiryDao.countList(yuanLiaoId, companyId));
		
		return page;
	}
}
