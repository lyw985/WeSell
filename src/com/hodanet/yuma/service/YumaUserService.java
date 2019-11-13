package com.hodanet.yuma.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaUser;

/**
 * @anthor lyw
 * @yumaUser 2016-11-11 10:34:32
 */
public interface YumaUserService {

	/**
	 * idѯ¼
	 * 
	 * @param id
	 * @return
	 */
	public YumaUser getYumaUserById(Integer id);

	/**
	 * ҳѯ
	 * 
	 * @param pageData
	 * @return
	 */
	public PageData<YumaUser> getYumaUserByPage(PageData<YumaUser> pageData, YumaUser yumaUser);

	/**
	 * 
	 * @param ResDept
	 * @return
	 */
	public YumaUser saveYumaUser(YumaUser yumaUser);

	/**
	 * 
	 * @param ids
	 */
	public void deleteYumaUser(Integer[] ids);

	public void updateYumaUserStatus(Integer id, Integer status);
	
	public void updateYumaUserSyncStatus(Integer id, Integer syncStatus);

	public YumaUser getYumaUserByOpenId(String openId);

	public YumaUser getOrCreateYumaUserByPhone(String name, String phone);
	
	public void updateUnsycUserToSyncing(int number);

	public void updateSingleYumaUser(YumaUser yumaUser);
}
