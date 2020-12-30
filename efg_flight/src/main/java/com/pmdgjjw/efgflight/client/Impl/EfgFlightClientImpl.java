package com.pmdgjjw.efgflight.client.Impl;

import com.pmdgjjw.efgflight.client.FlightClient;
import com.pmdgjjw.efgflight.entity.Gold;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/8/5 21:11
 * @Description
 */
@Component
public class EfgFlightClientImpl implements FlightClient {

    @Override
    public Result selectUserDetail(Long id) {

        return new Result(false, 403, "熔断器生效了");
    }

    @Override
    public Result spitChangeGold(Map<String, Object> map) {

        return new Result(false, 403, "熔断器生效了");
    }

    @Override
    public int CommentInSertGold(Gold gold) {
        return 0;
    }

    @Override
    public SysUser getDetail(long id) {
        return new SysUser();
    }
}
