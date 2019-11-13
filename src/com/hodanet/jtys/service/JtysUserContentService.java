package com.hodanet.jtys.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysUserContent;

public interface JtysUserContentService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public JtysUserContent getJtysUserContentById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<JtysUserContent> getJtysUserContentByPage(PageData<JtysUserContent> pageData,
                                                              JtysUserContent blshUserContent);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public JtysUserContent saveJtysUserContent(JtysUserContent blshUserContent);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteJtysUserContent(Integer[] ids);

    public void updateJtysUserContentStatus(Integer id, Integer status);

}
