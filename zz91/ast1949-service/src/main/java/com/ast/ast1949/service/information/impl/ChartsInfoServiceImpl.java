/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-31 by Rolyer.
 */
package com.ast.ast1949.service.information.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ChartsInfoDO;
import com.ast.ast1949.dto.information.ChartsInfoDTO;
import com.ast.ast1949.persist.information.ChartsInfoDAO;
import com.ast.ast1949.service.information.ChartsInfoService;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("chartsInfoService")
public class ChartsInfoServiceImpl implements ChartsInfoService {

	@Autowired
	private ChartsInfoDAO chartsInfoDAO;

	public Integer deleteChartsInfoById(Integer id) {
		return chartsInfoDAO.deleteChartsInfoById(id);
	}

	public Integer insertChartsInfo(ChartsInfoDO chartsInfo) {
		return chartsInfoDAO.insertChartsInfo(chartsInfo);
	}

	public ChartsInfoDO queryChartsInfoById(Integer id) {
		return chartsInfoDAO.queryChartsInfoById(id);
	}

	public Integer updateChartsInfoById(ChartsInfoDO chartsInfo) {
		return chartsInfoDAO.updateChartsInfoById(chartsInfo);
	}

	public Integer countChartsInfoList(ChartsInfoDTO chartsInfoDTO) {
		return chartsInfoDAO.countChartsInfoList(chartsInfoDTO);
	}

	public List<ChartsInfoDTO> queryChartsInfoList(ChartsInfoDTO chartsInfoDTO) {
		return chartsInfoDAO.queryChartsInfoList(chartsInfoDTO);
	}
}
