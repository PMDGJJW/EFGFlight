package com.pmdgjjw.efgflight.service;

import com.pmdgjjw.efgflight.entity.PartitionDetail;

/**
 * @auth jian j w
 * @date 2020/6/28 16:41
 * @Description
 */
public interface PartitionDetailService extends BaseService<PartitionDetail>{

    Boolean checkInDetail(Long id , Long pid);

}
