package com.ast.feiliao91.service.company;

import java.util.List;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.AddressDto;

public interface AddressService {
	/**
	 * 插入信息
	 * 新添加一个地址
	 * @param address表
	 * @param type 0 代表收货地址 1 代表 发货地址
	 * @return
	 * @throws MyException 
	 */
	public Integer insert(Address address,Integer type) throws MyException;

	/**
	 * 根据companyId查询所有收货地址
	 */
	public List<Address> selectById(Integer companyId);
	

	/**
	 * 查询所有发货地址
	 */
	public List<Address> selectByDelId(Integer companyId);

	/**
	 * 修改地址
	 */
	public Integer updateAll(Address address);

	/**
	 * 删除收发货地址
	 */
	public Integer deleteById(Integer id);
	/**
	 * 查询默认收货地址
	 * 
	 */
	public Address selectDefaultAddress(Integer companyId);
	/**
	 * 查询默认发货地址
	 * 
	 */
	public Address selectDefaultDelAddress(Integer companyId);
	/**
	 * 设置默认收货地址
	 * @param  addresss 其中id 和 companyId 不能为空
 	 */
	public Integer updateDefault(Address address);
	/**
	 * 设置默认发货地址
	 * @param  addresss 其中id 和 companyId 不能为空
 	 */
	public Integer updateDefaultDel(Address address);
	/**
	 * 查询收货地址条数
	 */
	public Integer selectShopCount(Integer companyId);
	/**
	 * 查询发货地址条数
	 */
	public Integer selectHairCount(Integer companyId);
	/**
	 * 根据id 查询详细地址信息
	 */
	public Address selectAddress(Integer id);
	/**
	 * 根据公司和地址类型获取地址
	 * @param company
	 * @param addressType
	 * @return
	 */
	public List<AddressDto> queryAddressByCondition(Integer companyId, Integer addressType);

	/**
	 * 获取地址
	 * @param areaCode
	 * @return
	 */
	public String getArea(String areaCode);
	
	/**
	 * 手机战下拉获得地址
	 * @param page
	 * @return
	 */
	public PageDto<AddressDto> getByPage(PageDto<AddressDto> page, Integer companyId,Integer addressType);
}
