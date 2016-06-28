package com.ast.ast1949.service.credit;

import java.util.List;

import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditFileDTO;

public interface CreditFileService {

	public List<CreditFileDo> queryFileByCompany(Integer companyId);

	/**
	 * 用户自己添加诚信档案信息，审核状态为0
	 */
	public Integer insertFileByCompany(CreditFileDo creditFile);

	public Integer updateFileById(CreditFileDo creditFile);

	public Integer deleteFileById(Integer id, Integer companyId);

	public Integer updateCheckStatusByAdmin(Integer id, String checkStatus,
			String checkPerson,Integer companyId);

	public CreditFileDo queryFileById(Integer id);

//	public List<CreditFileDTO> queryCreditFileByConditions(CreditFileDTO dot);

//	public Integer countCreditFileByConditions(CreditFileDTO dot);
	
	public PageDto<CreditFileDTO> pageCriditFileByCondition(CreditFileDTO dto, PageDto<CreditFileDTO> page);
	
	
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
	// * @param ids为主键ID值，不可为空<br/>
	// * checkStatus不审核状态，不可为空
	// * @return 修改成功返回true；否则为false
	// */
	// public Boolean updateCreditFileCheckStatus(String ids, String
	// checkStatus);
	//	
	// /**
	// * 根据ID查询证书荣誉
	// * @param id为主键ID值，不能为null
	// * @return 诚信档案CreditDTO信息，没找到返回为空
	// */
	// // public CreditDTO selectCreditFileById(Integer id);
	//	
	//	
	// /**
	// * 根据ID查询证书荣誉
	// * @param id为主键ID值，不能为null
	// * @return 荣誉证书CreditFileDO信息，没找到返回为空
	// */
	// public CreditFileDo selectCreditFileDOById(Integer id);
	//	
	// public CreditFileDo selectCreditFileByCompanyId(Integer company_id);
	//	
	// /**
	// * 提交修改后的信息
	// * @param crfeditFileDO
	// */
	// public Integer updateCreditFileDO(CreditFileDo crfeditFileDO);
	// /**
	// * 根据ID删除证书荣誉
	// * @param id
	// */
	// public Integer deleteCreditFile(Integer id);
	/**
	 * 根据CreditFileDo查询picName
	 * @param creditFile
	 * @return
	 */
	public String selectpicNameByCompanyId(CreditFileDo creditFile);
	
}
