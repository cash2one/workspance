package com.kl91.service.company;

import com.kl91.domain.company.UploadPic;


public interface UploadPicService {
	public final static String MODEL_KL91="kl91";
	public final static String THUMB_PREFIX="min.";
	public static final Integer TARGETTYPE_OF_COMPANY=1;
	public static final Integer TARGETTYPE_OF_PRODUCTS=2;
	public static final Integer TARGETTYPE_OF_CREDIT=3;
	
	/**
	 * 创建一张图片信息
	 * 上传供求图片、公司图片
	 */
	public Integer createUploadPic(UploadPic uploadPic);
	/**
	 * 发布供求、上传公司图片
	 * 更新targetId和targetType
	 */
	public Integer editUploadPicById(Integer id, Integer targetId, Integer targetType);
	/**
	 * 删除图片
	 * 将关联的targetId更新为0.
	 * 待任务系统统一一并删除。
	 */
	public Integer deleteById(Integer id);
	
	public UploadPic queryById(Integer id);
	
	public UploadPic queryUploadPicByTargetId(Integer targetId);
}
 
