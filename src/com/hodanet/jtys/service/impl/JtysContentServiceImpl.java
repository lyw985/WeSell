package com.hodanet.jtys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.jtys.constant.JtysContentStatus;
import com.hodanet.jtys.entity.po.JtysContent;
import com.hodanet.jtys.entity.po.JtysUserContent;
import com.hodanet.jtys.service.JtysContentService;
import com.hodanet.jtys.service.JtysUserContentService;

/**
 * @anthor lyw
 * @jtysContent 2013-5-15 4:03:32
 */
@Service
public class JtysContentServiceImpl extends AbstractDaoService implements JtysContentService {

    @Autowired
    private JtysUserContentService jtysUserContentService;

    @Override
    public JtysContent getJtysContentById(Integer id) {
        return this.getDao().get(JtysContent.class, id);
    }

    @Override
    public PageData<JtysContent> getJtysContentByPage(PageData<JtysContent> pageData, JtysContent jtysContent) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from JtysContent o where 1=1");
        if (jtysContent.getStatus() != null) {
            sb.append(" and o.status = ? ");
            params.add(jtysContent.getStatus());
        }
        if (jtysContent.getType() != null) {
            sb.append(" and o.type = ? ");
            params.add(jtysContent.getType());
        }
        if (jtysContent.getMsgType() != null) {
            sb.append(" and o.msgType = ? ");
            params.add(jtysContent.getMsgType());
        }
        if (StringUtil.isNotBlank(jtysContent.getContent())) {
            sb.append(" and o.content like ? or o.title like ?");
            params.add("%" + jtysContent.getContent() + "%");
            params.add("%" + jtysContent.getContent() + "%");
        }
        sb.append(" order by o.createTime desc");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public JtysContent saveJtysContent(JtysContent jtysContent) {
        if (jtysContent == null) {
            return null;
        }
        if (jtysContent.getId() == null) {
            jtysContent.setStatus(JtysContentStatus.INIT.getValue());
            this.getDao().save(jtysContent);
        } else {
            JtysContent orginal = getJtysContentById(jtysContent.getId());
            orginal.setType(jtysContent.getType());
            orginal.setMsgType(jtysContent.getMsgType());
            if (jtysContent.getMsgType() == 1) {
                orginal.setContent(jtysContent.getContent());
                orginal.setTitle("");
                orginal.setDescription("");
                orginal.setPicUrl("");
                orginal.setUrl("");
            } else {
                orginal.setContent("");
                orginal.setTitle(jtysContent.getTitle());
                orginal.setDescription(jtysContent.getDescription());
                orginal.setPicUrl(jtysContent.getPicUrl());
                orginal.setUrl(jtysContent.getUrl());
            }
            orginal.setStatus(0);
            // TODO
        }
        return jtysContent;
    }

    @Override
    public void deleteJtysContent(Integer[] ids) {
        this.getDao().delete(JtysContent.class, ids);
    }

    @Override
    public void updateJtysContentStatus(Integer id, Integer status) {
        String hql = "update JtysContent o set o.status = ? where o.id = ?";
        this.getDao().executeUpdate(hql, status, id);
    }

    @Override
    public JtysContent sendJtysContent(String open_id, Integer type) {
        // ȡӦҪ·ݣ·
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.* from jtys_content t , (");
        sb.append(" select a.id,sum(case when b.id is null then 0 else 1 end) ct from jtys_content a");
        sb.append(" left join jtys_user_content b on a.id = b.content_id and b.open_id = ?");
        sb.append(" where a.type = ? and a.status = 1 group by a.id) c");
        sb.append(" where t.id=c.id order by c.ct ,modified_time desc");

        List<Map<String, Object>> mapList = getDao().getJdbcTemplate().queryForList(sb.toString(),
                                                                                    new Object[] { open_id, type });

        if (mapList != null && mapList.size() > 0) {
            Map map = mapList.get(0);
            Integer id = (Integer) map.get("id");
            JtysContent jtysContent = getJtysContentById(id);
            JtysUserContent jtysUserContent = new JtysUserContent();
            jtysUserContent.setOpen_id(open_id);
            jtysUserContent.setJtysContent(jtysContent);
            jtysUserContentService.saveJtysUserContent(jtysUserContent);
            return jtysContent;
        }
        return null;
    }

}
