package com.pmdgjjw.efguser.util;

/**
 * @auth jian j w
 * @date 2020/7/27 10:04
 * @Description
 */
public class CheckCode {



    public static String getCode(){

        String code = new String();

        for (int i = 0; i <6 ; i++) {
           int c = (int) (Math.random()*9+1);
           code+=c;
        }
        return code;
    }

}
