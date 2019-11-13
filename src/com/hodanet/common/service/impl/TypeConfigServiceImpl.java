package com.hodanet.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.po.TypeConfig;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.service.TypeConfigService;
import com.hodanet.common.util.StringUtil;

/**
 * @author lance.lengcs
 * @version 2012-8-21 11:04:30
 * 
 * <pre>
 * 	TypeConfigService ʵ
 * </pre>
 */
@Service
public class TypeConfigServiceImpl extends AbstractDaoService implements TypeConfigService {

    @Override
    public TypeConfig getTypeConfigById(String id) {
        return this.getDao().get(TypeConfig.class, id);
    }

    @Override
    public List<TypeConfig> getTypeConfigByCode(String code) {
        if (StringUtil.isBlank(code)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("from TypeConfig o where code = '");
        sb.append(code);
        sb.append("' order by o.ordering");
        return getDao().queryHql(sb.toString());
    }

    @Override
    public PageData<TypeConfig> getTypeConfigByPage(PageData<TypeConfig> pageData, String name, String code,
                                                    String module) {
        StringBuilder sb = new StringBuilder();
        sb.append("from TypeConfig o where 1=1 ");
        if (StringUtil.isNotBlank(name)) {
            sb.append(" and o.name like '%").append(name).append("%'");
        }

        if (StringUtil.isNotBlank(code)) {
            sb.append(" and o.code like '%").append(code).append("%'");
        }

        if (StringUtil.isNotBlank(module)) {
            sb.append(" and o.module like '%").append(module).append("%'");
        }
        sb.append(" order by o.code,o.ordering");

        return getDao().queryHqlPageData(sb.toString(), pageData);
    }

    @Override
    public TypeConfig saveTypeConfig(TypeConfig typeConfig) {
        if (typeConfig == null) {
            return null;
        }
        if (StringUtil.isBlank(typeConfig.getId())) {
            this.getDao().save(typeConfig);
        } else {
            TypeConfig orginal = getTypeConfigById(typeConfig.getId());

            orginal.setName(typeConfig.getName());
            orginal.setCode(typeConfig.getCode());
            orginal.setModule(typeConfig.getModule());
            orginal.setRemark(typeConfig.getRemark());
        }

        return typeConfig;
    }

    @Override
    public void deleteTypeConfigs(String[] ids) {
        this.getDao().delete(TypeConfig.class, ids);
    }
}
