package com.pmdgjjw.efgflight.service.Impl;


import com.pmdgjjw.efgflight.dao.PartitionMapper;
import com.pmdgjjw.efgflight.dao.SonPartitionMapper;
import com.pmdgjjw.efgflight.entity.*;
import com.pmdgjjw.efgflight.service.PartitionDetailService;
import com.pmdgjjw.efgflight.service.SonPartitionService;
import com.pmdgjjw.efgflight.service.SysUserService;
import com.pmdgjjw.efgflight.util.DoCheck;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultDelete;
import com.pmdgjjw.entity.ResultSelect;
import com.pmdgjjw.entity.ResultUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/6/28 14:36
 * @Description 子分区数据处理
 */
@Service
@Transactional
public class SonPratitionImpl extends BaseImpl<SonPartition> implements SonPartitionService {

    @Autowired
    private SonPartitionMapper sonPartitionMapper ;

    @Autowired
    private PartitionDetailService partitionDetailService;

    @Autowired
    private PartitionMapper partitionMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    //根据上级分区ID查询
    @Override
    public Result selectALLByParentID(Long id) {
            List<SonPartition> list = sonPartitionMapper.selectALLByParentID(id);
            return ResultSelect.Select(list);
    }

    //插入子分区数据
    @Override
    public Result sonPartitionInsert(Long id, Long pid, List<SonPartition> list) {

        Result result = new Result();
        Integer typeCheck = sysUserService.typeCheck(id);
        SysUser user = new SysUser();
        user.setId(id);
        user.setUserType(typeCheck);

        Boolean aBoolean = DoCheck.TypeCheck(user);

        //验证该用户是否曾经更新过子分区或者创建过分区
        //如果是则不用更新partition_detail中间表
        if (partitionDetailService.checkInDetail(id,pid) && aBoolean){

            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCreateDate(new Date());
                list.get(i).setUpdateDate(new Date());
                list.get(i).setParentId(pid);
            }


            int i = sonPartitionMapper.SonPartitionInsert(list);
            //是否子分区添加成功
            if (i>0 ){
                SysUser sysUser = new SysUser();
                ParentPartition parentPartition = new ParentPartition();
                sysUser.setId(id);
                parentPartition.setId(Math.toIntExact(pid));
                int j = partitionMapper.insertPartitionDetail(sysUser, parentPartition);
                //如果partition_detail中间表处理成功返回成功Result
                if (j>0){
                    result.setFlag(true);
                    result.setCode(200);
                    result.setMessage("添加成功");
                }
            }
            else {
                result.setFlag(false);
                result.setCode(200);
                result.setMessage("添加失败，请联系管理员");
            }

        }else{

            if (aBoolean){
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setCreateDate(new Date());
                    list.get(i).setUpdateDate(new Date());
                    list.get(i).setParentId(pid);
                }
                int i = sonPartitionMapper.SonPartitionInsert(list);
                result.setFlag(true);
                result.setCode(200);
                result.setMessage("操作成功");
            }
            else {
                result.setFlag(true);
                result.setCode(400);
                result.setMessage("对不起，你的用户权限不足");
            }

        }
        return result;
    }

    @Override
    public Result sonPartitionUpdate(SysUser user, SonPartition sonPartition) {

        user.setUserType(sysUserService.typeCheck(user.getId()));
        int i = 0;
        if (DoCheck.TypeCheck(user)){
            i = sonPartitionMapper.updateByPrimaryKeySelective(sonPartition);
        }
        else {
            i=2;
        }
        return ResultUpdate.update(i);

    }

    @Override
    public Result sonPartitionDelete(SysUser user, SonPartition sonPartition) {

        sonPartition.setDelFlag(1);

        user.setUserType(sysUserService.typeCheck(user.getId()));
        int i = 0;
        if (DoCheck.TypeCheck(user)){
            i = sonPartitionMapper.updateByPrimaryKeySelective(sonPartition);
        }
        else {
            i=2;
        }
        return ResultDelete.delete(i);
    }

    @Override
    public List<PartitionInfo> sonPartitionInfoSelect(int pid) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        List<PartitionInfo> partitionInfos = sonPartitionMapper.selectDetailByCid(pid);

        for (int i = 0; i < partitionInfos.size(); i++) {

            int id = partitionInfos.get(i).getId();
            Integer o = (Integer) redisTemplate.opsForHash().get("sonPartitionInfo", "partition::" + id);
            if (o ==null || o==0){
                Aggregation pageCount = Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("visits").is(id)),
                        Aggregation.group("_id").count().as("test"));
                AggregationResults<MgPage> mgPages = mongoTemplate.aggregate(pageCount,"test", MgPage.class);
                List results = (List) mgPages.getRawResults().get("results");
                partitionInfos.get(i).setSpitSize(results.size());
                redisTemplate.opsForHash().put("sonPartitionInfo","partition::"+id,results.size());
            }
            else {
                partitionInfos.get(i).setSpitSize(o);
            }

        }

        return partitionInfos;
    }
}
