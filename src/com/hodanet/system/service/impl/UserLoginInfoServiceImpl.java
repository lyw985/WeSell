package com.hodanet.system.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.UserLoginInfo;
import com.hodanet.system.service.UserLoginInfoService;

/**
 * @author lance.lengcs
 * @version 2012-8-18 7:26:29
 * 
 * <pre>
 * 	UserLoginInfoService ʵ
 * </pre>
 */
@Service
public class UserLoginInfoServiceImpl extends AbstractDaoService implements UserLoginInfoService {

    @Override
    public UserLoginInfo getUserLoginInfoByLoginId(String loginId) {
        return this.getDao().get(UserLoginInfo.class, loginId);
    }

    @Override
    public UserLoginInfo saveUserLoginInfo(String loginId) {
        if (StringUtil.isBlank(loginId)) {
            return null;
        }

        UserLoginInfo userLoginInfo = getUserLoginInfoByLoginId(loginId);
        if (userLoginInfo == null) {
            userLoginInfo = new UserLoginInfo(loginId);
            userLoginInfo.setLastLoginTime(new Timestamp(new Date().getTime() - 1));
            this.getDao().save(userLoginInfo);
        } else {

            userLoginInfo.setLastLoginTime(userLoginInfo.getModifiedTime());
        }

        return userLoginInfo;
    }
}
