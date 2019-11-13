package com.hodanet.jtys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.constant.JtysUserStatus;
import com.hodanet.jtys.entity.po.JtysUser;
import com.hodanet.jtys.service.JtysUserService;

/**
 * @anthor lyw
 * @jtysUser 2013-5-15 4:03:32
 */
@Service
public class JtysUserServiceImpl extends AbstractDaoService implements JtysUserService {

    @Override
    public JtysUser getJtysUserById(Integer id) {
        return this.getDao().get(JtysUser.class, id);
    }

    @Override
    public PageData<JtysUser> getJtysUserByPage(PageData<JtysUser> pageData, JtysUser jtysUser) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("from JtysUser o where 1=1");
        if (jtysUser.getStatus() != null) {
            sb.append(" and o.status = ? ");
            params.add(jtysUser.getStatus());
        }
        sb.append(" order by o.createTime desc");
        return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
    }

    @Override
    public JtysUser saveJtysUser(JtysUser jtysUser) {
        if (jtysUser == null) {
            return null;
        }
        if (jtysUser.getId() == null) {
            jtysUser.setStatus(JtysUserStatus.GUEST.getValue());
            this.getDao().save(jtysUser);
        } else {
            JtysUser orginal = getJtysUserById(jtysUser.getId());
            // TODO
        }
        return jtysUser;
    }

    @Override
    public void deleteJtysUser(Integer[] ids) {
        this.getDao().delete(JtysUser.class, ids);
    }

    @Override
    public void updateJtysUserStatus(Integer id, Integer status) {
        String hql = "update JtysUser o set o.status = ? where o.id = ?";
        this.getDao().executeUpdate(hql, status, id);
    }

    @Override
    public JtysUser getJtysUserByOpenId(String openId) {
        String hql = "from JtysUser o where o.openId = ?";
        JtysUser jtysUser = this.getDao().queryHqlUniqueResult(hql, openId);
        // ȡûϢû򴴽û
        if (jtysUser == null) {
            jtysUser = new JtysUser();
            jtysUser.setOpenId(openId);
            jtysUser.setCanFree(1);
            jtysUser.setStatus(JtysUserStatus.GUEST.getValue());
            jtysUser.setChunyuRegs(0);
            jtysUser = saveJtysUser(jtysUser);
        }
        return jtysUser;
    }

}
