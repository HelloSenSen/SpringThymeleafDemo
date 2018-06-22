package springdemo.model1.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springdemo.converter.USLocalDateFormatter;
import springdemo.model1.dto.ProfileFormDTO;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Sunnysen
 */
@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String displayProfile(ProfileFormDTO profileFormDTO, Model model){
        model.addAttribute("profileForm",profileFormDTO);
        return "profile/profilePage";
    }

    @RequestMapping(value= "/profile",method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileFormDTO profileFormDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        System.out.println("save ok" + profileFormDTO);
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

}
