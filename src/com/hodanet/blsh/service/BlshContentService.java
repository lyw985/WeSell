package com.hodanet.blsh.service;

import com.hodanet.blsh.entity.po.BlshContent;
import com.hodanet.common.entity.vo.PageData;

public interface BlshContentService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public BlshContent getBlshContentById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<BlshContent> getBlshContentByPage(PageData<BlshContent> pageData, BlshContent blshContent);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public BlshContent saveBlshContent(BlshContent blshContent);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteBlshContent(Integer[] ids);

    public void updateBlshContentStatus(Integer id, Integer status);

    public BlshContent sendBlshContent(String open_id, Integer type);

}
