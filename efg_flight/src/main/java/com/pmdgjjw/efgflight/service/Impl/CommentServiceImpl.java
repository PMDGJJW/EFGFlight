package com.pmdgjjw.efgflight.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pmdgjjw.efgflight.dao.*;
import com.pmdgjjw.efgflight.entity.*;
import com.pmdgjjw.efgflight.service.CommentService;
import com.pmdgjjw.efgflight.service.SpitService;
import com.pmdgjjw.efgflight.util.IPUtils;
import com.pmdgjjw.efgflight.util.MongodbPage;
import com.pmdgjjw.efgflight.util.TimeCountUtil;
import com.pmdgjjw.timedeaft.TimeIsNew;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @auth jian j w
 * @date 2020/6/27 9:27
 * @Description
 */
@Service
@Transactional
public class CommentServiceImpl extends BaseImpl<Comments> implements CommentService {


    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private SpitMongo spitResp;

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SonPartitionMapper sonPartitionMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public PageInfo<Comments> pageInfo(int page, int size, int id) {

        PageHelper.startPage(page, size);

        List<Comments> list = commentsMapper.selectAllComments(id);

        list = DataCheck(list);
        return new PageInfo<Comments>(list);
    }

    @Override
    public PageInfo<Comments> pageInfoByCatID(int page, int size, int id,int pid) {
        PageHelper.startPage(page, size);

        List<Comments> list = commentsMapper.selectByCartId(id,pid);

        list = DataCheck(list);

        return new PageInfo<Comments>(list);
    }

    public List<Comments> DataCheck(List<Comments> list){

        for (int i = 0; i < list.size(); i++) {

            Long cTime = new Date().getTime() - list.get(i).getCreateTime().getTime();
            if (cTime < TimeIsNew.COMMENT_IS_NEW && cTime>=0){
                list.get(i).setIsNewComment(1);
            }else {
                list.get(i).setIsNewComment(0);
            }

            if (list.get(i).getSysUser().getRegiestTime() != null){
                Long uTime = new Date().getTime() - list.get(i).getSysUser().getRegiestTime().getTime();
                if (uTime<TimeIsNew.USER_IS_NEW && cTime>=0 ){
                    list.get(i).setIsNewUserComment(1);
                }else {
                    list.get(i).setIsNewUserComment(0);
                }
            }else {
                list.get(i).setIsNewUserComment(0);
            }

            list.get(i).setNewCreateTime(TimeCountUtil.format(list.get(i).getCreateTime()));


            if (list.get(i).getCommentTime() !=null && list.get(i).getCreateTime()!=null && list.get(i).getCreateTime().getTime()<list.get(i).getCommentTime().getTime()){
                list.get(i).setNewCommentTime(TimeCountUtil.format(list.get(i).getCommentTime()));
            }
            else {
                list.get(i).setNewCommentTime("1秒前");
            }

        }
        return list;
    }



    @Override
    public Comments selectCommentDetail(int id ,HttpServletRequest request) {

        //redis编码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //初始查看数
        int redisseeCount = 0;

        Comments comment = new Comments();

        //获取用户Ip 作为key
        String s = IPUtils.getClientAddress(request);
        String key = "ip:"+s+"cid:"+id;

        //获取查看数key
        String format = new SimpleDateFormat("yyyy-MM").format(new Date());
        String ckey = "cid:"+id+"date:"+format;

        //获取查看数
        Object o1 = redisTemplate.opsForValue().get(ckey);
        //获取用户是否查看过
        Object o = redisTemplate.opsForValue().get(key);

         comment = (Comments) redisTemplate.opsForValue().get("comment:stringDetail:"+id);
        //是否有帖子缓存
        if (comment !=null){
            Integer seecount = (Integer) redisTemplate.opsForHash().get("comment:seeCount:"+id, "seecount");
            comment.setSeeCount(seecount);
        }
        else {

        comment = commentsMapper.selectCommentDetail(id);

        if (comment ==null){
            return null;
        }

           if(comment !=null){
               Date createTime = comment.getCreateTime();
               comment.setNewCreateTime(TimeCountUtil.format(createTime));
               comment.setNewUpdateTime(TimeCountUtil.format(comment.getUpdateTime()));
           }
            //修改时间语言化

            redisTemplate.opsForValue().set("comment:stringDetail:"+id,comment);

        }

        //如果没有查看数缓存创建缓存
        if (o1 == null){
            Comments csee = commentsMapper.selectByPrimaryKey(id);
            if (csee !=null){
                redisTemplate.opsForValue().set(ckey,csee.getSeeCount());
            }
        }
        //判断用户是否查看过帖子，2小时限期
        if ( o ==null ){
            redisTemplate.opsForValue().set(key,"1",2, TimeUnit.HOURS);

            Long aLong = redisTemplate.opsForValue().increment(ckey);

            String foday = new SimpleDateFormat("dd").format(new Date());
            String oneday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            redisTemplate.opsForZSet().incrementScore(oneday+":hot", "cid:" + id + ":seeCount", 1);
            double wk = Integer.valueOf(foday) / 7;
            int floor = (int) (Math.floor(wk)+1);

            if (floor>=5){
                floor=4;
            }
            redisTemplate.opsForZSet().incrementScore(format+":week:"+floor+":hot", "cid:" + id + ":seeCount", 1);
            o1 = aLong;
        }


        //查看数以300为范围做更新数据库查看数信息以及删除redis查看数缓存
        if (o1 != null ){
            redisseeCount  = (int) redisTemplate.opsForValue().get(ckey);
            if (redisseeCount%300 == 0){
                Comments comments = new Comments();
                comments.setId(id);
                int newSeeCount = redisseeCount;
                comments.setSeeCount(newSeeCount);
                int i = commentsMapper.updateByPrimaryKeySelective(comments);
                if (i>0){
                    redisTemplate.delete(ckey);
                }
            }

            if ( comment !=null){
                comment.setSeeCount(redisseeCount);
                redisTemplate.opsForHash().put("comment:seeCount:"+id,"seecount", comment.getSeeCount());
            }

        }

        return comment;
        }

    @Override
    public Spit doSpit(int id ,Spit spit, SysUser user) {


        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        int flag = 0;

        if (Integer.valueOf(id) !=null){
            spit.setVisits(id);
        }

        spit.setPublishtime(new Date());
        spit.setUserid(String.valueOf(user.getId()));
        spit.setNickname(user.getName());
        spit.setgCheck(0);
        spit.setThumbup(0);
        spit.setThumbdw(0);

        String key = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Spit save = spitResp.save(spit);

        if (save !=null){

            redisTemplate.opsForZSet().incrementScore(key,id,1.0);
            flag = 1 ;
        }
        else {
           flag = 0;
        }

        if (flag>0){
                Comments comments = new Comments();
                comments.setId(id);
                comments.setNewreplyId(user.getId());
                comments.setCommentTime(new Date());

                int i = spitService.countByVisites(id);
                comments.setReplyCount(i+1);
                commentsMapper.updateAfterSpit(comments);

                SonPartition sonPartition = new SonPartition();
                sonPartition.setId(spit.getPartitionId());
                sonPartition.setCreateBy(user.getId());
                sonPartition.setUpdateDate(new Date());
                sonPartitionMapper.updateSonAfterSpit(sonPartition);
            }


           return  save;

    }

    @Override
    public int commentInsert(Comments comment, SysUser user) {

        comment.setIsapprove(0);
        comment.setReward(1);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setIssurper(0);
        comment.setSeeCount(0);
        comment.setReplyCount(0);
        comment.setIsBand(0);
        comment.setIshot(0);
        comment.setDelFlag(0);
        comment.setCreateBy(user.getId());
        comment.setIsBand(0);
        comment.setUnSave(0);

        int i = commentsMapper.insert(comment);
        int insert = 0;

        Integer cid = comment.getId();

        if (cid != null && i > 0) {

           insert = cid;

        }
        return insert;
    }

    @Override
    public int countTopicByPid(int pid) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Integer o = (Integer) redisTemplate.opsForValue().get(format + ":Topic:" + pid);
        int i = 0;
        if (o == null){
            i = commentsMapper.countByPid(pid);
            redisTemplate.opsForValue().set(format+":Topic:"+pid,i,5,TimeUnit.MINUTES);
        }else {
            i = o;
        }

        return i ;
    }

    @Override
    public int countItemByPid(int pid) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Integer o = (Integer) redisTemplate.opsForValue().get(format + ":Item:" + pid);
        int i = 0;
        if (o == null){
             i = spitService.countByPid(pid);
            redisTemplate.opsForValue().set(format+":Item:"+pid,i,5,TimeUnit.MINUTES);
        }else {
             i = o;
        }
        return i ;

    }

    @Override
    public int sonPartitionCheck(int id) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        Long info = redisTemplate.opsForHash().delete("sonPartitionInfo", "partition::" + id);

        if (info>0){
            return 1;
        }
        else {
            return 0;
        }

    }

    @Override
    public List<BaseComment> newComments() {

        List<BaseComment> baseComments = commentsMapper.newComments();

        return baseComments;
    }

    @Override
    public BaseComment newCommentDeatail(int id) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        BaseComment baseComment = commentsMapper.selectNewCommentDetail(id);

        Integer seecount = (Integer) redisTemplate.opsForHash().get("comment:seeCount:" + id, "seecount");
        baseComment.setSeeCount(seecount);
        baseComment.setNewCommentTime(TimeCountUtil.format(baseComment.getCommentTime()));
        baseComment.setNewCreateTime(TimeCountUtil.format(baseComment.getCreateTime()));
        return baseComment;
    }

    @Override
    public List<BaseComment> newReplyComments() {

        List<BaseComment> baseComments = commentsMapper.newReplyComments();

        for (BaseComment baseComment : baseComments) {
            baseComment.setNewCommentTime(TimeCountUtil.format(baseComment.getCommentTime()));
        }

        return baseComments;
    }

    @Override
    public List<BaseComment> dayHotComments() {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Set<String>set = redisTemplate.opsForZSet().reverseRange(format + ":hot", 0, 7);

        List<String> list = new ArrayList<>();

        for (String o : set) {
            String s = o.split(":")[1];
            list.add(s);
        }

        List<BaseComment> baseComments = commentsMapper.hotComments(list);

        return baseComments;
    }

    @Override
    public List<BaseComment> weekHotComments() {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        String format = new SimpleDateFormat("yyyy-MM").format(new Date());
        String forday = new SimpleDateFormat("dd").format(new Date());

        int dayNum = Integer.valueOf(forday);

        double week = dayNum / 7;

        int floor = (int) (Math.floor(week)+1);

        if (floor>=5){
            floor=4;
        }

        Set<String>set = redisTemplate.opsForZSet().reverseRange(format + ":week:"+floor+":hot", 0, 7);

        List<String> list = new ArrayList<>();

        for (String o : set) {
            String s = o.split(":")[1];
            list.add(s);
        }

        List<BaseComment> baseComments = commentsMapper.hotComments(list);

        return baseComments;
    }

    @Override
    public int doReplyHot(int id) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());


        String foday = new SimpleDateFormat("dd").format(new Date());
        String oneday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String format = new SimpleDateFormat("yyyy-MM").format(new Date());

        redisTemplate.opsForZSet().incrementScore(oneday+":hotReply", "cid:" + id + ":replyCount", 1);
        double wk = Integer.valueOf(foday) / 7;
        int floor = (int) (Math.floor(wk)+1);

        if (floor>=5){
            floor=4;
        }
        redisTemplate.opsForZSet().incrementScore(format+":week:"+floor+":hotReply", "cid:" + id + ":replyCount", 1);
        return 1;
    }

    @Override
    public BaseComment getIndexCommentDetail(int id) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        BaseComment baseComment = commentsMapper.selectNewCommentDetail(id);

        double score = redisTemplate.opsForZSet().score(format + ":hot", "cid:" + id + ":seeCount");
        double replyScore = redisTemplate.opsForZSet().score(format + ":hotReply", "cid:" + id + ":replyCount");

        baseComment.setSeeCount((int) score);
        baseComment.setReplyCount((int) replyScore);

        baseComment.setNewCommentTime(TimeCountUtil.format(baseComment.getCommentTime()));
        baseComment.setNewCreateTime(TimeCountUtil.format(baseComment.getCreateTime()));
        return baseComment;
    }

    @Override
    public List<Spit> getReplyUser(int id) {



        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("visits").is(id)),
                Aggregation.sort(Sort.Direction.ASC,"publishtime"),
                Aggregation.project("userid"),
                Aggregation.limit(30),
                Aggregation.project("userid"));

        AggregationResults<Spit> test = mongoTemplate.aggregate(aggregation, "test", Spit.class);
        List<Spit> mappedResults = test.getMappedResults();
        return mappedResults;
    }

    @Override
    public void MqNewThread( Map<String, Object> map) {

            Future<String> future = executor.submit(() -> {
                amqpTemplate.convertAndSend("efgTopicExchange", "efg.reply", map);
                return Thread.currentThread().getName();
            });

    }


    @Override
    public BaseComment CommentTitle(int id) {
        return commentsMapper.CommentsTitle(id);
    }
}
