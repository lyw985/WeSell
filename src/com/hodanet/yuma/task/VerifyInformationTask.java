package com.hodanet.yuma.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hodanet.common.constant.CommonConstants;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.service.VerifyInformationService;
import com.hodanet.yuma.service.YumaWeidianItemService;

public class VerifyInformationTask extends QuartzJobBean {

	private final Logger logger = Logger.getLogger(VerifyInformationTask.class);

	private static VerifyInformationService verifyInformationService;
	private static YumaWeidianItemService yumaWeidianItemService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		verifyInformation();
	}

	public void verifyInformation() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		verifyInformationService = (VerifyInformationService) webApplicationContext
				.getBean(VerifyInformationService.class);

		yumaWeidianItemService = (YumaWeidianItemService) webApplicationContext.getBean(YumaWeidianItemService.class);

		if (CommonConstants.SYSC_VERIFY_LOCK != 0) {
			return;
		}
		try {
			CommonConstants.SYSC_VERIFY_LOCK = 1;
			YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();
			PageData<YumaWeidianItem> pageData = new PageData<YumaWeidianItem>();
			pageData.setPageSize(Integer.MAX_VALUE);
			yumaWeidianItem.setIsBody(false);
			pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
			if (pageData != null && pageData.getData() != null) {
				List<YumaWeidianItem> list = pageData.getData();
				for (int i = 0; i < list.size(); i++) {
					YumaWeidianItem item = list.get(i);
					logger.info(i + ":" + item.getId() + ":" + item.getBody().getId());
// 					yumaWeidianItemService.updateYumaWeidianItemByBody(item.getId(), item.getBody().getId());
				}
			}
			CommonConstants.SYSC_VERIFY_LOCK = 2;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonConstants.SYSC_VERIFY_LOCK = 0;
		}
	}
}
