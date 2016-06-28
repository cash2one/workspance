package com.ast.ast1949.service.price;
/**
 * @author shiqp
 * @date 2015-05-04
 */
import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.dto.PageDto;
public interface PriceOfferService {
	public Integer insertPriceOffer(PriceOffer offer);

	/**
	 * 获取该用户的所有自主报价
	 * 
	 * @param page
	 * @param companyId
	 * @return
	 */
	public PageDto<PriceOffer> queryOfferByCompanyId(PageDto<PriceOffer> page, Integer companyId, String keywords);
	/**
	 * 根据id获取自主报价信息
	 * @param id
	 * @return
	 */
	public PriceOffer queryOfferById(Integer id);
	 /**
     * 更新自主报价的标题、类别、excel表格链接，介绍以及更新时间
     * @param offer
     * @return
     */
    public Integer updateOfferById(PriceOffer offer);
    /**
     * 更新下载次数
     * @param id
     * @param downloadNum
     */
    public void updateDownloadNumById(Integer id,Integer downloadNum);
    /**
     * 更新删除状态
     * @param id
     * @param isDel
     * @return
     */
    public Integer updateIsDelByid(Integer id,Integer isDel);
    /**
     * 关注的自主报价列表
     * @param page
     * @param companyId
     * @param favoriteTypeCode
     * @return
     */
    public PageDto<PriceOffer> queryOfferByCompanyIdAndType(PageDto<PriceOffer> page,Integer companyId,String favoriteTypeCode,String keywords);
    
    /**
     * 后台自主报价列表信息
     * @param page
     * @param priceOffer
     * @param from
     * @param to
     * @return
     */
    public PageDto<PriceOffer> pageOfferByCondition(PageDto<PriceOffer> page,PriceOffer priceOffer,String from,String to,String menberShip);
    /**
     * 更新审核信息
     * @param id
     * @param checkStatus
     * @param checkPerson
     * @return
     */
    public Integer updateCheckInfo(Integer id,Integer checkStatus,String checkPerson,String checkReason,Integer isDel);
}
