package com.hodanet.jtys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysUserContent;
import com.hodanet.jtys.service.JtysUserContentService;

/**
 * @anthor lyw
 * @JtysUserContent 2013-5-15 4:03:32
 */
@Service
public class JtysUserContentServiceImpl extends AbstractDaoService implements JtysUserContentService {

    @Override
    public JtysUserContent getJtysUserContentById(Integer id) {
        return this.getDao().get(JtysUserContent.class, id);
    }

    @Override
    public PageData<JtysUserContent> getJtysUserContentByPage(PageData<JtysUserContent> pageData,
                                                              JtysUserContent JtysUserContent) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from JtysUserContent o where 1=1");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public JtysUserContent saveJtysUserContent(JtysUserContent JtysUserContent) {
        if (JtysUserContent == null) {
            return null;
        }
        if (JtysUserContent.getId() == null) {
            this.getDao().save(JtysUserContent);
        } else {
            JtysUserContent orginal = getJtysUserContentById(JtysUserContent.getId());
            // TODO
        }
        return JtysUserContent;
    }

    @Override
    public void deleteJtysUserContent(Integer[] ids) {
        this.getDao().delete(JtysUserContent.class, ids);
    }

    @Override
    public void updateJtysUserContentStatus(Integer id, Integer status) {
        String hql = "update JtysUserContent o set o.status = ? where o.id = ?";
        this.getDao().executeUpdate(hql, status, id);
    }

}
