package springdemo.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springdemo.search.service.SearchService;

import java.util.List;

/**
 * 新的搜索控制层
 * @author Sunnysen
 */
@Controller
public class SearchContoller {
    private SearchService searchService;
    @Autowired
    public SearchContoller(SearchService searchService){
        this.searchService = searchService;
    }

    /**
     * 搜索关键词使用矩阵变量
     * @param searchType
     * @param keywords
     * @return
     */
    @RequestMapping("/search/{searchType}")
    public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
        List<Tweet> tweets = searchService.search(searchType,keywords);
        ModelAndView modelAndView = new ModelAndView("resultPage");
        modelAndView.addObject("tweets",tweets);
        modelAndView.addObject("search",String.join(",",keywords));
        return modelAndView;
    }
}
