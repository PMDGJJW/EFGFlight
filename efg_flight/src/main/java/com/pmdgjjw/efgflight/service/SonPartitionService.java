package com.pmdgjjw.efgflight.service;

import com.pmdgjjw.efgflight.entity.PartitionInfo;
import com.pmdgjjw.efgflight.entity.SonPartition;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.entity.Result;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/6/28 12:26
 * @Description
 */
public interface SonPartitionService extends BaseService<SonPartition> {

    Result selectALLByParentID(Long id);

    Result sonPartitionInsert(Long id,Long pid,List<SonPartition> list);

    Result sonPartitionUpdate(SysUser user, SonPartition sonPartition);

    Result sonPartitionDelete(SysUser user, SonPartition sonPartition);

    List<PartitionInfo> sonPartitionInfoSelect(int pid);
}
