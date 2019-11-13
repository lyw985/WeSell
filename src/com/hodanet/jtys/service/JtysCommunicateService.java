package com.hodanet.jtys.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysCommunicate;
import com.hodanet.jtys.entity.po.JtysUser;

public interface JtysCommunicateService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public JtysCommunicate getJtysCommunicateById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<JtysCommunicate> getJtysCommunicateByPage(PageData<JtysCommunicate> pageData, JtysCommunicate jtysCommunicate);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public JtysCommunicate saveJtysCommunicate(JtysCommunicate jtysCommunicate);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteJtysCommunicate(Integer[] ids);

    public void updateJtysCommunicateStatus(Integer id, Integer status);

    /***
     * @param jtysUser
     * @param text
     * @param imageUrl
     * @param audioUrl
     */
    public void createJtysCommunicate(JtysUser jtysUser, String text, String imageUrl, String audioUrl);

}
