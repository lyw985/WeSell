package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.constant.SyncStatus;
import com.hodanet.yuma.constant.YumaUserStatus;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.service.YumaUserService;

/**
 * @anthor lyw
 * @yumaUser 2016-11-11 10:34:32
 */
@Service
public class YumaUserServiceImpl extends AbstractDaoService implements YumaUserService {

	@Override
	public YumaUser getYumaUserById(Integer id) {
		return this.getDao().get(YumaUser.class, id);
	}

	@Override
	public PageData<YumaUser> getYumaUserByPage(PageData<YumaUser> pageData, YumaUser yumaUser) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from YumaUser o where 1=1");
		if (yumaUser.getName() != null) {
			sb.append(" and o.name like ? ");
			params.add("%" + yumaUser.getName() + "%");
		}
		if (yumaUser.getName() != null) {
			sb.append(" and o.phone like ? ");
			params.add("%" + yumaUser.getPhone() + "%");
		}
		if (yumaUser.getStatus() != null) {
			sb.append(" and o.status = ? ");
			params.add(yumaUser.getStatus());
		}
		sb.append(" order by o.createTime desc");
		return this.getDao().queryHqlPageData(sb.toString(), pageData, params.toArray(new Object[params.size()]));
	}

	@Override
	public YumaUser saveYumaUser(YumaUser yumaUser) {
		if (yumaUser == null) {
			return null;
		}
		if (yumaUser.getId() == null) {
			yumaUser.setStatus(YumaUserStatus.USER.getValue());
			this.getDao().save(yumaUser);
		} else {
			YumaUser orginal = getYumaUserById(yumaUser.getId());
			orginal.setName(yumaUser.getName());
			orginal.setPhone(yumaUser.getPhone());
			// TODO
		}
		return yumaUser;
	}

	@Override
	public void deleteYumaUser(Integer[] ids) {
		this.getDao().delete(YumaUser.class, ids);
	}

	@Override
	public void updateYumaUserStatus(Integer id, Integer status) {
		String hql = "update YumaUser o set o.status = ? where o.id = ?";
		this.getDao().executeUpdate(hql, status, id);
	}

	@Override
	public void updateYumaUserSyncStatus(Integer id, Integer syncStatus) {
		String hql = "update YumaUser o set o.syncStatus = ? where o.id = ?";
		this.getDao().executeUpdate(hql, syncStatus, id);
	}

	@Override
	public YumaUser getYumaUserByOpenId(String openId) {
		String hql = "from YumaUser o where o.openId = ?";
		YumaUser yumaUser = this.getDao().queryHqlUniqueResult(hql, openId);
		if (yumaUser == null) {
			yumaUser = new YumaUser();
			yumaUser.setOpenId(openId);
			yumaUser.setStatus(YumaUserStatus.USER.getValue());
			yumaUser = saveYumaUser(yumaUser);
		}
		return yumaUser;
	}

	@Override
	public YumaUser getOrCreateYumaUserByPhone(String name, String phone) {
		String hql = "from YumaUser o where o.phone = ?";
		YumaUser yumaUser = this.getDao().queryHqlUniqueResult(hql, phone);
		if (yumaUser == null) {
			yumaUser = new YumaUser();
			yumaUser.setName(name);
			yumaUser.setPhone(phone);
			yumaUser = saveYumaUser(yumaUser);
		}
		return yumaUser;
	}

	@Override
	public void updateUnsycUserToSyncing(int number) {
		String sql = "update yuma_user a set a.sync_status = ? where a.id in ( select b.id from (SELECT id FROM yuma_user c where c.sync_status = ? ORDER BY c.create_time asc LIMIT 0,?) b)";
		this.getDao().getJdbcTemplate().update(sql, SyncStatus.SYNC_ING.getValue(), SyncStatus.INIT.getValue(), number);
	}

	@Override
	public void updateSingleYumaUser(YumaUser yumaUser) {
		// TODO 解析用户购买行为
		// TODO 更新省市区别名
	}

}
