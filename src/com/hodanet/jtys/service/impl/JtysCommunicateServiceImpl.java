package com.hodanet.jtys.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.constant.JtysCommunicateStatus;
import com.hodanet.jtys.constant.JtysCommunicateType;
import com.hodanet.jtys.constant.JtysConstant;
import com.hodanet.jtys.entity.po.JtysCommunicate;
import com.hodanet.jtys.entity.po.JtysUser;
import com.hodanet.jtys.service.JtysCommunicateService;

/**
 * @anthor lyw
 * @jtysCommunicate 2013-5-15 4:03:32
 */
@Service
public class JtysCommunicateServiceImpl extends AbstractDaoService implements JtysCommunicateService {

    @Override
    public JtysCommunicate getJtysCommunicateById(Integer id) {
        return this.getDao().get(JtysCommunicate.class, id);
    }

    @Override
    public PageData<JtysCommunicate> getJtysCommunicateByPage(PageData<JtysCommunicate> pageData,
                                                              JtysCommunicate jtysCommunicate) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from JtysCommunicate o where 1=1");
        if (jtysCommunicate.getProblemId() != null) {
            sb.append(" and o.problemId = ? ");
            params.add(jtysCommunicate.getProblemId());
        }
        if (jtysCommunicate.getIsFirst() != null) {
            sb.append(" and o.isFirst = ? ");
            params.add(jtysCommunicate.getIsFirst());
        }
        if (jtysCommunicate.getType() != null) {
            sb.append(" and o.type = ? ");
            params.add(jtysCommunicate.getType());
        }
        if (jtysCommunicate.getStatus() != null) {
            sb.append(" and o.status = ? ");
            params.add(jtysCommunicate.getStatus());
        }
        if (jtysCommunicate.getUser() != null) {
            sb.append(" and o.user.id = ? ");
            params.add(jtysCommunicate.getUser().getId());
        }
        sb.append(" order by o.createTime desc");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public JtysCommunicate saveJtysCommunicate(JtysCommunicate jtysCommunicate) {
        if (jtysCommunicate == null) {
            return null;
        }
        if (jtysCommunicate.getId() == null) {
            jtysCommunicate.setStatus(JtysCommunicateStatus.INIT.getValue());
            this.getDao().save(jtysCommunicate);
        } else {
            JtysCommunicate orginal = getJtysCommunicateById(jtysCommunicate.getId());
            // TODO
        }
        return jtysCommunicate;
    }

    @Override
    public void deleteJtysCommunicate(Integer[] ids) {
        this.getDao().delete(JtysCommunicate.class, ids);
    }

    @Override
    public void updateJtysCommunicateStatus(Integer id, Integer status) {
        String hql = "update JtysCommunicate o set o.status = ? where o.id = ?";
        this.getDao().executeUpdate(hql, status, id);
    }

    @Override
    public void createJtysCommunicate(JtysUser jtysUser, String text, String imageUrl, String audioUrl) {
        JtysCommunicate jtysCommunicate = new JtysCommunicate();
        jtysCommunicate.setUser(jtysUser);
        jtysCommunicate.setText(text);
        jtysCommunicate.setImageUrl(imageUrl);
        jtysCommunicate.setAudioUrl(audioUrl);
        jtysCommunicate.setIsFirst(1);
        jtysCommunicate.setIsFinish(0);
        jtysCommunicate.setType(JtysCommunicateType.QUESTION.getValue());
        jtysCommunicate.setStatus(JtysCommunicateStatus.INIT.getValue());

        PageData<JtysCommunicate> pageData = new PageData<JtysCommunicate>();
        pageData.setPageNumber(1);
        pageData.setPageSize(1);
        JtysCommunicate condition = new JtysCommunicate();
        condition.setUser(jtysUser);
        pageData = getJtysCommunicateByPage(pageData, condition);
        if (pageData != null && pageData.getData() != null && pageData.getData().size() != 0) {
            JtysCommunicate lastJtysCommunicate = pageData.getData().get(0);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, -JtysConstant.COMMUNICATE_EXPIRE_PERIOD);
            if (lastJtysCommunicate.getCreateTime().after(cal.getTime())) {
                // ׷
                jtysCommunicate.setProblemId(lastJtysCommunicate.getProblemId());
                jtysCommunicate.setIsFirst(0);
            }
        }
        jtysCommunicate = saveJtysCommunicate(jtysCommunicate);
    }

}
