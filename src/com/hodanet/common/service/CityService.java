package com.hodanet.common.service;

import java.util.List;

import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.PageData;

/**
 * @author lance.lengcs
 * @version 2012-8-21 10:57:59
 * 
 *          <pre>
 *          </pre>
 */
public interface CityService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public City getCityById(Integer id);

	/**
	 * codeѯ¼
	 * 
	 * @param code
	 * @return
	 */
	public List<City> getCityByParentId(String parentId);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<City> getCityByPage(PageData<City> pageData, City city);
	
	public List<City> getCityList(City city);

	/**
	 * 
	 * 
	 * @param BlshContent
	 * @return
	 */
	public City saveCity(City city);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteCitys(String[] ids);

	public City getOrCreateCityByName(String name, Province province);

	public void updateCityDisplayStatus(Integer id, Integer displayStatus);

	

}
