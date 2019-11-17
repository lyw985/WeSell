package com.hodanet.yuma.task;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.constant.SyncStatus;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.service.YumaUserService;

public class SyncYumaUserTask extends QuartzJobBean {

	private final Logger logger = Logger.getLogger(SyncYumaUserTask.class);
	private final int SYNC_NUMBER_PER_TIME = 500;

	private static YumaUserService yumaUserService ;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		syncYumaWeidianData();
	}

	public void syncYumaWeidianData() {
		logger.debug("开始同步" + SYNC_NUMBER_PER_TIME + "条数据");
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		yumaUserService = (YumaUserService) webApplicationContext
				.getBean(YumaUserService.class);
		yumaUserService.updateUnsycUserToSyncing(SYNC_NUMBER_PER_TIME);
		PageData<YumaUser> pageData = new PageData<YumaUser>();
		pageData.setPageNumber(1);
		pageData.setPageSize(SYNC_NUMBER_PER_TIME);
		YumaUser yumaUser = new YumaUser();
		yumaUser.setSyncStatus(SyncStatus.INIT.getValue());
		pageData = yumaUserService.getYumaUserByPage(pageData, yumaUser);
		for (int i = 0; i < pageData.getData().size(); i++) {
			yumaUser = pageData.getData().get(i);
			yumaUserService.updateSingleYumaUser(yumaUser);
		}
		logger.debug("微店同步完成");
	}
}
