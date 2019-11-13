package com.hodanet.common.service;

import java.util.List;

import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.PageData;

/**
 * @author lance.lengcs
 * @version 2012-8-21 10:57:59
 * 
 *          <pre>
 *          </pre>
 */
public interface ProvinceService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public Province getProvinceById(Integer id);

	/**
	 * codeѯ¼
	 * 
	 * @param code
	 * @return
	 */
	public List<Province> getProvinceByParentId(String parentId);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<Province> getProvinceByPage(PageData<Province> pageData, Province province);

	/**
	 * 
	 * 
	 * @param BlshContent
	 * @return
	 */
	public Province saveProvince(Province province);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteProvinces(String[] ids);

	public Province getOrCreateProvinceByName(String name);

	public List<Province> getBodyProvinces();

	public void updateProvinceByBody(Integer id, Integer bodyId);

}
