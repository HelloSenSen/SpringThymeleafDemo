package springdemo.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springdemo.profile.session.UserProfileSession;

import java.util.List;

/**
 * 访问默认页 控制器
 * @author Sunnysen
 */
@Controller
public class HomeController {
    private UserProfileSession userProfileSession;

    @Autowired
    public HomeController(UserProfileSession userProfileSession) {
        this.userProfileSession = userProfileSession;
    }

    @RequestMapping("/")
    public String home(){
        List<String> tastes = userProfileSession.getTastes();
        if(tastes.isEmpty()){
            return "redirect:/profile";
        }
        return "redirect:/search/mixed;keywords="+String.join(",",tastes);
    }
}
