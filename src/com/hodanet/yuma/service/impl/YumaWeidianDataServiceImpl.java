package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.service.AreaService;
import com.hodanet.common.service.CityService;
import com.hodanet.common.service.ProvinceService;
import com.hodanet.yuma.constant.SyncStatus;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaOrderItem;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.entity.po.YumaWeidianData;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.service.YumaOrderItemService;
import com.hodanet.yuma.service.YumaOrderService;
import com.hodanet.yuma.service.YumaReceiverService;
import com.hodanet.yuma.service.YumaUserService;
import com.hodanet.yuma.service.YumaWeidianDataService;
import com.hodanet.yuma.service.YumaWeidianItemModelService;
import com.hodanet.yuma.service.YumaWeidianItemService;

/**
 * @anthor lyw
 * @originalData 2016-11-11 10:34:32
 */
@Service
public class YumaWeidianDataServiceImpl extends AbstractDaoService implements YumaWeidianDataService {

	@Autowired
	private YumaWeidianItemService yumaWeidianItemService;

	@Autowired
	private YumaWeidianItemModelService yumaWeidianItemModelService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private YumaUserService yumaUserSerivce;

	@Autowired
	private YumaReceiverService yumaReceiverService;

	@Autowired
	private YumaOrderService yumaOrderService;

	@Autowired
	private YumaOrderItemService yumaOrderItemService;

	@Override
	public YumaWeidianData getYumaWeidianDataById(Integer id) {
		return this.getDao().get(YumaWeidianData.class, id);
	}

	@Override
	public PageData<YumaWeidianData> getYumaWeidianDataByPage(PageData<YumaWeidianData> pageData,
			YumaWeidianData yumaWeidianData) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaWeidianData o where 1=1");
		if (yumaWeidianData.getSyncStatus() != null) {
			sb.append(" and o.syncStatus  = ? ");
			params.add(yumaWeidianData.getSyncStatus());
		}
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaWeidianData saveYumaWeidianData(YumaWeidianData yumaWeidianData) {
		if (yumaWeidianData == null) {
			return null;
		}
		// 判断是否数据已存在，存在的话返回提示
		if (yumaWeidianData.getId() == null) {
			this.getDao().save(yumaWeidianData);
		} else {
			YumaWeidianData orginal = getYumaWeidianDataById(yumaWeidianData.getId());
			// TODO
		}
		return yumaWeidianData;
	}

	@Override
	public void saveYumaWeidianDatas(YumaWeidianData[] yWeidianDatas) {
		this.getDao().save(yWeidianDatas);
	}

	@Override
	public void deleteYumaWeidianData(Integer[] ids) {
		this.getDao().delete(YumaWeidianData.class, ids);
	}

	@Override
	public void updateYumaWeidianDataSyncStatus(Integer id, Integer syncStatus) {
		String hql = "update YumaWeidianData o set o.syncStatus = ? where o.id = ?";
		this.getDao().executeUpdate(hql, syncStatus, id);
	}

	@Override
	public void updateUnsycWeidianDataToSyncing(int number) {
		String sql = "update yuma_weidian_data a set a.sync_status = ? where a.id in ( select b.id from (SELECT id FROM yuma_weidian_data c where c.sync_status = ? ORDER BY c.order_create_time asc LIMIT 0,?) b)";
		this.getDao().getJdbcTemplate().update(sql, SyncStatus.SYNC_ING.getValue(), SyncStatus.INIT.getValue(), number);
	}

	@Override
	public void updateSingleYumaWeidianData(YumaWeidianData yumaWeidianData) {
		// 1.如果没有商品和商品型号，增加商品和商品型号
		YumaWeidianItem yumaWeidianItem = yumaWeidianItemService
				.getOrCreateYumaWeidianItemByName(yumaWeidianData.getItemName());
		YumaWeidianItemModel yumaWeidianItemModel = yumaWeidianItemModelService
				.getOrCreateWeidianItemModelByName(yumaWeidianData.getItemModelName(), yumaWeidianItem);
		// 2.如果没有用户，增加用户
		YumaUser yumaUser = yumaUserSerivce.getOrCreateYumaUserByPhone(yumaWeidianData.getReceiverName(),
				yumaWeidianData.getReceiverPhone());
		// 3.如果没有省市地区，增加省市地区
		Province province = provinceService.getOrCreateProvinceByName(yumaWeidianData.getReceiverProvince());
		City city = cityService.getOrCreateCityByName(yumaWeidianData.getReceiverCity(), province);
		Area area = areaService.getOrCreateAreaByName(yumaWeidianData.getReceiverArea(), city);

		// 4.如果没有收货地址，增加收货地址
		YumaReceiver yumaReceiver = yumaReceiverService.getOrCreateReceiver(yumaWeidianData.getReceiverName(),
				yumaWeidianData.getReceiverPhone(), yumaUser, province, city, area,
				yumaWeidianData.getReceiverAddress());

		// 5.如果没有当天的订单，增加订单
		YumaOrder yumaOrder = yumaOrderService.getOrCreateOrder(yumaUser, yumaReceiver,
				yumaWeidianData.getOrderPayTime(), yumaWeidianData.getOrderStatus());
		// 6.如果有订单，增删改订单详情
		YumaOrderItem yumaOrderItem = yumaOrderItemService.changeOrderItem(yumaOrder, yumaReceiver,yumaWeidianItemModel,
				yumaWeidianData.getOrderStatus(), yumaWeidianData.getOrderPayTime(), yumaWeidianData.getItemCount(),
				yumaWeidianData.getItemModelPrice(), yumaWeidianData.getOriginalPrice());

		yumaUserSerivce.updateYumaUserSyncStatus(yumaUser.getId(), SyncStatus.INIT.getValue());
	}

}
