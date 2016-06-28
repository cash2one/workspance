package com.kl91.service.company;

import java.util.List;

import com.kl91.domain.company.CreditFile;
import com.kl91.domain.dto.company.CreditFileSearchDto;

public interface CreditFileService {

 
	/**
	 * 逻辑：
	 * 1.写入证书信息
	 * 2.得到ID后更新图片的targetId
	 * picid: 必填 为上传图片成功后对应id
	 */
	public Integer createByUser(CreditFile createFile, Integer picid);
	/**
	 * 1、判断上传证书图片地址是否更新
	 * 1.1、证书图片更新的情况下：
	 * 1.1.1、更新原图片表该证书targetId为null
	 * 1.2、证书图片没有更改的情况下，不更新图片表
	 * 2、更新证书内容
	 * nfileFlag: 标记 着图片地址是否有改动。默认为false：未改动。
	 */
	public Integer editFile(CreditFile creditFile, boolean nfileFlag);
	/**
	 * 删除诚信档案
	 * 用户在生意管家中使用,删除自己添加的荣誉证书
	 */
	public Integer deleteById(Integer id);
	/**
	 * 根据证书ID搜索诚信档案
	 * 使用于更改荣誉证书
	 */
	public CreditFile queryById(Integer id);
	/**
	 * 搜索诚信档案列表
	 * 客户在荣誉证书页面,
	 * 浏览自己添加的所有荣誉证，
	 * 目前荣誉证书最多可添加5张
	 */
	public List<CreditFile> queryFile(CreditFileSearchDto searchDto);
}
 
