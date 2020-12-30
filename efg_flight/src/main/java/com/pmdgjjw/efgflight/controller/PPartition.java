package com.pmdgjjw.efgflight.controller;


import com.pmdgjjw.efgflight.entity.ParentPartition;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.efgflight.service.ParentPartitonService;
import com.pmdgjjw.efgflight.util.MapChange;
import com.pmdgjjw.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @auth jian j w
 * @date 2020/6/27 18:09
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/par")
public class PPartition {

    @Autowired
    ParentPartitonService parentPartitonService;

    @GetMapping
    public Result selectAll(){

        Result result = parentPartitonService.selectAllParentPartition();

        return result;

    }
    @PostMapping
    public Result parInsert(@RequestBody Map<String,Object> map ){

        List<Object> list = MapChange.getBean(map);
       return parentPartitonService.insertCheck((SysUser) list.get(0),(ParentPartition) list.get(1));
    }

    @PutMapping
    public Result parUpdate(@RequestBody Map<String,Object> map ){

        List<Object> list = MapChange.getBean(map);
        return parentPartitonService.updateCheck((SysUser) list.get(0),(ParentPartition) list.get(1));

    }

    @DeleteMapping
    public Result parDelete(@RequestBody Map<String,Object> map ){

        List<Object> list = MapChange.getBean(map);
        return parentPartitonService.deleteCheck((SysUser) list.get(0),(ParentPartition) list.get(1));

    }
}
