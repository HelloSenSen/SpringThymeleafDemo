package springdemo.model1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Sunnysen
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String hello(@RequestParam("name") String username,  Model model){
        model.addAttribute("message","Hello,"+username);
        return "resultPage";
    }
}
