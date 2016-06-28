/**
 * @author shiqp
 * @date 2015-07-22
 */
package com.ast.ast1949.persist.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustPic;
import com.ast.ast1949.dto.PageDto;

public interface TrustPicDao {
	/**
	 * 创建一条交易凭证的记录
	 * @param trustPic
	 * @return
	 */
	public Integer createTradePic(TrustPic trustPic);
	/**
	 * 修改图片信息
	 * @param trustPic
	 * @return
	 */
	public Integer updateTradeInfo(TrustPic trustPic);
	/**
	 * 某个交易未删除交易凭证
	 * @param page
	 * @param tradeId
	 * @return
	 */
	public List<TrustPic> querypicList(PageDto<TrustPic> page,Integer tradeId);
	/**
	 * 某个交易的未删除的交易凭证数
	 * @param tradeId
	 * @return
	 */
	public Integer countpicList(Integer tradeId);
	/**
	 * 更新凭证的交易id
	 * @param tradeId
	 * @param picAddress
	 * @return
	 */
	public Integer updateTradeIdByPicAddress(Integer tradeId, String picAddress);
	/**
	 * 获取置顶或最新上传的图片
	 * @param tradeId
	 * @return
	 */
	public TrustPic queryOnePic(Integer tradeId);
	
	/**
	 * 根据id获取数据对象
	 */
	public TrustPic queryById(Integer id);

}
