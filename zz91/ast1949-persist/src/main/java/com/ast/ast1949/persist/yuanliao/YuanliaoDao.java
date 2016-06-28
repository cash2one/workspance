package com.ast.ast1949.persist.yuanliao;

import java.util.List;

import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.SearchDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;

/**
 * @date 2015-08-22
 * @author shiqp
 */
public interface YuanliaoDao {
	
	public Integer insertYuanliao(Yuanliao yuanliao);
	
	public Integer updateYuanliao(Yuanliao yuanliao);
	
	public Yuanliao queryYuanliaoById(Integer id);
	
	public List<Yuanliao> queryYuanliaoList(Yuanliao yuanliao,PageDto<YuanliaoDto> page,YuanLiaoSearch search);
	
	public Integer countYuanliaoList(Yuanliao yuanliao,YuanLiaoSearch search);
	
	public List<String> queryYuanliaoForCategory(Integer companyId);
	
	public List<Yuanliao> queryYuanliaoListByAdmin(PageDto<YuanliaoDto> page,YuanLiaoSearch search);
	
	public Integer countYunaliaoListByAdmin(YuanLiaoSearch search);
	
	public List<Yuanliao> queryYuanLiaoByCondition(YuanLiaoSearch search,Integer size);
	
	public List<Yuanliao> queryYuanliaoBYCompanyId(Integer companyId);
	/**
	 * 管理员刷新
	 * @param id
	 * @param expireTime
	 * @return
	 */
	public Integer updateRefreshTime(Integer id, String expireTime);

	public List<Yuanliao> queryYuanliaoSearchDto(SearchDto search,
			PageDto<YuanliaoDto> page);

	public Integer queryYuanliaoSearchDtoCount(SearchDto search);

	public List<Yuanliao> queryNewSize(Integer size);
}
