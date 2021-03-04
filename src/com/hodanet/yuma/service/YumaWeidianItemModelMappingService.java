package com.hodanet.yuma.service;

import java.util.List;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItemModelMapping;

/**
 * @anthor lyw
 * @yumaWeidianItemModelMapping 2016-11-11 10:34:32
 */
public interface YumaWeidianItemModelMappingService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public YumaWeidianItemModelMapping getYumaWeidianItemModelMappingById(Integer id);

	/**
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaWeidianItemModelMapping> getYumaWeidianItemModelMappingByPage(
			PageData<YumaWeidianItemModelMapping> pageData,
			YumaWeidianItemModelMapping yumaWeidianItemModelMapping);

	public List<YumaWeidianItemModelMapping> getYumaWeidianItemModelMappingList(
			YumaWeidianItemModelMapping yumaWeidianItemModelMapping);

	/**
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaWeidianItemModelMapping saveYumaWeidianItemModelMapping(
			YumaWeidianItemModelMapping yumaWeidianItemModelMapping);

	/**
	 * 
	 * @param ids
	 */
	public void deleteYumaWeidianItemModelMapping(Integer id);

	public void updateYumaWeidianItemModelMappingPercent(Integer yumaWeidianItemModelId, Integer... exceptIds);
	
	public void updateYumaWeidianItemDetail(YumaWeidianItemModelMapping yumaWeidianItemModelMapping);

}
