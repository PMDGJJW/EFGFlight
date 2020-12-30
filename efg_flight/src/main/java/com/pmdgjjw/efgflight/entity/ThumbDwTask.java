package com.pmdgjjw.efgflight.entity;

import com.pmdgjjw.efgflight.service.ThumbDwService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @auth jian j w
 * @date 2020/7/13 0:35
 * @Description
 */
public class ThumbDwTask extends QuartzJobBean {

    @Autowired
    ThumbDwService thumbDwService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        int i = thumbDwService.dwInsert();

        System.out.println(i);

    }
}
