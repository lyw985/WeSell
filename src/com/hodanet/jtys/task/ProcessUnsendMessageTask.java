package com.hodanet.jtys.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ProcessUnsendMessageTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        processUnsendMessage();
    }

    public static void processUnsendMessage() {
        System.out.println("processUnsendMessage");
    }
}
