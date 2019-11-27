package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.constant.SyncStatus;
import com.hodanet.yuma.entity.po.YumaWeidianItemModelMapping;
import com.hodanet.yuma.service.YumaWeidianItemModelMappingService;

/**
 * @anthor lyw
 * @yumaWeidianItemModelMapping 2016-11-11 10:34:32
 */
@Service
public class YumaWeidianItemModelMappingServiceImpl extends AbstractDaoService
		implements YumaWeidianItemModelMappingService {

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
	public void deleteYumaWeidianItemModelMapping(Integer[] ids) {
		String sql="delete from yuma_weidian_item_model_mapping where id = ?";
		this.getDao().getJdbcTemplate().update(sql,ids[0]);
	}

}
