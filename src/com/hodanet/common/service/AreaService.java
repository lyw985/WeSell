package com.hodanet.common.service;

import java.util.List;

import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.vo.PageData;

/**
 * @author lance.lengcs
 * @version 2012-8-21 10:57:59
 * 
 *          <pre>
 *          </pre>
 */
public interface AreaService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public Area getAreaById(Integer id);

	/**
	 * codeѯ¼
	 * 
	 * @param code
	 * @return
	 */
	public List<Area> getAreaByParentId(String parentId);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<Area> getAreaByPage(PageData<Area> pageData, Area area);

	/**
	 * 
	 * 
	 * @param BlshContent
	 * @return
	 */
	public Area saveArea(Area area);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteAreas(String[] ids);

	public Area getOrCreateAreaByName(String areaName, City city);

}
