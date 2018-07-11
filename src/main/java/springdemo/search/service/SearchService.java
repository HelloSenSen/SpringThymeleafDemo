package springdemo.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索服务
 * @author Sunnysen
 */
@Service
public class SearchService implements TwitterSearch {

    private Twitter twitter;

    @Autowired
    public SearchService(Twitter twitter) {
        this.twitter = twitter;
    }

    /**
     * 搜索每个关键词，返回合并后的搜索结果
     * @param searchType
     * @param keywords 关键词列表
     * @return
     */
    @Override
    public List<Tweet> search(String searchType, List<String> keywords) {
        //生成搜索条件
        List<SearchParameters> searches = keywords.stream()
                .map(taste -> createSearchParam(searchType, taste))
                .collect(Collectors.toList());
        //封装搜索结果
        List<Tweet> results = searches.stream()
                .map(params -> twitter.searchOperations().search(params))
                .flatMap(searchResults -> searchResults.getTweets().stream())
                .collect(Collectors.toList());
        return results;
    }

    /**
     * 返回匹配的twitter结果搜索类型
     * 不匹配则返回 recent
     * @param searchType mixed recent popular 中的一个 不区分大小写
     * @return
     */
    private SearchParameters.ResultType getResultType(String searchType) {
        for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {
            if (knownType.name().equalsIgnoreCase(searchType)) {
                return knownType;
            }
        }
        return SearchParameters.ResultType.RECENT;
    }

    /**
     * 封装为twitter的搜索参数对象
     * @param searchType String twitter结果搜索类型
     * @param taste 关键词
     * @return
     */
    private SearchParameters createSearchParam(String searchType, String taste) {
        SearchParameters.ResultType resultType = getResultType(searchType);
        SearchParameters searchParameters = new SearchParameters(taste);
        searchParameters.resultType(resultType);
        searchParameters.count(3);
        return searchParameters;
    }


}
