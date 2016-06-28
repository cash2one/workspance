package com.kl91.persist.company;

import com.kl91.domain.company.UploadPic;


public interface UploadPicDao {
 
	/**
	 *  
	 */
	public Integer insert(UploadPic uploadPic);
	public Integer update(UploadPic uploadPic);
	public Integer deleteById(Integer id);
	public UploadPic queryById(Integer id);
	public Integer updateTargetId(Integer id, Integer targetId,Integer typeType);
	public UploadPic queryByTargetId(Integer targetId);
}
 
