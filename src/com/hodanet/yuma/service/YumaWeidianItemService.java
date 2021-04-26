package com.hodanet.yuma.service;

import java.util.List;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItem;

public interface YumaWeidianItemService {

	public List<YumaWeidianItem> getBodyYumaWeidianItems(YumaWeidianItem yumaWeidianItem, Integer bodyId);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public YumaWeidianItem getYumaWeidianItemById(Integer id);

	public YumaWeidianItem getOrCreateYumaWeidianItemByName(String name);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaWeidianItem> getYumaWeidianItemByPage(PageData<YumaWeidianItem> pageData,
			YumaWeidianItem yumaYumaWeidianItem);

	/**
	 * 
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaWeidianItem saveYumaWeidianItem(YumaWeidianItem yumaYumaWeidianItem);

	/**
	 * ɾ
	 * 
	 * @param ids
	 */
	public void deleteYumaWeidianItem(Integer[] ids);

	public void updateYumaWeidianItemStatus(Integer id, Integer status);

	public void updateYumaWeidianItemByBody(Integer id, Integer bodyId);
	
	public void updateYumaWeidianItemDoneStatus(Integer id);
	

}
