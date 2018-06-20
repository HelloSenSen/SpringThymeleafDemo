package springdemo.model1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 特殊@Component
 * 一般结合@RequestMapping使用
 */
@Controller
/**
 *
 * @author Daniel
 */
public class HelloController {

    @RequestMapping
    @ResponseBody
    public String hello(){

    }
}
