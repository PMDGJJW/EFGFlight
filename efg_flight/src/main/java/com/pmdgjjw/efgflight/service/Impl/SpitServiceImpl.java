package com.pmdgjjw.efgflight.service.Impl;

import com.mongodb.client.result.UpdateResult;
import com.pmdgjjw.efgflight.entity.MgPage;
import com.pmdgjjw.efgflight.entity.Spit;
import com.pmdgjjw.efgflight.service.SpitService;
import com.pmdgjjw.efgflight.util.MongodbPage;
import com.pmdgjjw.efgflight.util.TimeCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/7/12 21:20
 * @Description
 */
@Service
@Transactional
public class SpitServiceImpl  implements SpitService {

//    @Autowired
//    SpitResp spitMapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public int doThumbUp(String sid , String uid) {
        String o = (String) redisTemplate.opsForHash().get("SpitThumbUp", sid+"::"+uid);
        if ( o ==null ||  "0".equals(o)  ){
            Query query = new Query();

            query.addCriteria(Criteria.where("_id").is(sid));

            Update update = new Update();

            update.inc("thumbup",1);

            UpdateResult test = mongoTemplate.updateFirst(query, update, "test");

            if (test !=null){

                redisTemplate.opsForHash().put("SpitThumbUp", sid+"::"+uid, "1");
                return 1;

            }else {
                return 0;
            }
        }else {
            return 2;
        }




    }

    @Override
    public int doThumbDw(String sid , String uid) {
        String o = (String) redisTemplate.opsForHash().get("SpitThumbDw", sid+"::"+uid);
        if ( o == null || "0".equals(o) ){
            Query query = new Query();

            query.addCriteria(Criteria.where("_id").is(sid));

            Update update = new Update();

            update.inc("thumbdw",1);

            UpdateResult test = mongoTemplate.updateFirst(query, update, "test");

            if (test !=null){
                redisTemplate.opsForHash().put("SpitThumbDw", sid+"::"+uid, "1");
                return 1;
            }else {
                return 0;
            }
        }else {
            return 2;
        }

    }

    @Override
    public int delThumbUp(String sid , String uid) {
        String o = (String) redisTemplate.opsForHash().get("SpitThumbUp", sid+"::"+uid);

        if ( o != null && "1".equals(o) ){
            Query query = new Query();

            query.addCriteria(Criteria.where("_id").is(sid));

            Update update = new Update();

            update.inc("thumbup",-1);

            UpdateResult test = mongoTemplate.updateFirst(query, update, "test");

            if (test !=null){
                redisTemplate.opsForHash().put("SpitThumbUp", sid+"::"+uid,"0");
                return 1;

            }else {
                return 0;
            }
        }else {
            return 0;
        }

    }

    @Override
    public int delThumbDw(String sid , String uid) {
        String o = (String) redisTemplate.opsForHash().get("SpitThumbDw", sid+"::"+uid);
        if ( o != null && "1".equals(o) ){
            Query query = new Query();

            query.addCriteria(Criteria.where("_id").is(sid));

            Update update = new Update();

            update.inc("thumbdw",-1);

            UpdateResult test = mongoTemplate.updateFirst(query, update, "test");

            if (test !=null){
                redisTemplate.opsForHash().put("SpitThumbDw", sid+"::"+uid,"0");
                return 1;
            }else {
                return 0;
            }
        }else {
            return 0;
        }

    }

    @Override
    public Map<String,Object> selectSpit(int page, int uid, int cid,int size) {

        Map<String,Object> map = new HashMap<>();

        Aggregation pageCount = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("visits").is(cid)),
                Aggregation.group("_id").count().as("test"));

        AggregationResults<MgPage> mgPages = mongoTemplate.aggregate(pageCount,"test", MgPage.class);
        List results = (List) mgPages.getRawResults().get("results");

        MgPage mgPage = MongodbPage.getPage(page, size, results.size());

        map.put("mgpage",mgPage);


        //查看评论信息
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("test").localField("parentid").foreignField("_id").as("pspit");
        TypedAggregation aggregation = Aggregation.newAggregation(Spit.class, lookupOperation,
                Aggregation.match(Criteria.where("visits").is(cid)),
                Aggregation.skip(mgPage.getPage()),
                Aggregation.limit(mgPage.getSize()));

        AggregationResults<Spit> aggregate = mongoTemplate.aggregate(aggregation, Spit.class);
        List<Spit> spits = aggregate.getMappedResults();

        List list1 = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisConnection.openPipeline();
                String key = "SpitThumbDw";
                for (Spit spit : spits) {
                    String o = spit.getId() + "::" + uid;
                    redisConnection.hGet(key.getBytes(),o.getBytes());
                }

                return null;
            }
        });

        List list2 = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisConnection.openPipeline();
                String key = "SpitThumbUp";
                for (Spit spit : spits) {
                    String o = spit.getId() + "::" + uid;
                    redisConnection.hGet(key.getBytes(),o.getBytes());

                }

                return null;
            }
        });



        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i)!=null && list1.get(i).equals("1")){
                spits.get(i).setDwCheck(1);
            }else {
                spits.get(i).setDwCheck(0);
            }
        }

        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i)!=null && list2.get(i).equals("1")){
                spits.get(i).setLikeCheck(1);
            }else {
                spits.get(i).setLikeCheck(0);
            }
        }


        spits.stream().forEach(n->{
            n.setNewPublishTime(TimeCountUtil.format(n.getPublishtime()));
        });

        map.put("spits", spits);
        map.put("count",results.size());
        return map;
    }

    @Override
    public int countByPid(int pid) {

        Aggregation pageCount = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("partitionId").is(pid)),
                Aggregation.group("_id").count().as("test"));
        AggregationResults<MgPage> mgPages = mongoTemplate.aggregate(pageCount,"test", MgPage.class);
        List results = (List) mgPages.getRawResults().get("results");
        return results.size();
    }

    @Override
    public int countByVisites(int cid) {
        Aggregation pageCount = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("visits").is(cid)),
                Aggregation.group("_id").count().as("test"));
        AggregationResults<MgPage> mgPages = mongoTemplate.aggregate(pageCount,"test", MgPage.class);
        List results = (List) mgPages.getRawResults().get("results");
        return results.size();
    }

}
