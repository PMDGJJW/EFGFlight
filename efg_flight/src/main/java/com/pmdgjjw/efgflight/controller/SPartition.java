package com.pmdgjjw.efgflight.controller;

import com.pmdgjjw.efgflight.entity.SonPartition;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.efgflight.service.SonPartitionService;
import com.pmdgjjw.efgflight.util.MapChange;
import com.pmdgjjw.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/6/28 12:18
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/son")
public class SPartition {

    @Autowired
    SonPartitionService sonPartitionService;

    @GetMapping("/{id}")
    public Result SonSelect(@PathVariable Long id){

        Result result = sonPartitionService.selectALLByParentID(id);
        return result;

    }

    @PostMapping("/{id}/{pid}")
    public Result SonInsert(@PathVariable Long id ,@PathVariable Long pid ,@RequestBody List<SonPartition> list){

        return sonPartitionService.sonPartitionInsert(id, pid, list);

    }

    @PutMapping
    public Result SonUpdate( @RequestBody Map<String,Object> map){
        List<Object> bean = MapChange.getBean(map);
        Result result = sonPartitionService.sonPartitionUpdate( (SysUser)(bean.get(0)),(SonPartition) (bean.get(1)));
        return result;
    }

    @DeleteMapping
    public Result SonDelete(@RequestBody Map<String,Object> map){
        List<Object> bean = MapChange.getBean(map);
        Result result = sonPartitionService.sonPartitionDelete((SysUser) (bean.get(0)), (SonPartition) (bean.get(1)));
        return result;
    }

}
