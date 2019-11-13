package com.hodanet.blsh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.blsh.entity.po.BlshUserContent;
import com.hodanet.blsh.service.BlshUserContentService;
import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;

/**
 * @anthor lyw
 * @blshUserContent 2013-5-15 4:03:32
 */
@Service
public class BlshUserContentServiceImpl extends AbstractDaoService implements BlshUserContentService {

    @Override
    public BlshUserContent getBlshUserContentById(Integer id) {
        return this.getDao().get(BlshUserContent.class, id);
    }

    @Override
    public PageData<BlshUserContent> getBlshUserContentByPage(PageData<BlshUserContent> pageData,
                                                              BlshUserContent blshUserContent) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from BlshUserContent o where 1=1");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public BlshUserContent saveBlshUserContent(BlshUserContent blshUserContent) {
        if (blshUserContent == null) {
            return null;
        }
        if (blshUserContent.getId() == null) {
            this.getDao().save(blshUserContent);
        } else {
            BlshUserContent orginal = getBlshUserContentById(blshUserContent.getId());
            // TODO
        }
        return blshUserContent;
    }

    @Override
    public void deleteBlshUserContent(Integer[] ids) {
        this.getDao().delete(BlshUserContent.class, ids);
    }

    @Override
    public void updateBlshUserContentStatus(Integer id, Integer status) {
        String hql = "update BlshUserContent o set o.status = ? where o.id = ?";
        this.getDao().executeUpdate(hql, status, id);
    }

}
