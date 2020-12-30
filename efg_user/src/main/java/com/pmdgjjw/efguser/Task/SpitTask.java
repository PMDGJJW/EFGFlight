package com.pmdgjjw.efguser.Task;

import com.pmdgjjw.efguser.service.GoldTackService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SpitTask extends QuartzJobBean {


    @Autowired
    private GoldTackService goldTackService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int i = goldTackService.GoldTackInsert();

        System.out.println(i);
    }
}
