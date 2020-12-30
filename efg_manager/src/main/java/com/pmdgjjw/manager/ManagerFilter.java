package com.pmdgjjw.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @auth jian j w
 * @date 2020/8/5 22:56
 * @Description
 */
@Component
public class ManagerFilter extends ZuulFilter{

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String ostr = "";
        String CommentUri = request.getRequestURI();
        if (CommentUri.equals("/flight/getComment/doInsert") || CommentUri.startsWith("/flight/getComment/doSpit")){
        try {
            ServletInputStream inputStream = request.getInputStream();
            String string = IOUtils.toString(inputStream);

            if (string.length()>0 && string!=null){
                JSONObject map = JSON.parseObject(string);

                Object o = map.get("SysUser");

                if (o!=null){
                    ostr = o.toString();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // 设置响应数据的格式
        context.getResponse().setContentType("application/json;charset=utf8");
        //设置正确响应内容

            String auth = request.getHeader("auth");
            String prefix = "";
            String token = "";
            if (!StringUtils.isEmpty(auth)){
              prefix = env.getProperty("jwt.config.prefix");
              token  = auth.substring(prefix.length());
            }else {
                context.setSendZuulResponse(false);
                Result result = new Result(false,403,"你还没有登录");
                String string = JSON.toJSONString(result);
                context.setResponseBody(string);
                return null;
            }

            Claims claims = null;
            try {
                claims = jwtUtil.parseJWT(token);
            } catch (Exception e) {
                // 不需要将请求转发到后端
                context.setSendZuulResponse(false);
                Result result = new Result(false,403,"登录信息已过期，请重新登录");
                String string1 = JSON.toJSONString(result);
                context.setResponseBody(string1);

                return null;
            }

            if (auth == null || !auth.startsWith(prefix)){
                // 不需要将请求转发到后端
                context.setSendZuulResponse(false);
                Result result = new Result(false,403,"你还没有登录");
                String string = JSON.toJSONString(result);
                context.setResponseBody(string);
                return null;
            }else {
                Object o = claims.get("role");

                if (o != null){
                    String string = o.toString();

                    UserCheck userCheck = JSONObject.parseObject(string, UserCheck.class);
                    UserCheck userCheck1 = JSONObject.parseObject(ostr, UserCheck.class);

                    if (!userCheck.equals(userCheck1)){
                        context.setSendZuulResponse(false);
                        Result result = new Result(false,403,"验证失败");
                        String string1 = JSON.toJSONString(result);
                        context.setResponseBody(string1);
                        return null;
                    }
                } else {
                    context.setSendZuulResponse(false);
                    Result result = new Result(false,403,"登录信息已过期，请重新登录");
                    String string1 = JSON.toJSONString(result);
                    context.setResponseBody(string1);
                    return null;
                }

            }

        }

        return null;
    }
}
