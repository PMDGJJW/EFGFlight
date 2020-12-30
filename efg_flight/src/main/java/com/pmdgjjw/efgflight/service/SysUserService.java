package com.pmdgjjw.efgflight.service;

import com.pmdgjjw.efgflight.entity.MailUser;
import com.pmdgjjw.efgflight.entity.SysUser;

/**
 * @auth jian j w
 * @date 2020/6/28 22:17
 * @Description
 */
public interface SysUserService extends BaseService<SysUser>{

    Integer typeCheck(Long id);


}
