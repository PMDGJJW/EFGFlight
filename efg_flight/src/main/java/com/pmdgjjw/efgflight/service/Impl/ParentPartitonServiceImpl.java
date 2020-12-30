package com.pmdgjjw.efgflight.service.Impl;

import com.pmdgjjw.efgflight.dao.PartitionMapper;
import com.pmdgjjw.efgflight.dao.SonPartitionMapper;
import com.pmdgjjw.efgflight.entity.*;
import com.pmdgjjw.efgflight.service.CommentService;
import com.pmdgjjw.efgflight.service.ParentPartitonService;
import com.pmdgjjw.efgflight.service.SysUserService;
import com.pmdgjjw.efgflight.util.DoCheck;
import com.pmdgjjw.efgflight.util.TimeCountUtil;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultSelect;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @auth jian j w
 * @date 2020/6/26 15:50
 * @Description 父分区模块数据处理
 */
@Service
@Transactional
public class ParentPartitonServiceImpl extends BaseImpl<ParentPartition> implements ParentPartitonService {

    @Autowired
    private PartitionMapper partitionMapper;

    @Autowired
    private SonPartitionMapper sonPartitionMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;



    // 作为后台查询所有父分区
    @Override
    public Result selectAllParentPartition() {

        List<ParentPartition> parentPartitions = partitionMapper.selectAll();

        return ResultSelect.Select(parentPartitions);

    }

    //前台父子分区关联数据
    @Override
    public List<ParentPartition> selectAll() {

        List<ParentPartition> parentPartitions = partitionMapper.selectAllInIndex();
        List<ParentPartition> par = partitionMapper.selectUserID();
        List<List<SysUser>> createName = new ArrayList<>();

        int count = par.size();
        //处理分区创建人数据
        for (int j = 0; j <=count ; j++) {
            List<SysUser> createUser = new ArrayList<>();
            for (int i = 0; i <par.size() ; i++) {

                if (par.get(i).getId()==j+1){
                    createUser.add(par.get(i).getSysUsers());
                    par.remove(i);
                    i-=1;
                }
            }
            createName.add(createUser);
            count--;
        }

        //处理回复时间据现在多长时间
        for (int i = 0; i < parentPartitions.size(); i++) {
            List<SonPartition> sonPartition = parentPartitions.get(i).getSonPartition();
            for (int i1 = 0; i1 < sonPartition.size(); i1++) {
                Date updateDate = sonPartition.get(i1).getUpdateDate();
                sonPartition.get(i1).setNewReplyTime(TimeCountUtil.format(updateDate));
                int pid = sonPartition.get(i1).getId();
                int i2 = commentService.countTopicByPid(pid);
                int i3 = commentService.countItemByPid(pid);
                sonPartition.get(i1).setTopics(i2);
                sonPartition.get(i1).setComments(i3);
            }
            parentPartitions.get(i).setCreateUser(createName.get(i));
        }



        return parentPartitions;

    }

    //前台查看父分区下的子分区详情
    @Override
    public ParentPartition selectByPrimaryKey(Object o) {

            List<ParentPartition> list = partitionMapper.selectUserIDByKey(1);
            ParentPartition parentPartition = partitionMapper.selectByIdInIndex((int) o);

            List<SysUser> createUser = new ArrayList<>();

            for (int i = 0; i <list.size() ; i++) {
                createUser.add(list.get(i).getSysUsers());
            }
            //处理回复时间据现在多长时间
            for (int i = 0; i < parentPartition.getSonPartition().size(); i++) {
                parentPartition.getSonPartition().get(i)
                        .setNewReplyTime(TimeCountUtil.format(parentPartition.getSonPartition().get(i)
                        .getUpdateDate()
                        ));
            }
            parentPartition.setCreateUser(createUser);

            return parentPartition;
    }

    //后台添加父分区数据及用户类型验证
    @Override
    public Result insertCheck(SysUser user, ParentPartition partition) {

        Result result = new Result();

        //根据父分区名称验证是否创建过
        ParentPartition parentPartition = partitionMapper.selectOne(partition);

        user.setUserType(sysUserService.typeCheck(user.getId()));

        partition.setCreateDate(new Date());
        partition.setDelFlag("0");
        partition.setUpdateDate(new Date());
        partition.setCreateBy(user.getId()+"");

        int i = 0;

        //查看该用户是否曾经修改或添加父子分区
        String k = partitionMapper.PartitionDetailCheck(user,partition);

        //如果创建过父分区则返回已存在
        if (parentPartition !=null){

                result.setCode(200);
                result.setFlag(true);
                result.setMessage("该分区已经存在了");

        }
        //第一次创建父分区
        else {
            //曾经创建修改过父子分区
            if (k !=null && k.length()>0){

            }
            //表示第一次创建新父分区及权限认证
            else if (DoCheck.TypeCheck(user)){
                i = partitionMapper.insertPartition(partition);
                int d = partitionMapper.insertPartitionDetail(user, partition);

                //父分区及中间表数据添加成功
                if (i>0 && d>0){
                    result.setCode(200);
                    result.setFlag(true);
                    result.setMessage("添加成功");
                }
                else {
                    result.setCode(400);
                    result.setFlag(false);
                    result.setMessage("添加失败，请联系系统管理员");
                }

            }
            //权限认证失败
            else {
                result.setCode(400);
                result.setFlag(false);
                result.setMessage("对不起，你的用户权限不足");
            }
        }
        return  result ;
    }

    //更新分区信息
    @Override
    public Result updateCheck(SysUser user, ParentPartition partition) {
        Result result = new Result();

        user.setUserType(sysUserService.typeCheck(user.getId()));

        if (DoCheck.TypeCheck(user)){

            int i = partitionMapper.updateByPrimaryKeySelective(partition);

            if (i>0){
                result.setCode(200);
                result.setFlag(true);
                result.setMessage("更新成功");
            }
            else {
                result.setCode(400);
                result.setFlag(false);
                result.setMessage("更新失败，请联系系统管理员");
            }

        }else {
            result.setCode(400);
            result.setFlag(false);
            result.setMessage("对不起，你的用户权限不足");
        }
        return  result ;
    }

    @Override
    public Result deleteCheck(SysUser user, ParentPartition partition) {

        Result result = new Result();
        partition.setDelFlag("1");

        user.setUserType(sysUserService.typeCheck(user.getId()));

        if (DoCheck.TypeCheck(user)){

            int i = partitionMapper.updateByPrimaryKeySelective(partition);

            if (i>0){
                result.setCode(200);
                result.setFlag(true);
                result.setMessage("删除成功");
            }
            else {
                result.setCode(400);
                result.setFlag(false);
                result.setMessage("删除失败，请联系系统管理员");
            }

        }else {
            result.setCode(400);
            result.setFlag(false);
            result.setMessage("对不起，你的用户权限不足");
        }
        return  result ;
    }

    @Override
    public PartitionInfo incrScore(int pid) {

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        SonPartition sonPartition = sonPartitionMapper.selectByPrimaryKey(pid);
        PartitionInfo partitionInfo = new PartitionInfo();

        partitionInfo.setId(sonPartition.getId());
        partitionInfo.setName(sonPartition.getName());


        String key = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Double score = redisTemplate.opsForZSet().score(key, pid);

        Long aLong = redisTemplate.opsForZSet().reverseRank(key, pid);

        Aggregation pageCount = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("sonPartitionId").is(pid).and("partitionId").is(sonPartition.getParentId())),
                Aggregation.group("_id").count().as("test"));
        AggregationResults<MgPage> mgPages = mongoTemplate.aggregate(pageCount,"test", MgPage.class);
        List results = (List) mgPages.getRawResults().get("results");

        partitionInfo.setComSize(score);
        partitionInfo.setSpitSize(results.size());
        partitionInfo.setIncrScore(aLong);
        return partitionInfo;
    }
}
