package com.pmdgjjw.efgflight.controller;

import com.pmdgjjw.efgflight.entity.PartitionInfo;
import com.pmdgjjw.efgflight.service.ParentPartitonService;
import com.pmdgjjw.efgflight.service.SonPartitionService;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/15 9:52
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/partition")
public class PartitionColtroller {

    @Autowired
    private ParentPartitonService parentPartitonService;

    @Autowired
    private SonPartitionService sonPartitionService;

    @GetMapping("/{pid}")
    public Result incrPPartition(@PathVariable int pid){
        PartitionInfo partitionInfo = parentPartitonService.incrScore(pid);
        return ResultSelect.Select(partitionInfo);
    }

    @GetMapping("/sonPartitionInfo/{pid}")
    public Result sonPartitionInfoSelect(@PathVariable int pid){

        List<PartitionInfo> partitionInfos = sonPartitionService.sonPartitionInfoSelect(pid);

        return ResultSelect.Select(partitionInfos);
    }

}
