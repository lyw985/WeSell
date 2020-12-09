package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.constant.CommonConstants;
import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.constant.SyncStatus;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.service.YumaReceiverService;

/**
 * @anthor lyw
 * @yumaReceiver 2016-11-11 10:34:32
 */
@Service
public class YumaReceiverServiceImpl extends AbstractDaoService implements YumaReceiverService {

	@Override
	public YumaReceiver getYumaReceiverById(Integer id) {
		return this.getDao().get(YumaReceiver.class, id);
	}

	@Override
	public PageData<YumaReceiver> getYumaReceiverByPage(PageData<YumaReceiver> pageData, YumaReceiver yumaReceiver) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaReceiver o where 1=1");
		if (yumaReceiver.getYumaUser() != null && yumaReceiver.getYumaUser().getId() != null) {
			sb.append(" and o.yumaUser.id = ? ");
			params.add(yumaReceiver.getYumaUser().getId());
		}
		if (yumaReceiver.getStatus() != null) {
			sb.append(" and o.status = ? ");
			params.add(yumaReceiver.getStatus());
		}
		if (yumaReceiver.getSyncStatus() != null) {
			sb.append(" and o.syncStatus = ? ");
			params.add(yumaReceiver.getSyncStatus());
		}
		sb.append(" order by o.createTime desc");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaReceiver saveYumaReceiver(YumaReceiver yumaReceiver) {
		if (yumaReceiver == null) {
			return null;
		}
		if (yumaReceiver.getId() == null) {
			yumaReceiver.setStatus(0);
			yumaReceiver.setDefaultAddress(0);
			this.getDao().save(yumaReceiver);
		} else {
			YumaReceiver orginal = getYumaReceiverById(yumaReceiver.getId());
			orginal.setName(yumaReceiver.getName());
			orginal.setPhone(yumaReceiver.getPhone());
			orginal.setProvince(yumaReceiver.getProvince());
			orginal.setCity(yumaReceiver.getCity());
			orginal.setArea(yumaReceiver.getArea());
			// TODO
		}
		return yumaReceiver;
	}

	@Override
	public void deleteYumaReceiver(Integer[] ids) {
		this.getDao().delete(YumaReceiver.class, ids);
	}

	@Override
	public void updateYumaReceiverStatus(Integer id, Integer status) {
		String hql = "update YumaReceiver o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

	@Override
	public void updateYumaReceiverDefaultAddress(Integer id, Integer defaultAddress) {
		if (defaultAddress == 1) {
			YumaReceiver yumaReceiver = getYumaReceiverById(id);
			String hql = "update YumaReceiver o set o.defaultAddress = 0 where o.id != ? and o.yumaUser.id = ?";
			this.getDao().executeUpdate(hql, id, yumaReceiver.getYumaUser().getId());
		}

		String hql = "update YumaReceiver o set o.defaultAddress = ? where o.id = ?";
		this.getDao().executeUpdate(hql, defaultAddress, id);
	}

	@Override
	public PageData<YumaReceiver> getYumaReceiverBySearchValue(PageData<YumaReceiver> pageData, String searchValue) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaReceiver o where o.yumaUser.name like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" or o.yumaUser.nickname like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" or o.yumaUser.nickname like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" or o.yumaUser.phone like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" or o.name like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" or o.phone like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" or o.addressDetail like ? ");
		params.add("%" + searchValue + "%");
		sb.append(" order by o.createTime desc");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaReceiver getOrCreateReceiver(String name, String phone, YumaUser yumaUser, Province province, City city,
			Area area, String address) {
		if (!StringUtil.isNotBlank(name) || !StringUtil.isNotBlank(phone) || yumaUser == null || province == null
				|| city == null) {
			return null;
		}
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaReceiver o where o.name = ? ");
		params.add(name);
		sb.append(" and o.phone = ? ");
		params.add(phone);
		sb.append(" and o.yumaUser.id = ? ");
		params.add(yumaUser.getId());
		sb.append(" and o.province.id = ? ");
		params.add(province.getId());
		sb.append(" and o.city.id = ? ");
		params.add(city.getId());
		if (area != null) {
			sb.append(" and o.area.id = ? ");
			params.add(area.getId());
		}
		sb.append(" and o.addressDetail = ? ");
		params.add(address);
		YumaReceiver yumaReceiver = this.getDao().queryHqlUniqueResult(sb.toString(),
				params.toArray(new Object[params.size()]));
		if (yumaReceiver == null) {
			yumaReceiver = new YumaReceiver();
			yumaReceiver.setYumaUser(yumaUser);
			yumaReceiver.setName(name);
			yumaReceiver.setPhone(phone);
			yumaReceiver.setProvince(province);
			yumaReceiver.setCity(city);
			yumaReceiver.setArea(area);
			yumaReceiver.setAddressDetail(address);
			yumaReceiver = saveYumaReceiver(yumaReceiver);
		}
		return yumaReceiver;
	}

	@Override
	public void updateBatchYumaReceiverArea(Integer fromAreaId, Integer toAreaId) {
		String hql = "update YumaReceiver o set o.area.id = ? where o.area.id = ?";
		this.getDao().executeUpdate(hql, toAreaId, fromAreaId);
	}

	@Override
	public void updateBatchYumaReceiverCity(Integer fromCityId, Integer toCityId) {
		String hql = "update YumaReceiver o set o.city.id = ? where o.city.id = ?";
		this.getDao().executeUpdate(hql, toCityId, fromCityId);
	}

	@Override
	public void updateBatchYumaReceiverProvince(Integer fromProvinceId, Integer toProvinceId) {
		String hql = "update YumaReceiver o set o.province.id = ? where o.province.id = ?";
		this.getDao().executeUpdate(hql, toProvinceId, fromProvinceId);
	}

	@Override
	public void updateUnsycReceiverToSyncing(int number) {
		String sql = "update yuma_receiver a set a.sync_status = ? where a.id in ( select b.id from (SELECT id FROM yuma_receiver c where c.sync_status = ? ORDER BY c.create_time asc LIMIT 0,?) b)";
		this.getDao().getJdbcTemplate().update(sql, SyncStatus.SYNC_ING.getValue(), SyncStatus.INIT.getValue(), number);
	}

	@Override
	public void updateYumaReceiverSyncStatus(Integer id, int syncStatus) {
		String hql = "update YumaReceiver o set o.syncStatus = ? where o.id = ?";
		this.getDao().executeUpdate(hql, syncStatus, id);
	}

	@Override
	public void updateSingleYumaReceiver(YumaReceiver yumaReceiver) {
		String region = yumaReceiver.getProvince().getName();
		String query = yumaReceiver.getAddressDetail();
		ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>();
		arr.add(new NameValuePair("query", query));
		arr.add(new NameValuePair("region", region));
		arr.add(new NameValuePair("page_size", "1"));
		arr.add(new NameValuePair("output", "json"));
		arr.add(new NameValuePair("ak", CommonConstants.API_BAIDU_AK));
		boolean done = false;
		JSONObject obj=null;
		while (!done) {
			obj = WebUtil.getJsonStringFromUrl(CommonConstants.API_BAIDU_GET_LOCATION_URL,
					arr.toArray(new NameValuePair[arr.size()]));
			System.out.println(obj.toJSONString());

			int jsonStatus = obj.getIntValue("status");
			if (jsonStatus != 0 ) {
				throw new RuntimeException("调用api异常");
			}
			done=true;
		}
		
		JSONArray results = obj.getJSONArray("results");
		if (results.size() == 0) {
			throw new RuntimeException("未找到对应定位");
		}
		JSONObject jsonLocation = results.getJSONObject(0).getJSONObject("location");
		String lat = jsonLocation.getString("lat");
		String lng = jsonLocation.getString("lng");
		String hql = "update YumaReceiver o set o.lat = ?, o.lng = ? where o.id = ?";
		this.getDao().executeUpdate(hql, lat, lng, yumaReceiver.getId());
	}

}
