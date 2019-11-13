package com.hodanet.common.service;

import java.util.List;

import com.hodanet.common.entity.po.TypeConfig;
import com.hodanet.common.entity.vo.PageData;

/**
 * @author lance.lengcs
 * @version 2012-8-21 10:57:59
 * 
 * <pre>
 * ÷ӿ
 * </pre>
 */
public interface TypeConfigService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public TypeConfig getTypeConfigById(String id);

    /**
     * codeѯ¼
     * 
     * @param code
     * @return
     */
    public List<TypeConfig> getTypeConfigByCode(String code);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<TypeConfig> getTypeConfigByPage(PageData<TypeConfig> pageData, String name, String code,
                                                    String module);

    /**
     * 
     * 
     * @param BlshContent
     * @return
     */
    public TypeConfig saveTypeConfig(TypeConfig typeConfig);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteTypeConfigs(String[] ids);
}
