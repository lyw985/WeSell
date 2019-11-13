package com.hodanet.yuma.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;

/**
 * @anthor lyw
 * @weidianItemModel 2016-11-11 10:34:32
 */
public interface YumaWeidianItemModelService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public YumaWeidianItemModel getWeidianItemModelById(Integer id);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaWeidianItemModel> getWeidianItemModelByPage(PageData<YumaWeidianItemModel> pageData,
			YumaWeidianItemModel yumaWeidianItemModel);

	/**
	 * 
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaWeidianItemModel saveWeidianItemModel(YumaWeidianItemModel yumaWeidianItemModel);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteWeidianItemModel(Integer[] ids);

	public void updateWeidianItemModelStatus(Integer id, Integer status);

	public YumaWeidianItemModel getOrCreateWeidianItemModelByName(String modelName, YumaWeidianItem yumaWeidianItem);

}
