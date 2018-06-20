package springdemo.model1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


/**
 * @author Sunnysen
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String hello(@RequestParam(name = "name",defaultValue = "world") String username,  Model model){
        List<String> messages = Arrays.asList(username+"-message1",username+"-message2");
        model.addAttribute("messages",messages);
        return "resultPage";
    }
}
