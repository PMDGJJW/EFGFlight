package com.pmdgjjw.efgflight.service.Impl;

import com.pmdgjjw.efgflight.dao.PartitionDetailMapper;
import com.pmdgjjw.efgflight.entity.PartitionDetail;
import com.pmdgjjw.efgflight.service.PartitionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auth jian j w
 * @date 2020/6/28 16:42
 * @Description
 */
@Service
@Transactional
public class PartitionDetailImpl extends BaseImpl<PartitionDetail> implements PartitionDetailService {

    @Autowired
    PartitionDetailMapper partitionDetailMapper;

    @Override
    public Boolean checkInDetail(Long id, Long pid) {
        PartitionDetail detail = partitionDetailMapper.checkInDetail(id, pid);
        if (detail ==null){
            return true;
        }else {
            return false;
        }
    }
}
