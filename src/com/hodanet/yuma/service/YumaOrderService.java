package com.hodanet.yuma.service;

import java.util.Date;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;

/**
 * @anthor lyw
 * @yumaOrder 2016-11-11 10:34:32
 */
public interface YumaOrderService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public YumaOrder getYumaOrderById(Integer id);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaOrder> getYumaOrderByPage(PageData<YumaOrder> pageData, YumaOrder yumaOrder);

	/**
	 * 
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaOrder saveYumaOrder(YumaOrder yumaOrder);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteYumaOrder(Integer[] ids);

	public void updateYumaOrderStatus(Integer id, Integer status);
	
	public YumaOrder getOrCreateOrder(YumaUser yumaUser, YumaReceiver yumaReceiver, Date orderPayTime);

	public void updateYumaOrderStatus(Integer id, int itemCountInt, float payPriceFloat, float originalPriceFloat);

}
