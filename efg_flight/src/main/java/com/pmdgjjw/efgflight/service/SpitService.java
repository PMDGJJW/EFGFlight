package com.pmdgjjw.efgflight.service;

import com.pmdgjjw.efgflight.entity.Spit;
import com.pmdgjjw.efgflight.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/7/12 21:19
 * @Description
 */
public interface SpitService  {

    int doThumbUp(String sid , String uid);

    int delThumbUp(String sid , String uid);

    int doThumbDw(String sid , String uid);

    int delThumbDw(String sid , String uid);

    Map<String,Object> selectSpit(int page, int uid, int cid,int size);

    int countByPid(int pid);

    int countByVisites(int cid);


}
