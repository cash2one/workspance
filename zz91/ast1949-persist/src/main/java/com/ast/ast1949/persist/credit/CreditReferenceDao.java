package com.ast.ast1949.persist.credit;

import java.util.List;

import com.ast.ast1949.domain.credit.CreditReferenceDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditReferenceDTO;

public interface CreditReferenceDao {

	public Integer countReferenceByCompany(Integer companyId, String checkStatus);

	public List<CreditReferenceDo> queryReferenceByCompany(Integer companyId);

	public Integer insertReferenceByCompany(CreditReferenceDo reference);

	public Integer updateReferenceByCompany(CreditReferenceDo reference);

	public Integer deleteReferenceByCompany(Integer id, Integer companyId);

	public Integer updateCheckStatusByAdmin(Integer id, String status,
			String person);
	
	public CreditReferenceDo queryReferenceById(Integer id);
	
	/**
	 * 查询资信参考人信息列表
	 * @param dto
	 * @return
	 */
	public List<CreditReferenceDTO> queryReferenceByConditions(CreditReferenceDTO dto, PageDto<CreditReferenceDTO> page);
	/**
	 * 统计资信参考人信息总数
	 * @param dto
	 * @return
	 */
	public Integer countReferenceByConditions(CreditReferenceDTO dto);
	/**
	 * 根据编号删除记录
	 * @param id 编号
	 * @return
	 */
	public Integer deleteReferenceById(Integer id);
	/**
	 * 更新审核状态
	 * @param id 编号
	 * @param checkStatus 审核状态：0 未审核；1 已审核；2 审核不通过。
	 * @param checkPerson 审核人
	 * @return
	 */
	public Integer updateReferenceCheckStatusById(Integer id,String checkStatus,String checkPerson);
 	
	// /**
	// * 添加资信参考人
	// * @param creditCompany为资信参考人CreditCompanyDO信息，不能为空
	// * @return 添加成功的记录条数，失败返回0
	// */
	// public Integer insertCreditCompany(CreditReferenceDo creditCompany);
	//
	// /**
	// * 查询资信参考人总个数
	// * @param pageDto为分页Dto，可为空
	// * @param creditCompany为资信参考人CreditCompanyDO信息，可为空
	// * @return 总个数，没找到返回为0
	// */
	// public Integer selectCreditCompanyCount(PageDto pageDto,
	// CreditReferenceDo creditCompany);
	//	
	// /**
	// * 查询资信参考人信息
	// * @param pageDto为分页Dto，可为空
	// * @param creditCompany为资信参考人CreditCompanyDO信息，可为空
	// * @return CreditCompanyDO信息集，没找到返回为空
	// */
	// public List<CreditReferenceDo> selectCreditCompany(PageDto pageDto,
	// CreditReferenceDo creditCompany);
	//	
	// /**
	// * 批量删除资信参考人
	// * @param ids为主键数组，不能为空
	// * @return 删除成功总条数，没成功返回为0
	// */
	// public Integer deleteCreditCompany(Integer[] ids);
	//	
	// /**
	// * 查询资信参考人
	// * @param id为主键ID值，不可为null
	// * @return CreditCompanyDO信息，没找到返回为空
	// */
	// public CreditReferenceDo selectCreditCompanyById(Integer id);
	//	
	// /**
	// * 查询资信参考人
	// * @param companyId为公司主键ID，不可为null
	// * @return CreditCompanyDO信息集，没找到返回为空
	// */
	// public List<CreditReferenceDo> selectCreditCompanyByCompanyId(Integer
	// companyId);
	//	
	// /**
	// * 修改资信参考人
	// * @param creditCompany为资信参考人CreditCompanyDO信息，不可为空
	// * @return 修改成功总记录条数
	// */
	// public Integer updateCreditCompany(CreditReferenceDo creditCompany);

}
