package com.ast.ast1949.service.yuanliao.impl;
/**
 * @date 2015-08-27
 * @author shiqp
 */
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.persist.yuanliao.YuanliaoPicDao;
import com.ast.ast1949.service.yuanliao.YuanliaoPicService;
@Component("yuanliaoPicService")
public class YuanliaoPicServiceImpl implements YuanliaoPicService {
	@Resource 
	private YuanliaoPicDao yuanliaoPicDao;

	@Override
	public Integer insertYuanliaoPic(YuanliaoPic yuanliaoPic) {
		return yuanliaoPicDao.insertYuanliaoPic(yuanliaoPic);
	}

	@Override
	public List<YuanliaoPic> queryYuanliaoPicByYuanliaoId(YuanliaoPic yuanliaoPic,Integer size) {
		return yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(yuanliaoPic, size);
	}

	@Override
	public Integer updateYuanliaoPic(YuanliaoPic yuanliaoPic) {
		return yuanliaoPicDao.updateYuanliaoPic(yuanliaoPic);
	}

}
