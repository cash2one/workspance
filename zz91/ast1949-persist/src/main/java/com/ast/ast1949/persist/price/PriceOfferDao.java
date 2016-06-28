package com.ast.ast1949.persist.price;

import java.util.List;

import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.dto.PageDto;

/**
 * @author shiqp
 * @date 2015-05-04
 */
public interface PriceOfferDao {
	public Integer insertPriceOffer(PriceOffer offer);
    /**
     * 获取该用户的所有自主报价
     * @param page
     * @param companyId
     * @return
     */
	public List<PriceOffer> queryOfferByCompanyId(PageDto<PriceOffer> page,Integer companyId, String keywords);
	/**
	 * 统计该用户的自主报价数
	 * @param companyId
	 * @return
	 */
    public Integer countOfferByCompanyId(Integer companyId, String keywords);
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
     * 更新自主报价的删除状态
     * @param id
     * @param isDel
     * @return
     */
    public Integer updateIsDelByid(Integer id,Integer isDel);
    
    /**
     * 后台企业自主报价
     * @param page
     * @param priceOffer
     * @param from
     * @param to
     * @return
     */
    public List<PriceOffer> listOfferByCondition(PageDto<PriceOffer> page,PriceOffer priceOffer,String from,String to,String menberShip);
    /**
     * 后台企业自主报价总条数
     * @param priceOffer
     * @param from
     * @param to
     * @return
     */
    public Integer countOfferByCondition(PriceOffer priceOffer,String from,String to,String menberShip);
    /**
     * 更新审核信息
     * @param id
     * @param checkStatus
     * @param checkPerson
     * @return
     */
    public Integer updateCheckInfo(Integer id,Integer checkStatus,String checkPerson,String checkReason,Integer isDel);
}
