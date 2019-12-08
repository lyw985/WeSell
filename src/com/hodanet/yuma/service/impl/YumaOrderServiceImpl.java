package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.DateConverterUtil;
import com.hodanet.common.util.StringUtil;
import com.hodanet.yuma.constant.YumaOrderStatus;
import com.hodanet.yuma.constant.YumaWeidianDataOrderStatus;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.service.YumaOrderService;

/**
 * @anthor lyw
 * @yumaOrder 2016-11-11 10:34:32
 */
@Service
public class YumaOrderServiceImpl extends AbstractDaoService implements YumaOrderService {

	@Override
	public YumaOrder getYumaOrderById(Integer id) {
		return this.getDao().get(YumaOrder.class, id);
	}

	@Override
	public PageData<YumaOrder> getYumaOrderByPage(PageData<YumaOrder> pageData, YumaOrder yumaOrder) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaOrder o where 1=1");
		if (yumaOrder.getYumaUser() != null) {
			YumaUser yumaUser = yumaOrder.getYumaUser();
			if (yumaUser.getId() != null) {
				sb.append(" and o.yumaUser.id = ? ");
				params.add(yumaUser.getId());
			}
			if (StringUtil.isNotBlank(yumaUser.getName())) {
				sb.append(" and o.yumaUser.name like ? ");
				params.add("%" + yumaUser.getName() + "%");
			}
		}
		if (yumaOrder.getYumaReceiver() != null) {
			YumaReceiver yumaReceiver = yumaOrder.getYumaReceiver();
			if (yumaReceiver.getId() != null) {
				sb.append(" and o.yumaReceiver.id = ? ");
				params.add(yumaReceiver.getId());
			}
			if (StringUtil.isNotBlank(yumaReceiver.getName())) {
				sb.append(" and o.yumaReceiver.name like ? ");
				params.add("%" + yumaReceiver.getName() + "%");
			}
			if (StringUtil.isNotBlank(yumaReceiver.getPhone())) {
				sb.append(" and o.yumaReceiver.phone like ? ");
				params.add("%" + yumaReceiver.getPhone() + "%");
			}
			if (yumaReceiver.getProvince() != null && yumaReceiver.getProvince().getId() != null) {
				sb.append(" and o.yumaReceiver.province.id = ? ");
				params.add(yumaReceiver.getProvince().getId());
			}
			if (yumaReceiver.getCity() != null && yumaReceiver.getCity().getId() != null) {
				sb.append(" and o.yumaReceiver.city.id = ? ");
				params.add(yumaReceiver.getCity().getId());
			}
			if (yumaReceiver.getArea() != null && yumaReceiver.getArea().getId() != null) {
				sb.append(" and o.yumaReceiver.area.id = ? ");
				params.add(yumaReceiver.getArea().getId());
			}
		}
		if (yumaOrder.getStartDate() != null) {
			sb.append(" and o.payDate >= ? ");
			params.add(yumaOrder.getStartDate());
		}
		if (yumaOrder.getEndDate() != null) {
			sb.append(" and o.payDate <= ? ");
			params.add(yumaOrder.getEndDate());
		}
		if (yumaOrder.getStatus() != null) {
			sb.append(" and o.status = ? ");
			params.add(yumaOrder.getStatus());
		}
		// sb.append(" and o.id in ( select i.yumaOrder.id from YumaOrderItem i where
		// i.yumaWeidianItemModel.name like ?) ");
		// params.add("%红美人%");
		sb.append(" order by o.payDate desc");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaOrder saveYumaOrder(YumaOrder yumaOrder) {
		if (yumaOrder == null) {
			return null;
		}
		if (yumaOrder.getId() == null) {
			this.getDao().save(yumaOrder);
		} else {
			YumaOrder orginal = getYumaOrderById(yumaOrder.getId());
			orginal.setPayDate(yumaOrder.getPayDate());
			orginal.setStatus(YumaOrderStatus.WAITFORSEND.getValue());
			// TODO
		}
		return yumaOrder;
	}

	@Override
	public void deleteYumaOrder(Integer[] ids) {
		this.getDao().delete(YumaOrder.class, ids);
	}

	@Override
	public void updateYumaOrderStatus(Integer id, Integer status) {
		String hql = "update YumaOrder o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

	@Override
	public YumaOrder getOrCreateOrder(YumaUser yumaUser, YumaReceiver yumaReceiver, Date orderPayTime, String orderStatus) {
		if (yumaUser == null || yumaUser.getId() == null || yumaReceiver == null || yumaReceiver.getId() == null
				|| orderPayTime == null) {
			return null;
		}
		String hql = "from YumaOrder o where o.yumaUser.id = ? and o.yumaReceiver.id = ? and o.payDate = ?";
		YumaOrder yumaOrder = this.getDao().queryHqlUniqueResult(hql, yumaUser.getId(), yumaReceiver.getId(),
				DateConverterUtil.floorDate(orderPayTime));
		if (yumaOrder == null && !YumaWeidianDataOrderStatus.CLOSED.toString().equals(orderStatus)) {
			yumaOrder = new YumaOrder();
			yumaOrder.setYumaUser(yumaUser);
			yumaOrder.setYumaReceiver(yumaReceiver);
			yumaOrder.setPayDate(orderPayTime);
			yumaOrder.setPayPrice(0f);
			yumaOrder.setOriginalPrice(0f);
			yumaOrder.setItemNumber(0);
			yumaOrder.setStatus(0);
			yumaOrder = saveYumaOrder(yumaOrder);
		} else {
			// TODO 如果需要调整订单状态的，在这里操作
		}
		return yumaOrder;
	}

	@Override
	public void updateYumaOrderStatus(Integer id, int itemCountInt, float payPriceFloat, float originalPriceFloat) {
		String sql = "update yuma_order a set a.pay_price = a.pay_price + ?, a.original_price = a.original_price + ? where a.id = ?";
		this.getDao().getJdbcTemplate().update(sql, itemCountInt * payPriceFloat, itemCountInt * originalPriceFloat,
				id);
	}

}
