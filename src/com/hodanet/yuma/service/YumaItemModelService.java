package com.hodanet.yuma.service;

import java.util.List;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaItemModel;

/**
 * @anthor lyw
 * @yumaItemModel 2016-11-11 10:34:32
 */
public interface YumaItemModelService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public YumaItemModel getYumaItemModelById(Integer id);

    /**
     * 
     * @param pageData
     * @return
     */
    public PageData<YumaItemModel> getYumaItemModelByPage(PageData<YumaItemModel> pageData, YumaItemModel yumaItemModel);
    
    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public List<YumaItemModel> getYumaItemModelList(YumaItemModel yumaItemModel);


    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public YumaItemModel saveYumaItemModel(YumaItemModel yumaItemModel);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteYumaItemModel(Integer[] ids);

    public void updateYumaItemModelStatus(Integer id, Integer status);
    
}
