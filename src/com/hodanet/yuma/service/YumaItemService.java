package com.hodanet.yuma.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaItem;

/**
 * @anthor lyw
 * @yumaItem 2016-11-11 10:34:32
 */
public interface YumaItemService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public YumaItem getYumaItemById(Integer id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<YumaItem> getYumaItemByPage(PageData<YumaItem> pageData, YumaItem yumaItem);
    
    public PageData<YumaItem> getYumaItemsForSelect();

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public YumaItem saveYumaItem(YumaItem yumaItem);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteYumaItem(Integer[] ids);

    public void updateYumaItemStatus(Integer id, Integer status);

}
