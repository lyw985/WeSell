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
import com.hodanet.yuma.entity.po.YumaWeidianData;
import com.hodanet.yuma.service.YumaWeidianDataService;

public class SyncYumaWeidianDataTask extends QuartzJobBean {

	private final Logger logger = Logger.getLogger(SyncYumaWeidianDataTask.class);
	private final int SYNC_NUMBER_PER_TIME = 2000;

	private static YumaWeidianDataService yumaWeidianDataService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		syncYumaWeidianData();
	}

	public void syncYumaWeidianData() {
		if (CommonConstants.SYSC_WEIDIAN_DATA_LOCK != 0) {
			logger.warn("微店数据正在同步");
			return;
		}
		try {
			logger.debug("微店导入的源数据同步开始.");
			CommonConstants.SYSC_WEIDIAN_DATA_LOCK = 1;
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			yumaWeidianDataService = (YumaWeidianDataService) webApplicationContext
					.getBean(YumaWeidianDataService.class);
			yumaWeidianDataService.updateUnsycWeidianDataToSyncing(SYNC_NUMBER_PER_TIME);
			PageData<YumaWeidianData> pageData = new PageData<YumaWeidianData>();
			pageData.setPageNumber(1);
			pageData.setPageSize(SYNC_NUMBER_PER_TIME);
			YumaWeidianData yumaWeidianData = new YumaWeidianData();
			yumaWeidianData.setSyncStatus(SyncStatus.SYNC_ING.getValue());
			pageData = yumaWeidianDataService.getYumaWeidianDataByPage(pageData, yumaWeidianData);
			if (pageData != null && pageData.getData().size() > 0) {
				logger.info("本次共有" + pageData.getData().size() + "条微店数据需要同步");
				for (int i = 0; i < pageData.getData().size(); i++) {
					yumaWeidianData = pageData.getData().get(i);
					try {
						yumaWeidianDataService.updateSingleYumaWeidianData(yumaWeidianData);
						yumaWeidianDataService.updateYumaWeidianDataSyncStatus(yumaWeidianData.getId(),
								SyncStatus.SYNC_SUCCESS.getValue());
					} catch (Exception e) {
						logger.error(e.getMessage());
						yumaWeidianDataService.updateYumaWeidianDataSyncStatus(yumaWeidianData.getId(),
								SyncStatus.SYNC_FAILED.getValue());
					}
				}
				logger.info( pageData.getData().size() + "条微店数据同步完成");
			}
			logger.debug("微店导入的源数据同步结束.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			CommonConstants.SYSC_WEIDIAN_DATA_LOCK = 0;
		}
	}
}
