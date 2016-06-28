package com.ast.ast1949.service.yuanliao;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.SearchDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;

/**
 * @date 2015-08-26
 * @author shiqp
 */
public interface YuanLiaoService {
	
	public Integer insertYuanliao(Yuanliao yuanliao);
	
	public PageDto<YuanliaoDto> pageYaunliao(PageDto<YuanliaoDto> page,Yuanliao yuaoliao ,YuanLiaoSearch search);
	
	public Yuanliao queryYuanliaoById(Integer id);
	
	public Integer updateYuanliao(Yuanliao yuanliao);
	
	public Map<String,String> queryYuanliaoCategory(Integer companyId);
	
	public PageDto<YuanliaoDto> pageYuanliaoListByAdmin(PageDto<YuanliaoDto> page,YuanLiaoSearch search);
	
	public List<YuanliaoDto> queryYuanLiaoByCondition(YuanLiaoSearch search,Integer size);
	
	public List<Yuanliao> queryYuanliaoBYCompanyId(Integer companyId);
	
	/**
	 * 搜索引擎方法一
	 * 存在轮回规则的搜索引擎方法 
	 * @param search
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageDto<YuanliaoDto> pageSearchYuanliaoList(YuanLiaoSearch search,PageDto<YuanliaoDto> page) throws Exception;
	
	public Integer countYuanliaoList(Yuanliao yuanliao,YuanLiaoSearch search);
	/**
	 * 管理员刷新
	 * @param id
	 * @return
	 */
	
	/**
	 * searchDto 根据搜索条件检索供求
	 * @param id
	 * @return
	 */
	public PageDto<YuanliaoDto> queryYuanliaoSearchDto(SearchDto search,PageDto<YuanliaoDto> page);
	
	public Integer updateRefreshTime(Integer id);

	/**
	 * 搜索引擎方法二
	 * 获取指定要求的数据，没有轮回规则，暂时使用与原料首页
	 * @param search
	 * @param page
	 * @return
	 */
	public PageDto<YuanliaoDto> searchByEngine(YuanLiaoSearch search, PageDto<YuanliaoDto> page);
	/**
	 * 搜索数据量
	 * @param search
	 * @return
	 */
	public Integer queryYuanliaoSearchDtoCount(SearchDto search);
	/**
	 * 搜索改公司最新原料供求　带图片
	 * @param companyId
	 * @return
	 */
	public List<YuanliaoDto> queryYuanliaoBYCompanyIdPic(Integer companyId);
	/**
	 * 检索最新原料供求 size 条数
	 */
	public List<Yuanliao> queryNewSize(Integer size);
}
