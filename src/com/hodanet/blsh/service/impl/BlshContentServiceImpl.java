package com.hodanet.blsh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.blsh.constant.BlshContentStatus;
import com.hodanet.blsh.entity.po.BlshContent;
import com.hodanet.blsh.entity.po.BlshUserContent;
import com.hodanet.blsh.service.BlshContentService;
import com.hodanet.blsh.service.BlshUserContentService;
import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;

/**
 * @anthor lyw
 * @blshContent 2013-5-15 4:03:32
 */
@Service
public class BlshContentServiceImpl extends AbstractDaoService implements BlshContentService {

    @Autowired
    private BlshUserContentService blshUserContentService;

    @Override
    public BlshContent getBlshContentById(Integer id) {
        return this.getDao().get(BlshContent.class, id);
    }

    @Override
    public PageData<BlshContent> getBlshContentByPage(PageData<BlshContent> pageData, BlshContent blshContent) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from BlshContent o where 1=1");
        if (blshContent.getStatus() != null) {
            sb.append(" and o.status = ? ");
            params.add(blshContent.getStatus());
        }
        if (blshContent.getType() != null) {
            sb.append(" and o.type = ? ");
            params.add(blshContent.getType());
        }
        if (blshContent.getMsgType() != null) {
            sb.append(" and o.msgType = ? ");
            params.add(blshContent.getMsgType());
        }
        if (StringUtil.isNotBlank(blshContent.getContent())) {
            sb.append(" and o.content like ? or o.title like ?");
            params.add("%" + blshContent.getContent() + "%");
            params.add("%" + blshContent.getContent() + "%");
        }
        sb.append(" order by o.createTime desc");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public BlshContent saveBlshContent(BlshContent blshContent) {
        if (blshContent == null) {
            return null;
        }
        if (blshContent.getId() == null) {
            blshContent.setStatus(BlshContentStatus.INIT.getValue());
            this.getDao().save(blshContent);
        } else {
            BlshContent orginal = getBlshContentById(blshContent.getId());
            orginal.setType(blshContent.getType());
            orginal.setMsgType(blshContent.getMsgType());
            if (blshContent.getMsgType() == 1) {
                orginal.setContent(blshContent.getContent());
                orginal.setTitle("");
                orginal.setDescription("");
                orginal.setPicUrl("");
                orginal.setUrl("");
            } else {
                orginal.setContent("");
                orginal.setTitle(blshContent.getTitle());
                orginal.setDescription(blshContent.getDescription());
                orginal.setPicUrl(blshContent.getPicUrl());
                orginal.setUrl(blshContent.getUrl());
            }
            orginal.setStatus(0);
            // TODO
        }
        return blshContent;
    }

    @Override
    public void deleteBlshContent(Integer[] ids) {
        this.getDao().delete(BlshContent.class, ids);
    }

    @Override
    public void updateBlshContentStatus(Integer id, Integer status) {
        String hql = "update BlshContent o set o.status = ? where o.id = ?";
        this.getDao().executeUpdate(hql, status, id);
    }

    @Override
    public BlshContent sendBlshContent(String open_id, Integer type) {
        // ȡӦҪ·ݣ·
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.* from blsh_content t , (");
        sb.append(" select a.id,sum(case when b.id is null then 0 else 1 end) ct from blsh_content a");
        sb.append(" left join blsh_user_content b on a.id = b.content_id and b.open_id = ?");
        sb.append(" where a.type = ? and a.status = 1 group by a.id) c");
        sb.append(" where t.id=c.id order by c.ct ,modified_time desc");

        List<Map<String, Object>> mapList = getDao().getJdbcTemplate().queryForList(sb.toString(),
                                                                                    new Object[] { open_id, type });

        if (mapList != null && mapList.size() > 0) {
            Map map = mapList.get(0);
            Integer id = (Integer) map.get("id");
            BlshContent blshContent = getBlshContentById(id);
            BlshUserContent blshUserContent = new BlshUserContent();
            blshUserContent.setOpen_id(open_id);
            blshUserContent.setBlshContent(blshContent);
            blshUserContentService.saveBlshUserContent(blshUserContent);
            return blshContent;
        }
        return null;
    }

}
