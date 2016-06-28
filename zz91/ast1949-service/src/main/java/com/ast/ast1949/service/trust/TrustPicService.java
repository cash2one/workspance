/**
 * @author shiqp
 * @date 2015-07-25
 */
package com.ast.ast1949.service.trust;

import com.ast.ast1949.domain.trust.TrustPic;
import com.ast.ast1949.dto.PageDto;

public interface TrustPicService {
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
	 * 某交易未删除的交易凭证信息
	 * @param page
	 * @param tradeId
	 * @return
	 */
	public PageDto<TrustPic> pageTradePicInfo(PageDto<TrustPic> page,Integer tradeId);
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
	 * 获取图片信息，根据图片id
	 * @param id
	 * @return
	 */
	public TrustPic queryById(Integer id);

}
