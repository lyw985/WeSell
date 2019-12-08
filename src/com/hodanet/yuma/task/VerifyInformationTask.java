package com.hodanet.yuma.task;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hodanet.yuma.service.VerifyInformationService;

public class VerifyInformationTask extends QuartzJobBean {

	private final Logger logger = Logger.getLogger(VerifyInformationTask.class);

	private static VerifyInformationService verifyInformationService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		verifyInformation();
	}

	public void verifyInformation() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		verifyInformationService = (VerifyInformationService) webApplicationContext
				.getBean(VerifyInformationService.class);
		// TODO
	}
}
