package com.pmdgjjw.efgflight.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @auth jian j w
 * @date 2020/7/6 18:22
 * @Description
 */
@Document( "test")
public class PageCount {

    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
