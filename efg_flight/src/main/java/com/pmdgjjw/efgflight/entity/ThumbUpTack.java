package com.pmdgjjw.efgflight.entity;

import com.pmdgjjw.efgflight.service.ThumbUpService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @auth jian j w
 * @date 2020/7/13 0:44
 * @Description
 */
public class ThumbUpTack extends QuartzJobBean {

    @Autowired
    ThumbUpService thumbUpService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int i = thumbUpService.upInsert();
        System.out.println(i);
    }
}
