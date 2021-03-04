package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaItemModel;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaOrderItem;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.entity.po.YumaWeidianItemModelMapping;
import com.hodanet.yuma.service.YumaOrderItemService;
import com.hodanet.yuma.service.YumaWeidianItemModelMappingService;
import com.hodanet.yuma.service.YumaWeidianItemModelService;

/**
 * @anthor lyw
 * @weidianItemModel 2016-11-11 10:34:32
 */
@Service
public class YumaWeidianItemModelServiceImpl extends AbstractDaoService implements YumaWeidianItemModelService {
	@Autowired
	private YumaWeidianItemModelMappingService yumaWeidianItemModelMappingService;

	@Override
	public YumaWeidianItemModel getWeidianItemModelById(Integer id, boolean showMappings) {
		YumaWeidianItemModel model = this.getDao().get(YumaWeidianItemModel.class, id);
		if (showMappings && model != null) {
			YumaWeidianItemModelMapping mapping = new YumaWeidianItemModelMapping();
			mapping.setYumaWeidianItemModel(model);
			model.setYumaWeidianItemModelMappings(
					yumaWeidianItemModelMappingService.getYumaWeidianItemModelMappingList(mapping));
		}
		return this.getDao().get(YumaWeidianItemModel.class, id);
	}

	@Override
	public List<YumaWeidianItemModel> getYumaWeidianItemModelList(YumaWeidianItemModel yumaWeidianItemModel) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaWeidianItemModel o where 1=1");
		if (yumaWeidianItemModel.getYumaWeidianItem() != null
				& yumaWeidianItemModel.getYumaWeidianItem().getId() != null) {
			sb.append(" and o.yumaWeidianItem.id = ? ");
			params.add(yumaWeidianItemModel.getYumaWeidianItem().getId());
		}
		if (yumaWeidianItemModel.getName() != null) {
			sb.append(" and o.name like ? ");
			params.add("%" + yumaWeidianItemModel.getName() + "%");
		}
		if (yumaWeidianItemModel.getMappingType() != null) {
			sb.append(" and o.mappingType = ? ");
			params.add(yumaWeidianItemModel.getMappingType());
		}
		List<YumaWeidianItemModel> list = this.getDao().queryHql(sb.toString(),
				params.toArray(new Object[params.size()]));
		if (yumaWeidianItemModel.isShowMappings() && list != null && list.size() != 0) {
			for (YumaWeidianItemModel model : list) {
				YumaWeidianItemModelMapping mapping = new YumaWeidianItemModelMapping();
				mapping.setYumaWeidianItemModel(model);
				model.setYumaWeidianItemModelMappings(
						yumaWeidianItemModelMappingService.getYumaWeidianItemModelMappingList(mapping));
			}
		}
		return list;
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
			YumaWeidianItemModel orginal = getWeidianItemModelById(yumaWeidianItemModel.getId(), false);
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
		YumaWeidianItemModel yumaWeidianItemModel = this.getDao().queryHqlUniqueResult(hql, modelName,
				yumaWeidianItem.getId());
		if (yumaWeidianItemModel == null) {
			yumaWeidianItemModel = new YumaWeidianItemModel();
			yumaWeidianItemModel.setName(modelName);
			yumaWeidianItemModel.setWeidianItem(yumaWeidianItem);
			yumaWeidianItemModel = saveWeidianItemModel(yumaWeidianItemModel);
		}
		return yumaWeidianItemModel;
	}

	@Override
	public void updateWeidianItemModelMappingCount(Integer id, Integer addCount) {
		// TODO
//		YumaWeidianItemModel yumaWeidianItemModel = getWeidianItemModelById(id, false);
//		yumaWeidianItemModel.setMappingCount(yumaWeidianItemModel.getMappingCount() + addCount);
	}

}
