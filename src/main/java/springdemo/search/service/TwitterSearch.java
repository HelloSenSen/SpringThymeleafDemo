package springdemo.search.service;

import org.springframework.social.twitter.api.Tweet;

import java.util.List;

/**
 *
 * @author Sunnysen
 */
public interface TwitterSearch {

    List<Tweet> search(String searchType, List<String> keywords);

}
