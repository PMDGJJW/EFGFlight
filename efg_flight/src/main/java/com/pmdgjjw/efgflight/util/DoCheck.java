package com.pmdgjjw.efgflight.util;

import com.pmdgjjw.efgflight.dao.SysUserMapper;
import com.pmdgjjw.efgflight.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auth jian j w
 * @date 2020/6/27 20:11
 * @Description
 */
public class DoCheck {

    public static Boolean TypeCheck(SysUser user){

        if (user.getUserType()==0 || user.getUserType() == 1){
            return true;
        }
        else {
            return false;
        }

    }

}
