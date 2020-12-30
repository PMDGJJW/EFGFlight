package com.pmdgjjw.efgflight.util;

import com.alibaba.fastjson.JSON;
import com.pmdgjjw.timedeaft.EntityPage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auth jian j w
 * @date 2020/6/28 0:12
 * @Description
 */
public class MapChange {

    public static List<Object> getBean(Map<String,Object> map){

        List<Object> list = new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        entries.stream().forEach(n->{
            try {
                Class aClass = Class.forName(EntityPage.MAP_CHANGE_CLASS_NAME +  n.getKey());
                Map<String,Object> params = (Map<String, Object>) map.get(n.getKey());
//                if (params.containsKey("id" ) && "com.pmdgjjw.efgflight.entity.SysUser".equals(aClass.getName()) ) {
//                    params.put("voId",params.get("id"));
//                    params.remove("id");
//                }
                Object user = JSON.parseObject(JSON.toJSONString(params), (Type) aClass);
                list.add(user);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

}
