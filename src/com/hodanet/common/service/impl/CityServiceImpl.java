package com.hodanet.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hodanet.common.constant.DisplayStatus;
import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.service.CityService;
import com.hodanet.common.util.StringUtil;

/**
 * @author lance.lengcs
 * @version 2012-8-21 11:04:30
 * 
 *          <pre>
 * 	CityService ʵ
 *          </pre>
 */
@Service
public class CityServiceImpl extends AbstractDaoService implements CityService {

	@Override
	public City getCityById(Integer id) {
		return this.getDao().get(City.class, id);
	}

	@Override
	public PageData<City> getCityByPage(PageData<City> pageData, City city) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from City o where 1=1 ");
		if (city.getProvince() != null && city.getProvince().getId() != null) {
			sb.append(" and o.province.id = ? ");
			params.add(city.getProvince().getId());
		}
		if (city.getDisplayStatus()!=null) {
			sb.append(" and o.displayStatus = ? ");
			params.add(city.getDisplayStatus());
		}
		sb.append(" order by convert(o.name,'gbk')");
		return getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public City saveCity(City city) {
		if (city == null) {
			return null;
		}
		if (city.getId() == null) {
			this.getDao().save(city);
		} else {
			City orginal = getCityById(city.getId());
			orginal.setName(city.getName());
		}

		return city;
	}

	@Override
	public void deleteCitys(String[] ids) {
		this.getDao().delete(City.class, ids);
	}

	@Override
	public List<City> getCityByParentId(String parentId) {
		return null;
	}

	@Override
	public City getOrCreateCityByName(String name, Province province) {
		if (!StringUtil.isNotBlank(name) || province == null || province.getId() == null) {
			return null;
		}
		String hql = "from City o where o.name = ? and o.province.id = ?";
		City city = this.getDao().queryHqlUniqueResult(hql, name, province.getId());
		if (city == null) {
			if (!StringUtils.isNotBlank(name)) {
				throw new RuntimeException("城市不能为空！");
			}
			city = new City();
			city.setName(name);
			city.setProvince(province);
			city.setDisplayStatus(DisplayStatus.DISPLAY.getValue());
			city = saveCity(city);
		}
		return city;
	}

	@Override
	public void updateCityDisplayStatus(Integer id, Integer displayStatus) {
		String hql = "update City o set o.displayStatus=? where o.id = ?";
		this.getDao().executeUpdate(hql, displayStatus, id);
	}
}
