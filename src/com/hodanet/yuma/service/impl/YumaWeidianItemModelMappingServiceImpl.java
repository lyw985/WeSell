package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.entity.po.YumaWeidianItemModelMapping;
import com.hodanet.yuma.service.YumaWeidianItemModelMappingService;
import com.hodanet.yuma.service.YumaWeidianItemModelService;

/**
 * @anthor lyw
 * @yumaWeidianItemModelMapping 2016-11-11 10:34:32
 */
@Service
public class YumaWeidianItemModelMappingServiceImpl extends AbstractDaoService
		implements YumaWeidianItemModelMappingService {

	@Autowired
	private YumaWeidianItemModelService yumaWeidianItemModelService;

	@Override
	public YumaWeidianItemModelMapping getYumaWeidianItemModelMappingById(Integer id) {
		return this.getDao().get(YumaWeidianItemModelMapping.class, id);
	}

	@Override
	public PageData<YumaWeidianItemModelMapping> getYumaWeidianItemModelMappingByPage(
			PageData<YumaWeidianItemModelMapping> pageData, YumaWeidianItemModelMapping yumaWeidianItemModelMapping) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaWeidianItemModelMapping o where 1=1");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaWeidianItemModelMapping saveYumaWeidianItemModelMapping(
			YumaWeidianItemModelMapping yumaWeidianItemModelMapping) {
		if (yumaWeidianItemModelMapping == null) {
			return null;
		}
		if (yumaWeidianItemModelMapping.getId() == null) {
			yumaWeidianItemModelMapping.setPricePercent(1f);
			this.getDao().save(yumaWeidianItemModelMapping);
		} else {
			YumaWeidianItemModelMapping orginal = getYumaWeidianItemModelMappingById(
					yumaWeidianItemModelMapping.getId());
			orginal.setYumaItemModel(yumaWeidianItemModelMapping.getYumaItemModel());
			orginal.setCount(yumaWeidianItemModelMapping.getCount());
			// TODO
		}
		return yumaWeidianItemModelMapping;
	}

	@Override
	public void deleteYumaWeidianItemModelMapping(Integer id) {
		String sql = "delete from yuma_weidian_item_model_mapping where id = ?";
		this.getDao().getJdbcTemplate().update(sql, id);
	}

	public void updateYumaWeidianItemModelMappingPercent(Integer yumaWeidianItemModelId, Integer... exceptIds) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" select round(sum(yumaItemModel.pay_price*yumaItemModel.count)/sum(yumaItemModel.count*yumaWeidianItemModelMapping.count),2) as avgPrice");
		sb.append(" from yuma_order_item yumaItemModel");
		sb.append(" left join yuma_receiver yumaReceiver on yumaReceiver.id = yumaItemModel.receiver_id");
		sb.append(
				" left join yuma_weidian_item_model_mapping yumaWeidianItemModelMapping on yumaWeidianItemModelMapping.weidian_item_model_id = yumaItemModel.weidian_item_model_id");
		sb.append(" where  yumaWeidianItemModelMapping.item_model_id = ?");
		YumaWeidianItemModel yumaWeidianItemModel = yumaWeidianItemModelService
				.getWeidianItemModelById(yumaWeidianItemModelId);
		Set<YumaWeidianItemModelMapping> set = yumaWeidianItemModel.getYumaWeidianItemModelMappings();
		if (set != null && set.size() > 0) {
			if (exceptIds != null && exceptIds.length > 0) {
				for (Integer id : exceptIds) {
					for (YumaWeidianItemModelMapping mapping : set) {
						if (mapping.getId() == id) {
							set.remove(mapping);
							break;
						}
					}
				}
			}

			Map<Integer, Double> map = new HashMap<Integer, Double>();
			Double total = 0d;
			for (YumaWeidianItemModelMapping mapping : set) {
				int item_model_id = mapping.getYumaItemModel().getId();
				Map<String, Object> avgMap = this.getDao().getJdbcTemplate().queryForMap(sb.toString(), item_model_id);
				Double avgPrice = 0d;
				if (map != null) {
					avgPrice = (Double) avgMap.get("avgPrice");
				}
				if (avgPrice == null) {
					avgPrice = 1d;
				}
				map.put(mapping.getId(), avgPrice * mapping.getCount());
				total += avgPrice * mapping.getCount();
			}
			for (YumaWeidianItemModelMapping mapping : set) {
				for (Integer key : map.keySet()) {
					if (mapping.getId() == key) {
						mapping.setPricePercent((float) (map.get(key) / total));
						break;
					}
				}
			}
		}
	}

}
