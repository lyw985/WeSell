package com.hodanet.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.hodanet.yuma.service.YumaReceiverService;

/**
 * @author lance.lengcs
 * @version 2012-8-21 11:04:30
 * 
 *          <pre>
 * 	ProvinceService ʵ
 *          </pre>
 */
@Service
public class ProvinceServiceImpl extends AbstractDaoService implements ProvinceService {

	@Autowired
	private YumaReceiverService yumaReceiverService;

	@Autowired
	private CityService cityService;

	@Autowired
	private AreaService areaService;

	@Override
	public Province getProvinceById(Integer id) {
		return this.getDao().get(Province.class, id);
	}

	@Override
	public PageData<Province> getProvinceByPage(PageData<Province> pageData, Province province) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from Province o where 1=1 ");
		if (province != null) {
			if (StringUtils.isNotBlank(province.getName())) {
				sb.append(" and o.name like ? ");
				params.add("%" + province.getName() + "%");
			}
			if (province.getBody() != null && province.getBody().getId() != null) {
				sb.append(" and o.body.id = ? ");
				params.add(province.getBody().getId());
			}
			if (province.getIsBody() != null) {
				if (province.getIsBody() == true) {
					sb.append(" and o.body.id = ? ");
					params.add(0);
				}
				if (province.getIsBody() == false) {
					sb.append(" and o.body.id != ? ");
					params.add(0);
				}
			}
		}
		sb.append(" order by convert(o.name,'gbk')");
		return getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public Province saveProvince(Province province) {
		if (province == null) {
			return null;
		}
		if (province.getId() == null) {
			this.getDao().save(province);
		} else {
			Province orginal = getProvinceById(province.getId());
			orginal.setName(province.getName());
		}
		return province;
	}

	@Override
	public void deleteProvinces(String[] ids) {
		this.getDao().delete(Province.class, ids);
	}

	@Override
	public List<Province> getProvinceByParentId(String parentId) {
		return null;
	}

	@Override
	public Province getOrCreateProvinceByName(String name) {
		String hql = "from Province o where o.name = ?";
		Province province = this.getDao().queryHqlUniqueResult(hql, name);
		if (province == null) {
			if (!StringUtils.isNotBlank(name)) {
				throw new RuntimeException("省份不能为空！");
			}
			province = new Province();
			province.setName(name);
			Province body = new Province();
			body.setId(0);
			province.setBody(body);
			province = saveProvince(province);
		} else {
			if (province.getBody() != null && province.getBody().getId() != null && province.getBody().getId() != 0) {
				province = province.getBody();
			}
		}
		return province;
	}

	@Override
	public List<Province> getBodyProvinces() {
		PageData<Province> pageData = new PageData<Province>();
		pageData.setPageSize(Integer.MAX_VALUE);
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from Province o where 1=1");
		sb.append(" and o.body.id = ? ");
		params.add(0);
		sb.append(" order by convert(o.name,'gbk')");
		pageData = this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
		if (pageData != null && pageData.getData() != null) {
			return pageData.getData();
		} else {
			return new ArrayList<Province>();
		}
	}

	@Override
	public void updateProvinceByBody(Integer id, Integer bodyId) {
		if (bodyId == null) {
			return;
		}
		String hql = "update Province o set o.body.id = ? where o.id = ?";
		this.getDao().executeUpdate(hql, bodyId, id);
		if (bodyId != 0) {
			Province province = getProvinceById(id);
			Province body = province.getBody();
			List<City> citys = province.getCitys();
			if (citys != null) {
				for (City city : citys) {
					City newCity = cityService.getOrCreateCityByName(city.getName(), body);
					List<Area> areas = city.getAreas();
					if (areas != null) {
						for (Area area : areas) {
							Area newArea = areaService.getOrCreateAreaByName(area.getName(), newCity);
							yumaReceiverService.updateBatchYumaReceiverArea(area.getId(), newArea.getId());
						}
					}
					yumaReceiverService.updateBatchYumaReceiverCity(city.getId(), newCity.getId());
				}
			}
			yumaReceiverService.updateBatchYumaReceiverProvince(province.getId(), bodyId);
		}
	}
}
