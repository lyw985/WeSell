package com.hodanet.yuma.task;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hodanet.common.constant.CommonConstants;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.constant.SyncStatus;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.service.YumaReceiverService;

public class SyncYumaReceiverTask extends QuartzJobBean {

	private final Logger logger = Logger.getLogger(SyncYumaReceiverTask.class);
	private final int SYNC_NUMBER_PER_TIME = 500;

	private static YumaReceiverService yumaReceiverService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		syncYumaWeidianData();
	}

	public void syncYumaWeidianData() {
		if (CommonConstants.SYSC_RECEIVER_DATA_LOCK != 0) {
			logger.warn("收件人数据正在同步");
			return;
		}
		try {
			logger.debug("开始同步" + SYNC_NUMBER_PER_TIME + "条收件人数据");
			CommonConstants.SYSC_RECEIVER_DATA_LOCK = 1;
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			yumaReceiverService = (YumaReceiverService) webApplicationContext.getBean(YumaReceiverService.class);
			yumaReceiverService.updateUnsycReceiverToSyncing(SYNC_NUMBER_PER_TIME);
			PageData<YumaReceiver> pageData = new PageData<YumaReceiver>();
			pageData.setPageNumber(1);
			pageData.setPageSize(SYNC_NUMBER_PER_TIME);
			YumaReceiver yumaReceiver = new YumaReceiver();
			yumaReceiver.setSyncStatus(SyncStatus.SYNC_ING.getValue());
			pageData = yumaReceiverService.getYumaReceiverByPage(pageData, yumaReceiver);
			if (pageData != null && pageData.getData().size() > 0) {
				logger.info("本次共有" + pageData.getData().size() + "条收件人数据需要同步");
				for (int i = 0; i < pageData.getData().size(); i++) {
					yumaReceiver = pageData.getData().get(i);
					try {
						yumaReceiverService.updateSingleYumaReceiver(yumaReceiver);
						yumaReceiverService.updateYumaReceiverSyncStatus(yumaReceiver.getId(),
								SyncStatus.SYNC_SUCCESS.getValue());
					} catch (Exception e) {
						logger.error(e.getMessage());
						yumaReceiverService.updateYumaReceiverSyncStatus(yumaReceiver.getId(),
								SyncStatus.SYNC_FAILED.getValue());
					}
				}
				logger.info(pageData.getData().size() + "条收件人数据同步完成");
			}
			logger.debug("微店同步完成");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonConstants.SYSC_RECEIVER_DATA_LOCK = 0;
		}
	}
}
