package com.ast.ast1949.persist.credit;

import java.util.List;

import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditFileDTO;

public interface CreditFileDao {

	public List<CreditFileDo> queryFileByCompany(Integer companyId);

	public Integer insertFileByCompany(CreditFileDo creditFile);

	public Integer updateFileById(CreditFileDo creditFile);

	public Integer deleteFileById(Integer id);

	public Integer updateCreditFileCheckStatusById(Integer id, String checkStatus,
			String checkPerson);
	
	public CreditFileDo queryFileById(Integer id);
	
	public List<CreditFileDTO> queryCreditFileByConditions(CreditFileDTO dto, PageDto<CreditFileDTO> page);
	
	public Integer countCreditFileByConditions(CreditFileDTO dot);

	//后台操作
	public Integer updateFileByAdmin(CreditFileDo creditFile);
	// /**
	// * 添加证书荣誉
	// * @param creditFile为证书荣誉CreditFileDO信息，不能为空
	// * @return 插入成功的总条数，失败返回0
	// */
	// public Integer insertCreditFile(CreditFileDo creditFile);
	//	
	// /**
	// * 查询证书荣誉总个数
	// * @param pageDto为分页DTO，可为空
	// * @param creditFile为证书荣誉CreditFileDO信息，可为空
	// * @return 总个数，没查询到返回为0
	// */
	// public Integer selectCreditFileCount(PageDto pageDto,
	// CreditFileDo creditFile);
	//	
	// /**
	// * 查询证书荣誉
	// * @param pageDto为分页DTO，可为空
	// * @param creditFile为证书荣誉CreditFileDO信息，可为空
	// * @return CreditFileDO信息集，没找到返回为空
	// */
	// public List<CreditFileDo> selectCreditFile(PageDto pageDto,
	// CreditFileDo creditFile);
	//	
	// public List<CreditFileDo> selectCreditFile(CreditFileDo creditFile);
	// /**
	// * 修改证书荣誉审核状态
	// * @param ids为主键ID整形数组，不可为空<br/>
	// * checkStatus不审核状态，不可为空
	// * @return 修改成功的总条数
	// */
	// public Integer updateCreditFileCheckStatus(Integer[] ids, String
	// checkStatus);
	//	
	// /**
	// * 根据ID查询证书荣誉
	// * @param id为主键ID值，不能为null
	// * @return 证书荣誉CreditFileDO信息，没找到返回为空
	// */
	// public CreditFileDo selectCreditFileById(Integer id);
	//	
	// public CreditFileDo selectCreditFileByCompanyId(Integer company_id);
	//	
	// public Integer deleteCreditFileById(Integer id);
	//	
	// public Integer updateCreditFileDO(CreditFileDo editFileDO);
	/**
	 * 查询picName
	 * @param creditFile
	 * @return
	 */
	public String selectpicNameByCompanyId(CreditFileDo creditFile);
}
