/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.persist.information;

import java.util.List;

import com.ast.ast1949.domain.information.Periodical;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.PeriodicalDTO;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface PeriodicalDAO {

	/**
	 * 创建期刊信息
	 * @param periodical:期刊信息,不能为null<br/>
	 * 		periodical.name:期刊名称,不能为null
	 * @return
	 */
	public Integer createPeriodical(Periodical periodical);

	/**
	 * 更新期刊信息
	 * @param periodical:待更新的期刊信息,不能为null<br/>
	 * 		只允许更新以下字段:(name,cycle,pdf_download,size,gmt_release,num_release,release_no,gmt_modified)
	 * @return
	 */
	public Integer updatePeriodical(Periodical periodical);

	/**
	 * 根据期刊ID删除一个期刊信息
	 * @param id
	 * @return
	 */
	public Integer deletePeriodicalById(Integer id);

	/**
	 * 通过期刊ID查找一个期刊信息
	 * @param id:期刊ID
	 * @return
	 */
	public Periodical listOnePeriodicalById(Integer id);

	/**
	 * 将全部期刊信息分页取出,不带搜索条件
	 * @param page:分页信息,不能为null
	 * @return
	 */
	public List<Periodical> pagePeriodicalWithoutSearch(PageDto page);

	/**
	 * 计算期刊数量
	 * @return
	 */
	public Integer countPagePeriodicalWithoutSearch();

	/**
	 * 更新期刊信息的zip文件位置
	 * @param id
	 * @param zipPath
	 * @return
	 */
	public Integer updatePeriodicalZipPath(Integer id, String zipPath);
     /**
      *   根据发行时间查询前几条废料商情
      * @param size 条数不能为空
      * @return List<PeriodicalDTO>
      */
	public List<PeriodicalDTO> listFrontCoverPeriodicalBySize(Integer size);

	/**
	 * 更新期刊信息浏览次数
	 * @param periodicalId:期刊ID,不能为null
	 * @param num:增加的数量
	 * @return
	 */
	public Integer updateNumViewById(Integer periodicalId, Integer num);

	/**
	 * 更新顶一下的次数
	 * @param id:期刊ID,不能为null
	 * @param num:增加的数量
	 * @return
	 */
	public Integer updateNumUpById(Integer id, Integer num);
}
