package com.hodanet.jtys.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysUser;

public interface JtysUserService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public JtysUser getJtysUserById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<JtysUser> getJtysUserByPage(PageData<JtysUser> pageData, JtysUser jtysUser);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public JtysUser saveJtysUser(JtysUser jtysUser);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteJtysUser(Integer[] ids);

    public void updateJtysUserStatus(Integer id, Integer status);

    public JtysUser getJtysUserByOpenId(String openId);
}
