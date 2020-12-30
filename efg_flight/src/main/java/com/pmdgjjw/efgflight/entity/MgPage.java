package com.pmdgjjw.efgflight.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @auth jian j w
 * @date 2020/7/6 17:52
 * @Description
 */
@Document( "test")
public class MgPage {

    private long count;

    private long page;

    private  long size;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "MgPage{" +
                "count=" + count +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
