package com.ast.ast1949.service.yuanliao;

import java.util.List;

import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;

public interface YuanLiaoExportInquiryService {
	
	/**
	 * 转讯盘次数
	 * @author zhujq
	 * @param yuanLiaoId
	 * @return
	 */
	public Integer countByYuanLiaoId(Integer yuanLiaoId);
	
	/**
	 * 根据原料id查出询盘关系列表
	 * @author zhujq
	 * @param id
	 * @return
	 */
	public List<YuanLiaoExportInquiry> queryByYuanLiaoId(Integer id);
	
	/**
	 * 
	 * @param yuanLiaoId
	 * @param companyId
	 * @param page
	 * @return
	 */
	public PageDto<YuanliaoDto> pageYuanLiaoExport(Integer yuanLiaoId,Integer companyId, PageDto<YuanliaoDto> page);
}
