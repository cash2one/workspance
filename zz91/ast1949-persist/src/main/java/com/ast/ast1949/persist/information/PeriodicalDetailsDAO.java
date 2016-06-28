/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.persist.information;

import java.util.List;

import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface PeriodicalDetailsDAO {

	/**
	 * 子页类型属于:封面
	 */
	public final static int PAGE_TYPE_FRONT_COVER = 0;

	/**
	 * 子页类型属于:目录
	 */
	public final static int PAGE_TYPE_MENU = 1;

	/**
	 * 子页类型属于:正文
	 */
	public final static int PAGE_TYPE_BODY = 2;

	/**
	 * 子页类型属于:封底
	 */
	public final static int PAGE_TYPE_BACK_COVER = 3;

	/**
	 * 批量保存期刊子页信息
	 * @param details:待保存的子页信息,不能为null
	 * @return
	 */
	public Integer batchInsertPeriodicalDetails(List<PeriodicalDetails> details);

	/**
	 * 列出预览用的期刊子页列表,取出所有子页信息,不分页
	 * @param periodicalId:期刊ID,不能为null
	 * @return
	 */
	public List<PeriodicalDetails> listPreviewDetailsByPeriodicalId(Integer periodicalId);

	/**
	 * 分页取出期刊子页列表
	 * @param periodicalId:期刊ID,不能为null
	 * @param page:分页信息,不能为null
	 * @return
	 */
	public List<PeriodicalDetails> pageDetailsByPeriodicalId(Integer periodicalId, PageDto page);
    /**
     *   根据periodicalId,type查询期刊信息
     * @param periodicalId  期刊Id  不能为空
     * @param type  期刊类别 不能为空
     * @return  List<PeriodicalDetails>
     *
     */
	public List<PeriodicalDetails> pageDetailsByPeriodicalIdAndType(Integer periodicalId,Integer type);
	/**
	 * 计算期刊子页总数
	 * @param periodicalId
	 * @return
	 */
	public Integer countPageDetailsByPeriodicalId(Integer periodicalId);

	/**
	 * 更新期刊子页的基本信息
	 * @param details:待更新的信息,不能为null<br/>
	 * 		只更新以下字段:name,orders,gmt_modified
	 * @return
	 */
	public Integer updateBaseDetails(PeriodicalDetails details);

	/**
	 * 更新期刊页面的类型
	 * @param id:期刊ID,不能为null
	 * @param pageType:期待更新的类型,该类型可以是以下几种
	 * 		{@link com.ast.ast1949.persist.information.PeriodicalDetailsDAO#PAGE_TYPE_FRONT_COVER}
	 * 		{@link com.ast.ast1949.persist.information.PeriodicalDetailsDAO#PAGE_TYPE_MENU}
	 * 		{@link com.ast.ast1949.persist.information.PeriodicalDetailsDAO#PAGE_TYPE_BODY}
	 * 		{@link com.ast.ast1949.persist.information.PeriodicalDetailsDAO#PAGE_TYPE_BACK_COVER}
	 * @return
	 */
	public Integer updatePageType(Integer id, Integer pageType);

	/**
	 * 批量删除一部分期刊子页
	 * @param detailsIdArray:待删除的子页id数组,不能为null
	 * @return
	 */
	public Integer deleteDetails(Integer[] detailsIdArray);

	/**
	 * 删除期刊的所有子页信息
	 * @param periodicalId:待删除的期刊ID,不能为null
	 * @return
	 */
	public Integer deleteDetailsByPeriodicalId(Integer periodicalId);
	/**
	 *   根据Id查询周刊子页信息
	 * @param id  不能为空
	 * @return PeriodicalDetails
	 */
	public PeriodicalDetails selectDetailsById(Integer id);

}
