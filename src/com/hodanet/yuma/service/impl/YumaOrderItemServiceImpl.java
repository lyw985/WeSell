package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.yuma.constant.YumaOrderItemStatus;
import com.hodanet.yuma.constant.YumaWeidianDataOrderStatus;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaOrderItem;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.service.YumaOrderItemService;
import com.hodanet.yuma.service.YumaOrderService;

/**
 * @anthor lyw
 * @yumaOrderItem 2016-11-11 10:34:32
 */
@Service
public class YumaOrderItemServiceImpl extends AbstractDaoService implements YumaOrderItemService {

	@Autowired
	private YumaOrderService yumaOrderService;

	@Override
	public YumaOrderItem getYumaOrderItemById(Integer id) {
		return this.getDao().get(YumaOrderItem.class, id);
	}

	@Override
	public PageData<YumaOrderItem> getYumaOrderItemByPage(PageData<YumaOrderItem> pageData,
			YumaOrderItem yumaOrderItem) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaOrderItem o where 1=1");
		if (yumaOrderItem.getStatus() != null) {
			sb.append(" and o.status = ? ");
			params.add(yumaOrderItem.getStatus());
		}
		sb.append(" order by o.createTime desc");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaOrderItem saveYumaOrderItem(YumaOrderItem yumaOrderItem) {
		if (yumaOrderItem == null) {
			return null;
		}
		if (yumaOrderItem.getId() == null) {
			this.getDao().save(yumaOrderItem);
		} else {
			YumaOrderItem orginal = getYumaOrderItemById(yumaOrderItem.getId());
			orginal.setStatus(yumaOrderItem.getStatus());
			// TODO
		}
		return yumaOrderItem;
	}

	@Override
	public void deleteYumaOrderItem(Integer[] ids) {
		this.getDao().delete(YumaOrderItem.class, ids);
	}

	@Override
	public void updateYumaOrderItemStatus(Integer id, Integer status) {
		String hql = "update YumaOrderItem o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

	@Override
	public YumaOrderItem changeOrderItem(YumaOrder yumaOrder, YumaReceiver yumaReceiver,
			YumaWeidianItemModel yumaWeidianItemModel, String orderStatus, Date orderPayDateTime, String itemCount,
			String payPrice, String originalPrice) {
		if (yumaOrder == null || yumaOrder.getId() == null || yumaReceiver == null || yumaReceiver.getId() == null
				|| yumaWeidianItemModel == null || yumaWeidianItemModel.getId() == null || orderStatus == null
				|| !StringUtil.isNotBlank(itemCount) || !StringUtil.isNotBlank(payPrice)
				|| !StringUtil.isNotBlank(originalPrice)) {
			return null;
		}
		int itemCountInt = Integer.parseInt(itemCount);
		float payPriceFloat = Float.parseFloat(payPrice);
		float originalPriceFloat = Float.parseFloat(originalPrice);
		String hql = "from YumaOrderItem o where o.yumaOrder.id = ? and o.yumaReceiver.id = ? and o.yumaWeidianItemModel.id = ? and o.payTime = ?";
		YumaOrderItem yumaOrderItem = this.getDao().queryHqlUniqueResult(hql, yumaOrder.getId(), yumaReceiver.getId(),
				yumaWeidianItemModel.getId(), orderPayDateTime);
		// TODO 不同订单状态需要增加功能，
		if (yumaOrderItem == null && !YumaWeidianDataOrderStatus.CLOSED.toString().equals(orderStatus)) {
			yumaOrderItem = new YumaOrderItem();
			yumaOrderItem.setYumaOrder(yumaOrder);
			yumaOrderItem.setYumaReceiver(yumaReceiver);
			yumaOrderItem.setWeidianItemModel(yumaWeidianItemModel);
			yumaOrderItem.setPayTime(orderPayDateTime);
			yumaOrderItem.setCount(itemCountInt);
			yumaOrderItem.setPayPrice(payPriceFloat);
			yumaOrderItem.setOriginalPrice(originalPriceFloat);
			yumaOrderItem.setStatus(YumaOrderItemStatus.NORMAL.getValue());
			yumaOrderItem = saveYumaOrderItem(yumaOrderItem);
			yumaOrder.setPayPrice(yumaOrder.getPayPrice() + itemCountInt * payPriceFloat);
			yumaOrder.setOriginalPrice(yumaOrder.getOriginalPrice() + itemCountInt * originalPriceFloat);
			yumaOrder.setItemNumber(yumaOrder.getItemNumber() + itemCountInt);
		}
		return yumaOrderItem;
	}

	@Override
	public void updateBatchYumaOrderWeidianItemModel(Integer fromWeidianItemModelId, Integer toWeidianItemModelId) {
		String hql = "update YumaOrderItem o set o.yumaWeidianItemModel.id = ? where o.yumaWeidianItemModel.id = ?";
		this.getDao().executeUpdate(hql, toWeidianItemModelId, fromWeidianItemModelId);
	}

}
