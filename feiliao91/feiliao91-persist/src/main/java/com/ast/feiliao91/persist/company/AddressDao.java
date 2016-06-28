package com.ast.feiliao91.persist.company;

import java.util.List;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.AddressDto;

public interface AddressDao {
	/**
	 * 插入信息
	 * @param address表
	 * @return
	 */
    public Integer insert (Address address);
    /**
     * 查询所有收货地址
     */
    public List<Address> selectById(Integer companyId);
    /**
     * 修改address地址
     * @param address
     * @return
     */
	public Integer updateAll(Address address);
	/**
	 * 查询默认收货地址
	 * 
	 */
	public Address selectDefaultAddress(Integer companyId);
	/**
     * 根查询所有发货地址
     */
	public List<Address> selectByDelId(Integer companyId);
	/**
     * 查询默认发货地址
     */
	public Address selectDefaultDelAddress(Integer companyId);
	/**
	 * 设为不是默认地址
	 * @param id
	 */
	public Integer setIsDefault(Integer id);
	/**
	 * 查询收货地址条数
	 */
	public Integer selectShopCount(Integer companyId);
	/**
	 * 查询发货地址条数
	 */
	public Integer selectHairCount(Integer companyId);
	/**
	 * 根据id查询地址信息
	 */
	public Address selectAddress(Integer id);
	/**
	 * 根据公司和地址类型获取地址
	 * @param company
	 * @param addressType
	 * @return
	 */
	public List<Address> queryAddressByCondition(Integer companyId, Integer addressType);
	/**
	 * 手机战下拉获得地址
	 * @param page
	 * @return
	 */
	public List<Address> getByPage(PageDto<AddressDto> page, Integer companyId, Integer addressType);
}
