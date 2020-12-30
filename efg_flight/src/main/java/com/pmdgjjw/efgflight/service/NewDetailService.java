package com.pmdgjjw.efgflight.service;


import com.pmdgjjw.efgflight.entity.NewInfoDetail;

import java.util.List;
import java.util.Map;

/**
 * 消息中心服务
 */
public interface NewDetailService extends BaseService<NewInfoDetail> {

    List<NewInfoDetail> getNewInfoForCommentId(NewInfoDetail newInfoDetail);

    Integer getNewInfoCount(NewInfoDetail newInfoDetail);

    Integer insertNewInfo(NewInfoDetail newInfoDetail);

    Map<String,Object> getPageForSpit(String id);

    Integer setMessageAllRead(List<Long> id);

}
