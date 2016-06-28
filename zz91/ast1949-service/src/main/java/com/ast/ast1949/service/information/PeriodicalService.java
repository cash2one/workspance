/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.service.information;

import java.io.IOException;
import java.util.List;

import com.ast.ast1949.domain.information.Periodical;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.PeriodicalDTO;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface PeriodicalService {

	final static int DEFAULT_NUM_ADD = 1;

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
	 * 批量删除期刊的全部子页面,但不删除期刊信息,只是把期刊页面清空
	 * @param periodicalIdsArray:期刊ID数字
	 * @return
	 */
	public Integer batchDeletePeriodicalOnlyDetails(Integer[] periodicalIdsArray);

	/**
	 * 批量删除期刊以及期刊对应的子页面.
	 * @param periodicalIdsArray:周刊ID编号数组,一次删除多条记录
	 * @return
	 */
	public Integer batchDeletePeriodical(Integer[] periodicalIdsArray);

	/**
	 * 将期刊的所有子页(即期刊图片)打包,并返回下载地址
	 * @param periodicalId
	 * @return
	 */
	public String zipPeriodicalDetails(Integer periodicalId);

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
	public PageDto pagePeriodicalWithoutSearch(PageDto page);

	/**
	 * 解压上传的期刊子页zip包,并将所有的页面信息逐条写入到数据库,<br/>
	 * 保存到期刊子页的子页信息中,页面类型默认为: {@link com.ast.ast1949.persist.information.PeriodicalDetailsDAO#PAGE_TYPE_BODY }
	 * @param path:文件的路径,不能为null
	 * @param periodicalId:期刊ID,不能为null
	 * @return
	 */
	public Integer unzipUploadedDetails(String path,Integer periodicalId) throws IOException;
    /**
     *   根据发行时间查询前几条废料商情
     * @param size 条数不能为空
     * @return List<PeriodicalDTO>
     */
	public List<PeriodicalDTO> listFrontCoverPeriodicalBySize(Integer size);

	/**
	 * 更新期刊信息浏览次数,默认增加{@link #DEFAULT_NUM_ADD}
	 * @param periodicalId:期刊ID,不能为null
	 * @return
	 */
	public Integer updateNumViewById(Integer periodicalId);

	/**
	 * 更新顶一下的次数,默认增加{@link #DEFAULT_NUM_ADD}
	 * @param id:期刊ID,不能为null
	 * @return
	 */
	public Integer updateNumUpById(Integer id);


}
