package com.hodanet.jtys.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FinishCommunicateTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        finishCommunicate();
    }

    public static void finishCommunicate() {
        System.out.println("finishCommunicate");
    }
}
