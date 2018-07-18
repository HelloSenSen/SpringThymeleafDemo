package springdemo.search.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.*;
import springdemo.search.service.SearchService;

import java.util.List;

/**
 * REST API 搜索 控制器
 * @author Sunnysen
 */
@RestController
@RequestMapping("/api/search")
public class SearchApiController {
    private SearchService searchService;

    @Autowired
    public  SearchApiController(SearchService searchService){
        this.searchService = searchService;
    }

    @RequestMapping(value = "/{searchType}",method = RequestMethod.GET)
    public List<Tweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
        return  searchService.search(searchType,keywords);
    }


}
