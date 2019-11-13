package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.service.YumaOrderItemService;
import com.hodanet.yuma.service.YumaOrderService;
import com.hodanet.yuma.service.YumaWeidianItemModelService;
import com.hodanet.yuma.service.YumaWeidianItemService;

/**
 * @anthor lyw
 * @yumaWeidianItem 2016-11-11 10:34:32
 */
@Service
public class YumaWeidianItemServiceImpl extends AbstractDaoService implements YumaWeidianItemService {

	@Autowired
	private YumaWeidianItemModelService yumaWeidianItemModelService;

	@Autowired
	private YumaOrderService yumaOrderService;

	@Autowired
	private YumaOrderItemService yumaWeidianItemService;

	@Override
	public YumaWeidianItem getYumaWeidianItemById(Integer id) {
		return this.getDao().get(YumaWeidianItem.class, id);
	}

	@Override
	public PageData<YumaWeidianItem> getYumaWeidianItemByPage(PageData<YumaWeidianItem> pageData,
			YumaWeidianItem yumaWeidianItem) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaWeidianItem o where 1=1");
		if (yumaWeidianItem.getName() != null) {
			sb.append(" and o.name like ? ");
			params.add("%" + yumaWeidianItem.getName() + "%");
		}
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaWeidianItem saveYumaWeidianItem(YumaWeidianItem yumaYumaWeidianItem) {
		if (yumaYumaWeidianItem == null) {
			return null;
		}
		if (yumaYumaWeidianItem.getId() == null) {
			this.getDao().save(yumaYumaWeidianItem);
		} else {
			YumaWeidianItem orginal = getYumaWeidianItemById(yumaYumaWeidianItem.getId());
			// TODO
		}
		return yumaYumaWeidianItem;
	}

	@Override
	public void deleteYumaWeidianItem(Integer[] ids) {
		this.getDao().delete(YumaWeidianItem.class, ids);
	}

	@Override
	public void updateYumaWeidianItemStatus(Integer id, Integer status) {
		String hql = "update YumaWeidianItem o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

	@Override
	public YumaWeidianItem getOrCreateYumaWeidianItemByName(String name) {
		String hql = "from YumaWeidianItem o where o.name = ?";
		YumaWeidianItem yumaYumaWeidianItem = this.getDao().queryHqlUniqueResult(hql, name);
		if (yumaYumaWeidianItem == null) {
			yumaYumaWeidianItem = new YumaWeidianItem();
			yumaYumaWeidianItem.setName(name);
			yumaYumaWeidianItem = saveYumaWeidianItem(yumaYumaWeidianItem);
		} else {
			if (yumaYumaWeidianItem.getBody() != null && yumaYumaWeidianItem.getBody().getId() != null
					&& yumaYumaWeidianItem.getBody().getId() != 0) {
				yumaYumaWeidianItem = yumaYumaWeidianItem.getBody();
			}
		}
		return yumaYumaWeidianItem;
	}

	@Override
	public void updateYumaWeidianItemByBody(Integer id, Integer bodyId) {
		if (bodyId == null) {
			return;
		}
		String hql = "update YumaWeidianItem o set o.body.id = ? where o.id = ?";
		this.getDao().executeUpdate(hql, bodyId, id);
		if (bodyId != 0) {
			YumaWeidianItem yumaWeidianItem = getYumaWeidianItemById(id);
			YumaWeidianItem body = yumaWeidianItem.getBody();
			List<YumaWeidianItemModel> yumaWeidianItemModels = yumaWeidianItem.getYumaWeidianItemModels();
			if (yumaWeidianItemModels != null) {
				for (YumaWeidianItemModel model : yumaWeidianItemModels) {
					YumaWeidianItemModel yumaWeidianItemModel = yumaWeidianItemModelService
							.getOrCreateWeidianItemModelByName(model.getName(), body);
					yumaWeidianItemService.updateBatchYumaOrderWeidianItemModel(model.getId(),
							yumaWeidianItemModel.getId());
				}
			}
		}
	}

	@Override
	public List<YumaWeidianItem> getBodyYumaWeidianItems(YumaWeidianItem yumaWeidianItem) {
		PageData<YumaWeidianItem> pageData = new PageData<YumaWeidianItem>();
		YumaWeidianItem body = new YumaWeidianItem();
		body.setId(0);
		yumaWeidianItem.setBody(body);
		pageData.setPageSize(Integer.MAX_VALUE);
		pageData = getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		if (pageData != null && pageData.getData() != null) {
			return pageData.getData();
		} else {
			return new ArrayList<YumaWeidianItem>();
		}
	}

}
