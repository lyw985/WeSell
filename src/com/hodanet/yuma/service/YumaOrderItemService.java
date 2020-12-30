package com.hodanet.yuma.service;

import java.util.Date;
import java.util.List;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaOrderItem;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;

/**
 * @anthor lyw
 * @yumaOrderItem 2016-11-11 10:34:32
 */
public interface YumaOrderItemService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public YumaOrderItem getYumaOrderItemById(Integer id);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaOrderItem> getYumaOrderItemByPage(PageData<YumaOrderItem> pageData,
			YumaOrderItem yumaOrderItem);

	public List<YumaOrderItem> getYumaOrderItemList(YumaOrderItem yumaOrderItem);

	/**
	 * 
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaOrderItem saveYumaOrderItem(YumaOrderItem yumaOrderItem);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteYumaOrderItem(Integer[] ids);

	public void updateYumaOrderItemStatus(Integer id, Integer status);

	public YumaOrderItem changeOrderItem(YumaOrder yumaOrder, YumaReceiver yumaReceiver,
			YumaWeidianItemModel yumaWeidianItemModel, String orderStatus, Date orderPayDateTime, String itemCount,
			String itemModelPrice, String originalPrice);

	public void updateBatchYumaOrderWeidianItemModel(Integer fromWeidianItemModelId, Integer toWeidianItemModelId);

}
