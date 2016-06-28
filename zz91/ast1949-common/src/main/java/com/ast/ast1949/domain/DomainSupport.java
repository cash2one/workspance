package com.ast.ast1949.domain;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

/**
 * the parent class for all domain classes
 * 
 * @author liuwb
 * 
 */
public class DomainSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 传递对象当前所要进行操作使用的配置文件中sql语句编号
	private String sqlKey = null;
	// 存放对象将要操作的一个sql语句，只能针对每次操作临时存放
	private String sqlStmt = null;
	
	private Integer id;

	/**
	 * 将实体类的每个字段值都显示出来
	 */
	@SuppressWarnings("unchecked")
	public String toString() {
		StringBuffer results = new StringBuffer();
		Class clazz = getClass();
		results.append(getClass().getName() + "\n");
		Field[] fields = clazz.getDeclaredFields();
		try {
			AccessibleObject.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				results.append("\t" + fields[i].getName() + "="
						+ fields[i].get(this) + "\n");
			}
		} catch (Exception e) {
			// ignored!
		}
		return results.toString();
	}

	/**
	 * 对实体类每个字段都赋值null
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		Class clazz = getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			AccessibleObject.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getName().equals("serialVersionUID"))
					fields[i].set(this, null);
					
			}
		} catch (Exception e) {
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 获取实体类当前进行的操作的sql语句key值。
	 * 
	 * @return Returns the sqlKey.
	 */
	public String getSqlKey() {
		return sqlKey;
	}

	/**
	 * 设定实体类当前进行的操作的sql语句的key值
	 * 
	 * @param sqlKey
	 *            The sqlKey to set.
	 */
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}

	public String getSqlStmt() {
		return sqlStmt;
	}

	public void setSqlStmt(String sqlStmt) {
		this.sqlStmt = sqlStmt;
	}
}
