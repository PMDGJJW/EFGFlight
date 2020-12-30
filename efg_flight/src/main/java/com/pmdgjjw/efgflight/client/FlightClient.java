package com.pmdgjjw.efgflight.client;

import com.pmdgjjw.efgflight.client.Impl.EfgFlightClientImpl;
import com.pmdgjjw.efgflight.entity.Gold;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/8/5 21:08
 * @Description
 */
@FeignClient(value = "efg-user",fallback = EfgFlightClientImpl.class)
public interface FlightClient {

    @GetMapping("/doUser/getDetail/{id}")
     Result selectUserDetail(@PathVariable("id") Long id);

    @PostMapping("/doUser/spitChangeGold")
     Result spitChangeGold(@RequestBody Map<String,Object> map);

    @PostMapping("/doUser/commentInsertGold")
    int CommentInSertGold(@RequestBody Gold gold);

    @GetMapping("/doUser/getUserDetail/{id}")
    SysUser getDetail(@PathVariable("id") long id);

}
