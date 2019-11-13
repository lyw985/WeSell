package com.hodanet.jtys.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class NoticeSoonExpireUserTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        noticeSoonExpireUser();
    }

    public static void noticeSoonExpireUser() {
        System.out.println("noticeSoonExpireUser");
    }
}
