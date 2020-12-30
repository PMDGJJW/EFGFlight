package com.pmdgjjw.efgflight.service;

import com.pmdgjjw.efgflight.entity.ParentPartition;
import com.pmdgjjw.efgflight.entity.PartitionInfo;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.entity.Result;

/**
 * @auth jian j w
 * @date 2020/6/25 21:58
 * @Description
 */
public interface ParentPartitonService extends BaseService<ParentPartition>{

    Result insertCheck(SysUser user , ParentPartition partition);

    Result updateCheck(SysUser user, ParentPartition partition);

    Result deleteCheck(SysUser user , ParentPartition partition);

    Result selectAllParentPartition();

    PartitionInfo incrScore(int pid);
}
