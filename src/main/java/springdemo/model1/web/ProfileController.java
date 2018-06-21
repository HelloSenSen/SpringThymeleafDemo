package springdemo.model1.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springdemo.converter.USLocalDateFormatter;
import springdemo.model1.dto.ProfileFormDTO;

import java.util.Locale;

@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String displayProfile(ProfileFormDTO profileForm, Model model){
        model.addAttribute("profileForm",profileForm);
        return "profile/profilePage";
    }

    @RequestMapping(value= "/profile",method = RequestMethod.POST)
    public String saveProfile(ProfileFormDTO profileForm){
        System.out.println("save ok" + profileForm);
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

}
