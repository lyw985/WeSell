package com.hodanet.blsh.service;

import com.hodanet.blsh.entity.po.BlshUserContent;
import com.hodanet.common.entity.vo.PageData;

public interface BlshUserContentService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public BlshUserContent getBlshUserContentById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<BlshUserContent> getBlshUserContentByPage(PageData<BlshUserContent> pageData,
                                                              BlshUserContent blshUserContent);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public BlshUserContent saveBlshUserContent(BlshUserContent blshUserContent);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteBlshUserContent(Integer[] ids);

    public void updateBlshUserContentStatus(Integer id, Integer status);

}
