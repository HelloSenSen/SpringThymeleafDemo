package springdemo.model1.web;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author Sunnysen
 */
@Controller
public class TweetController {

    private static final LongAdder ATOMIC_LONG_ID = new LongAdder();
    private static final Instant START_TIME = Instant.now();
    private static final Date START_DATE = Date.from(START_TIME);
    private static final Random TWEET_BUILDER_NUM_RANDOM = new Random();
    private static final int TWEET_BUILDER_NUM_MAX_BOUND = 20;

    @RequestMapping("/")
    public String home(){
        return "searchPage";
    }

    @RequestMapping("/result")
    public String hello(@RequestParam(name = "search",defaultValue = "world") String search,  Model model){
        List<Tweet> tweets = IntStream.range(0, TWEET_BUILDER_NUM_RANDOM.nextInt(TWEET_BUILDER_NUM_MAX_BOUND)).mapToObj(i -> tweetBuilder()).collect(Collectors.toList());
        model.addAttribute("tweets",tweets);
        model.addAttribute("search",search);
        return "resultPage";
    }

    /**
     * Constructs a Tweet
     *
     * id The tweet's ID
     * idStr The tweet's ID as a String
     * text The tweet's text
     * createdAt Date Tweet was created
     * fromUser The username of the author of the tweet.
     * profileImageUrl The URL to the profile picture of the tweet's author.
     * toUserId The user ID of the user to whom the tweet is targeted.
     * fromUserId The user ID of the tweet's author.
     * languageCode The language code
     * source The source of the tweet.
     *
     */
    private Tweet tweetBuilder(){
        ATOMIC_LONG_ID.increment();
        long id = ATOMIC_LONG_ID.sum();
        String idStr = String.valueOf(id);
        String text = "文本".concat(idStr);
        Date createdAt = Date.from(START_TIME.plusSeconds(id));
        String fromUser = "username".concat(idStr);
        String profileImageUrl = "https://avatars2.githubusercontent.com/u/"+(19281391+id);
        Long toUserId = 23333L;
        long fromUserId = 66666L;
        String languageCode ="zh";
        String source ="Unknown";
        Tweet result = new Tweet(id, idStr, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
        TwitterProfile twitterProfile = new TwitterProfile(66666L, "screenName_Sunnysen", "name_fullSunnysen", "github.com/HelloSenSen", profileImageUrl, "He's a programmer.", "{121.487899486,31.24916171 }", START_DATE);
        result.setUser(twitterProfile);
        return result;
    }

}
