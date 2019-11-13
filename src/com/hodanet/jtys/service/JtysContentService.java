package com.hodanet.jtys.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysContent;

public interface JtysContentService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public JtysContent getJtysContentById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<JtysContent> getJtysContentByPage(PageData<JtysContent> pageData, JtysContent blshContent);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public JtysContent saveJtysContent(JtysContent blshContent);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteJtysContent(Integer[] ids);

    public void updateJtysContentStatus(Integer id, Integer status);

    public JtysContent sendJtysContent(String open_id, Integer type);

}
