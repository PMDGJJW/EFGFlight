package com.pmdgjjw.efguser.interceptor;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.pmdgjjw.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auth jian j w
 * @date 2020/5/15 17:41
 * @Description
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Environment env;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("auth");
        //取出前缀
        String prefix = env.getProperty("jwt.config.prefix");
        if (auth!=null && auth !="null" && auth.startsWith(prefix)){

            String token = auth.substring(prefix.length());
            Claims claims = null;
            //解析token
            claims = jwtUtil.parseJWT(token);

            if (claims != null && "user".equals(claims.get("role"))){
                request.setAttribute("user_auth",claims);
            }
        }

        return true;
    }

}
