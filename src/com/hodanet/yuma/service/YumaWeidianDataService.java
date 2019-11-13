package com.hodanet.yuma.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianData;

/**
 * @anthor lyw
 * @originalData 2016-11-11 10:34:32
 */
public interface YumaWeidianDataService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public YumaWeidianData getYumaWeidianDataById(Integer id);

	/**
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaWeidianData> getYumaWeidianDataByPage(PageData<YumaWeidianData> pageData,
			YumaWeidianData yumaWeidianData);

	/**
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaWeidianData saveYumaWeidianData(YumaWeidianData yumaWeidianData);

	public void saveYumaWeidianDatas(YumaWeidianData[] yumaWeidianDatas);

	/**
	 * 
	 * @param ids
	 */
	public void deleteYumaWeidianData(Integer[] ids);

	public void updateYumaWeidianDataSyncStatus(Integer id, Integer syncStatus);

	public void updateUnsycWeidianDataToSyncing(int number);

	public void updateSingleYumaWeidianData(YumaWeidianData yumaWeidianData);

}
