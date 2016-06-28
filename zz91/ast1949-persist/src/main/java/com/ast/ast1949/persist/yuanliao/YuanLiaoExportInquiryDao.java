package com.ast.ast1949.persist.yuanliao;

import java.util.List;

import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;

public interface YuanLiaoExportInquiryDao {
	public Integer countByYuanLiaoId(Integer yuanLiaoId);

	public List<YuanLiaoExportInquiry> queryByYuanLiaoId(Integer id);

	public List<YuanLiaoExportInquiry> queryList(Integer yuanLiaoId,
			Integer companyId, PageDto<YuanliaoDto> page);

	public Integer countList(Integer yuanLiaoId, Integer companyId);
}
