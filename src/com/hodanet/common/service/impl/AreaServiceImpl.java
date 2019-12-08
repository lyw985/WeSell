package com.hodanet.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.service.AreaService;
import com.hodanet.common.util.StringUtil;

/**
 * @author lance.lengcs
 * @version 2012-8-21 11:04:30
 * 
 *          <pre>
 * 	AreaService ʵ
 *          </pre>
 */
@Service
public class AreaServiceImpl extends AbstractDaoService implements AreaService {

	@Override
	public Area getAreaById(Integer id) {
		return this.getDao().get(Area.class, id);
	}

	@Override
	public PageData<Area> getAreaByPage(PageData<Area> pageData, Area area) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from Area o where 1=1 ");
		if (area.getCity() != null && area.getCity().getId() != null) {
			sb.append(" and o.city.id = ? ");
			params.add(area.getCity().getId());
		}
		sb.append(" order by convert(o.name,'gbk')");
		return getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public Area saveArea(Area area) {
		if (area == null) {
			return null;
		}
		if (area.getId() == null) {
			this.getDao().save(area);
		} else {
			Area orginal = getAreaById(area.getId());
			orginal.setName(area.getName());
		}

		return area;
	}

	@Override
	public void deleteAreas(String[] ids) {
		this.getDao().delete(Area.class, ids);
	}

	@Override
	public List<Area> getAreaByParentId(String parentId) {
		return null;
	}

	@Override
	public Area getOrCreateAreaByName(String areaName, City city) {
		if (city == null || city.getId() == null) {
			return null;
		}
		if (!StringUtil.isNotBlank(areaName)) {
			areaName = "未知";
		}
		String hql = "from Area o where o.name = ? and o.city.id = ?";
		Area area = this.getDao().queryHqlUniqueResult(hql, areaName, city.getId());
		if (area == null) {
			area = new Area();
			area.setName(areaName);
			area.setCity(city);
			area = saveArea(area);
		}
		return area;
	}
}
