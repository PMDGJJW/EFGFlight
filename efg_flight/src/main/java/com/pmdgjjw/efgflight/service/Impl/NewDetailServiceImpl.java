package com.pmdgjjw.efgflight.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pmdgjjw.efgflight.dao.NewDetailMapper;
import com.pmdgjjw.efgflight.entity.MgPage;
import com.pmdgjjw.efgflight.entity.NewInfoDetail;
import com.pmdgjjw.efgflight.service.NewDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 消息中心服务实现类
 */
@Service
public class NewDetailServiceImpl extends BaseImpl<NewInfoDetail> implements NewDetailService {

    @Autowired
    private NewDetailMapper newDetailMapper;

    @Autowired
    private  MongoTemplate mongoTemplate ;

    @Override
    public List<NewInfoDetail> getNewInfoForCommentId(NewInfoDetail newInfoDetail) {
        Example example = new Example(newInfoDetail.getClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",newInfoDetail.getUserId());
        return newDetailMapper.selectByExample(example);
    }

    @Override
    public Integer getNewInfoCount(NewInfoDetail newInfoDetail) {
        Example example = new Example(newInfoDetail.getClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",newInfoDetail.getUserId());
        criteria.andEqualTo("isRead",0);
        int i = newDetailMapper.selectCountByExample(example);
        return i;
    }

    @Override
    public Integer insertNewInfo(NewInfoDetail newInfoDetail) {

        newInfoDetail.setCreateTime(new Date());

        return newDetailMapper.insertSelective(newInfoDetail);
    }

    @Override
    public Map<String, Object> getPageForSpit(String id) {

        NewInfoDetail newInfoDetail = newDetailMapper.selectByPrimaryKey(id);

        newInfoDetail.setIsRead(1);

        int i1 = newDetailMapper.updateByPrimaryKeySelective(newInfoDetail);


        Aggregation pageCount = Aggregation.newAggregation(
                Aggregation.sort(Sort.Direction.ASC, "publishtime"),
                Aggregation.match(Criteria.where("visits").is(newInfoDetail.getCommentId()))
                );


        AggregationResults<MgPage> mgPages = mongoTemplate.aggregate(pageCount,"test", MgPage.class);
        List results = (List) mgPages.getRawResults().get("results");

        // 目标评论所在的下标
        int targetIndex = 0;

        for (int i = 0; i < results.size(); i++) {
            Object o = results.get(i);
            Map map = (Map) o;
            if (newInfoDetail.getSpitId().toString().equals(map.get("_id").toString())){
                targetIndex = i;
            }
        }
        // 返回结果集
        Map<String,Object> resultMap = new HashMap();
        List<Integer> pageList = new ArrayList<>();
        if (targetIndex>=30 && i1>0){

            // 获取可以被整除的数字
            for (int i = 2; i <=9 ; i++) {
                if ((targetIndex-1) % i == 0){
                    pageList.add(i);
                }
            }
            // 判断当前评论的下标数是否是质数只能为1或它本身整除
            if (pageList.size() != 0){
                Integer max = Collections.max(pageList);

                int k =  (results.size()-1) / max;
                resultMap.put("pageSize",k);
                resultMap.put("pageNumber",(max+1));
            }else {
                // 分页大小为它本身减1
                resultMap.put("pageSize",(targetIndex-1));
                resultMap.put("pageNumber",2);
            }



        }else {
            if (targetIndex == 0 || i1 == 0){
                resultMap.put("pageSize",targetIndex);
                resultMap.put("pageNumber",1);
            }else {
                resultMap.put("pageSize",(targetIndex-1));
                resultMap.put("pageNumber",2);
            }

        }
        resultMap.put("spitId",newInfoDetail.getSpitId());
        return resultMap;
    }

    @Override
    public Integer setMessageAllRead(List<Long> id) {
          return newDetailMapper.updateIsRead(id);
    }
}
