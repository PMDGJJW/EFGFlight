package com.pmdgjjw.efgflight.util;

import com.pmdgjjw.efgflight.entity.MgPage;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/6 17:50
 * @Description
 */
public class MongodbPage {



    public static MgPage getPage(long page,long size,int count){

        MgPage mgPage = new MgPage();

        if (page==1){
            mgPage.setPage(0);
        }else {
            mgPage.setPage((page-1)*size);
        }

        mgPage.setSize(size);

        mgPage.setCount(Math.round((count/size))+1);

        return mgPage;
    }

}
