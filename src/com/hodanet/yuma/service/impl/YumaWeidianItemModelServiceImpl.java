package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.service.YumaWeidianItemModelService;

/**
 * @anthor lyw
 * @weidianItemModel 2016-11-11 10:34:32
 */
@Service
public class YumaWeidianItemModelServiceImpl extends AbstractDaoService implements YumaWeidianItemModelService {

	@Override
	public YumaWeidianItemModel getWeidianItemModelById(Integer id) {
		return this.getDao().get(YumaWeidianItemModel.class, id);
	}

	@Override
	public PageData<YumaWeidianItemModel> getWeidianItemModelByPage(PageData<YumaWeidianItemModel> pageData,
			YumaWeidianItemModel yumaWeidianItemModel) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaWeidianItemModel o where 1=1");
		if (yumaWeidianItemModel.getName() != null) {
			sb.append(" and o.name like ? ");
			params.add("%" + yumaWeidianItemModel.getName() + "%");
		}
		sb.append(" order by o.createTime desc");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaWeidianItemModel saveWeidianItemModel(YumaWeidianItemModel yumaWeidianItemModel) {
		if (yumaWeidianItemModel == null) {
			return null;
		}
		if (yumaWeidianItemModel.getId() == null) {
			this.getDao().save(yumaWeidianItemModel);
		} else {
			YumaWeidianItemModel orginal = getWeidianItemModelById(yumaWeidianItemModel.getId());
			orginal.setName(yumaWeidianItemModel.getName());
			// TODO
		}
		return yumaWeidianItemModel;
	}

	@Override
	public void deleteWeidianItemModel(Integer[] ids) {
		this.getDao().delete(YumaWeidianItemModel.class, ids);
	}

	@Override
	public void updateWeidianItemModelStatus(Integer id, Integer status) {
		String hql = "update YumaWeidianItemModel o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

	@Override
	public YumaWeidianItemModel getOrCreateWeidianItemModelByName(String modelName, YumaWeidianItem yumaWeidianItem) {
		if (yumaWeidianItem == null || yumaWeidianItem.getId() == null) {
			return null;
		}
		String hql = "from YumaWeidianItemModel o where o.name = ? and  o.yumaWeidianItem.id = ?";
		YumaWeidianItemModel yumaWeidianItemModel = this.getDao().queryHqlUniqueResult(hql, modelName, yumaWeidianItem.getId());
		if (yumaWeidianItemModel == null) {
			yumaWeidianItemModel = new YumaWeidianItemModel();
			yumaWeidianItemModel.setName(modelName);
			yumaWeidianItemModel.setWeidianItem(yumaWeidianItem);
			yumaWeidianItemModel = saveWeidianItemModel(yumaWeidianItemModel);
		}
		return yumaWeidianItemModel;
	}

}
