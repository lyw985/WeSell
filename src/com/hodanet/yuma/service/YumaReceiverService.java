package com.hodanet.yuma.service;

import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;

/**
 * @anthor lyw
 * @yumaReceiver 2016-11-11 10:34:32
 */
public interface YumaReceiverService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public YumaReceiver getYumaReceiverById(Integer id);

	/**
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaReceiver> getYumaReceiverByPage(PageData<YumaReceiver> pageData, YumaReceiver yumaReceiver);

	/**
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaReceiver saveYumaReceiver(YumaReceiver yumaReceiver);

	/**
	 * 
	 * @param ids
	 */
	public void deleteYumaReceiver(Integer[] ids);

	public void updateYumaReceiverStatus(Integer id, Integer status);

	public void updateYumaReceiverDefaultAddress(Integer id, Integer defaultAddress);

	public PageData<YumaReceiver> getYumaReceiverBySearchValue(PageData<YumaReceiver> pageData, String searchValue);

	public YumaReceiver getOrCreateReceiver(String name, String phone, YumaUser yumaUser, Province province, City city,
			Area area, String address);

	public void updateBatchYumaReceiverArea(Integer fromAreaId, Integer toAreaId);

	public void updateBatchYumaReceiverCity(Integer fromCityId, Integer toCityId);

	public void updateBatchYumaReceiverProvince(Integer fromProvinceId, Integer toProvinceId);

}
