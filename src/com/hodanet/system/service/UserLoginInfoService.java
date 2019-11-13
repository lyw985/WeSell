package com.hodanet.system.service;

import com.hodanet.system.entity.po.UserLoginInfo;

/**
 * @author lance.lengcs
 * @version 2012-8-18 7:17:41
 * 
 * <pre>
 * 	û¼Ϣ ӿ
 * </pre>
 */
public interface UserLoginInfoService {

    /**
     * loginIdѯ
     * 
     * @param loginId
     * @return
     */
    public UserLoginInfo getUserLoginInfoByLoginId(String loginId);

    /**
     * 
     * 
     * @param loginId
     * @return
     */
    public UserLoginInfo saveUserLoginInfo(String loginId);
}
