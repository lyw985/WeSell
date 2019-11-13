package com.hodanet.jtys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysDoctor;
import com.hodanet.jtys.service.JtysDoctorService;

/**
 * @anthor lyw
 * @jtysDoctor 2013-5-15 4:03:32
 */
@Service
public class JtysDoctorServiceImpl extends AbstractDaoService implements JtysDoctorService {

    @Override
    public JtysDoctor getJtysDoctorById(String id) {
        return this.getDao().get(JtysDoctor.class, id);
    }

    @Override
    public PageData<JtysDoctor> getJtysDoctorByPage(PageData<JtysDoctor> pageData, JtysDoctor jtysDoctor) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from JtysDoctor o where 1=1");
        sb.append(" order by o.createTime desc");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public JtysDoctor saveJtysDoctor(JtysDoctor jtysDoctor) {
        if (jtysDoctor == null) {
            return null;
        }
        JtysDoctor orginal = getJtysDoctorById(jtysDoctor.getId());
        if (orginal == null) {
            this.getDao().save(jtysDoctor);
        } else {
            // TODO
        }
        return jtysDoctor;
    }

    @Override
    public void deleteJtysDoctor(Integer[] ids) {
        this.getDao().delete(JtysDoctor.class, ids);
    }

}
