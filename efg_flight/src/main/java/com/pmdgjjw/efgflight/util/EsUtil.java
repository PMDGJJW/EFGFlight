package com.pmdgjjw.efgflight.util;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/8/16 14:52
 * @Description
 */
@Component
public class EsUtil {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public String[] getAnalyzes(String index,String text){
        //调用ES客户端分词器进行分词
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(elasticsearchTemplate.getClient(),
                AnalyzeAction.INSTANCE,index,text).setAnalyzer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();

        // 赋值
        List<String> searchTermList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> { searchTermList.add(ikToken.getTerm()); });

        return searchTermList.toArray(new String[searchTermList.size()]);
    }

    public String[] getSuggestion(Class clazz,String text){
        //构造搜索建议语句
        SuggestionBuilder completionSuggestionFuzzyBuilder = SuggestBuilders.completionSuggestion("suggest").prefix(text, Fuzziness.AUTO);

        //根据
        final SearchResponse suggestResponse = elasticsearchTemplate.suggest(new SuggestBuilder().addSuggestion("my-suggest",completionSuggestionFuzzyBuilder), clazz);
        CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion("my-suggest");
        List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();
        System.err.println(options);
        System.out.println(options.size());
        System.out.println(options.get(0).getText().string());

        List<String> suggestList = new ArrayList<>();
        options.forEach(item ->{ suggestList.add(item.getText().toString()); });

        return suggestList.toArray(new String[suggestList.size()]);
    }



}
