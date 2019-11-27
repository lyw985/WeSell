package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.constant.YumaItemModelStatus;
import com.hodanet.yuma.entity.po.YumaItemModel;
import com.hodanet.yuma.service.YumaItemModelService;

/**
 * @anthor lyw
 * @yumaItemModel 2016-11-11 10:34:32
 */
@Service
public class YumaItemModelServiceImpl extends AbstractDaoService implements
		YumaItemModelService {

	@Override
	public YumaItemModel getYumaItemModelById(Integer id) {
		return this.getDao().get(YumaItemModel.class, id);
	}

	@Override
	public PageData<YumaItemModel> getYumaItemModelByPage(
			PageData<YumaItemModel> pageData, YumaItemModel yumaItemModel) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaItemModel o where 1=1");
		if (yumaItemModel.getYumaItem() != null
				&& yumaItemModel.getYumaItem().getId() != null) {
			sb.append(" and o.yumaItem.id = ? ");
			params.add(yumaItemModel.getYumaItem().getId());
		}
		if (yumaItemModel.getName() != null) {
			sb.append(" and o.name like ? ");
			params.add("%" + yumaItemModel.getName() + "%");
		}
		if (yumaItemModel.getStatus() != null) {
			sb.append(" and o.status = ? ");
			params.add(yumaItemModel.getStatus());
		}
		sb.append(" order by convert(o.name,'gbk')");
		return this.getDao().queryHqlPageData(sb.toString(), pageData,
				params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaItemModel saveYumaItemModel(YumaItemModel yumaItemModel) {
		if (yumaItemModel == null) {
			return null;
		}
		if (yumaItemModel.getId() == null) {
			yumaItemModel.setStatus(YumaItemModelStatus.AVAILABLE.getValue());
			this.getDao().save(yumaItemModel);
		} else {
			YumaItemModel orginal = getYumaItemModelById(yumaItemModel
					.getId());
			orginal.setName(yumaItemModel.getName());
			// TODO
		}
		return yumaItemModel;
	}

	@Override
	public void deleteYumaItemModel(Integer[] ids) {
		this.getDao().delete(YumaItemModel.class, ids);
	}

	@Override
	public void updateYumaItemModelStatus(Integer id, Integer status) {
		String hql = "update YumaItemModel o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

}
