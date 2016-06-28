package com.ast.ast1949.persist.auth;

import java.util.List;

import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

/**
 * @author Mr.Mar (x03570227@gmail.com)
 *
 */
public interface ParamDao {

	/**
	 * 列出参数信息
	 * @param param :查询条件,不能为null
	 * 		param.id:不为null时增加查询条件 id = param.id
	 * 		param.name:不为空时按照name前匹配查询
	 * 		param.types:不为空时表示查询某参数类别下的所有参数信息
	 * 		param.key:不为空时增加查询条件 key=param.key
	 * 		param.isuse:不为空时起作用,0 表示禁用,1 表示正常
	 * @return 根据查询条件返回的参数列表
	 */
	public List<Param> listParam(Param param);

	/**
	 * 返回所有参数类别信息
	 * @return 参数类别列表
	 */
	public List<ParamType> listAllParamType();

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
	 * 更新参数信息
	 * @param param :需要更新的参数信息,不能为空
	 * 		param.id:不能为空,否则无法更新数据
	 * 		param.key:不能为空
	 * 		param.isuse:不会更新此字段,此字段的任何值设置了无效
	 * @return 更新影响的行数,0 表示没有更新记录,正数n 表示更新了n条记录
	 */
	public Integer updateParam(Param param);

	/**
	 * 通过参数ID删除一条
	 * @param id:被删除记录的ID号,不能为空
	 * @return 0或正数n, 0 表示没有记录被删除,n 表示n条记录被删除
	 */
	public Integer deleteParamById(Integer id);

	/**
	 * 通过参数类别删除删除信息
	 * @param types :参数类型,不能为null,也不能为"",否则不删除任何信息
	 * @return 0或正数n, 0 表示没有记录被删除,n 表示n条记录被删除
	 */
	public Integer deleteParamByTypes(String types);

	/**
	 * 通过参数ID查找参数信息
	 * @param id:参数ID,不能为null
	 * @return
	 */
	public Param listOneParamById(Integer id);

}
