package com.pmdgjjw.efguser.service;

import com.pmdgjjw.efguser.entity.WkMsg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 * @auth jian j w
 * @date 2020/8/25 17:00
 * @Description
 */
public class DateCompare implements Comparator<WkMsg> {

    @Override
    public int compare(WkMsg o1, WkMsg o2) {

        long o1time = o1.getDate().getTime();
        long o2time = o2.getDate().getTime();

        if (o1time>o2time){
            return 1;
        }else {
            return -1;
        }

    }
}
