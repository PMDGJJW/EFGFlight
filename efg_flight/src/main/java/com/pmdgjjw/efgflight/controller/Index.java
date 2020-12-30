package com.pmdgjjw.efgflight.controller;

import com.pmdgjjw.efgflight.entity.Article;
import com.pmdgjjw.efgflight.entity.BaseComment;
import com.pmdgjjw.efgflight.entity.ParentPartition;
import com.pmdgjjw.efgflight.service.CommentService;
import com.pmdgjjw.efgflight.service.ParentPartitonService;
import com.pmdgjjw.efgflight.util.EsUtil;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultSelect;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @auth jian j w
 * @date 2020/6/25 22:03
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/index")
public class Index {

    @Autowired
    private ParentPartitonService parentPartitonService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsUtil esUtil;

    @GetMapping()
    public Result toIndex(){

        List<ParentPartition> parentPartitions = parentPartitonService.selectAll();

        return ResultSelect.Select(parentPartitions);

    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable int id) {
        ParentPartition parentPartition = parentPartitonService.selectByPrimaryKey(id);

        return ResultSelect.Select(parentPartition);
    }

    @GetMapping("/newCommentDeatial/{id}")
    public Result selectDetailForNewComment(@PathVariable int id){

        BaseComment baseComment = commentService.newCommentDeatail(id);

        return ResultSelect.Select(baseComment);
    }

    @GetMapping("/newReplyComments")
    public Result selectNewReplyComment(){

        List<BaseComment> baseComments = commentService.newReplyComments();

        return ResultSelect.Select(baseComments);
    }

    @GetMapping("/getDayHot")
    public Result getDayHot(){

        List<BaseComment> baseComments = commentService.dayHotComments();

        return ResultSelect.Select(baseComments);

    }

    @GetMapping("/getWeekHot")
    public Result getWeekHot(){

        List<BaseComment> baseComments = commentService.weekHotComments();

        return ResultSelect.Select(baseComments);

    }

    @GetMapping("/getIndexCommentDetail/{id}")
    public Result getIndexCommentDetail(@PathVariable int id){

        BaseComment indexCommentDetail = commentService.getIndexCommentDetail(id);

        return ResultSelect.Select(indexCommentDetail);
    }

    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result search(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {

        Pageable pageable = PageRequest.of(page-1,size);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//        multiMatchQuery(keywords,"title","comment")
                .withQuery(matchPhraseQuery("title",keywords))
                .withPageable(pageable)
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);

        return new Result(true,200,"查询成功",articles);

    }

    @GetMapping("/getAnalyzes/{index}/{test}")
    public Result getAnalyzes(@PathVariable String index,@PathVariable String test){
        String[] analyzes = esUtil.getAnalyzes(index, test);

        return new Result(true,200,"查询成功",analyzes);

    }

    @GetMapping("/getSuggestion/{test}")
    public Result getSuggestion(@PathVariable String test) {

        String[] analyzes = esUtil.getSuggestion(Article.class, test);

        return new Result(true, 200, "查询成功", analyzes);

    }


}
