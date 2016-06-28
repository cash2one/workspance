package com.ast.ast1949.service.auth;

import java.util.List;

import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

public interface ParamService {

	/**
	 * 插入参数信息
	 * @param param :要插入的参数信息,不能为空
	 * 		param.key:不能为空
	 * 		param.types:不能为空
	 * 		param.isuse:为空时,设置默认值 @see {@link com.ast.ast1949.util.AstConst#ISUSE_TRUE}
	 * @return 插入后的记录ID
	 */
	public Integer insertParam(Param param);

	/**
	 * 根据参数类型列出所有参数信息
	 * @param types: 参数类型,不能为空
	 * @return
	 */
	public List<Param> listParamByTypes(String types);

	/**
	 * 更新参数信息
	 * @param param :需要更新的参数信息,不能为空
	 * 		param.id:不能为空,否则无法更新数据
	 * 		param.key:不能为空
	 * 		param.isuse:不会更新此字段,此字段的任何值设置了无效
	 * @return 更新影响的行数,0 表示没有更新记录,正数n 表示更新了n条记录
	 */
	public Integer updateParam(Param param);

	/**
	 * 查找所有参数类型
	 * @return 参数类型列表
	 */
	public List<ParamType> listAllParamTypes();

	/**
	 * 删除参数信息
	 * @param id:待删除的参数ID,不能为null
	 * @return
	 */
	public Integer deleteParam(Integer id);

	/**
	 * 通过参数ID查找参数信息
	 * @param id:参数ID,不能为null
	 * @return
	 */
	public Param listOneParam(Integer id);

	/**
	 * 将参数配置信息备份成SQL字符串
	 * @return
	 */
	public String backupToSqlString();
	
	public List<Param> queryUsefulParam();
}
