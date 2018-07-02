package springdemo.profile.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springdemo.date.USLocalDateFormatter;
import springdemo.profile.vo.ProfileFormVo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Sunnysen
 */
@Controller
public class ProfileController {

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

    @RequestMapping("/profile")
    public String displayProfile(ProfileFormVo profileFormVo,Model model){
        model.addAttribute("profileFormVo", profileFormVo);
        return "profile/profilePage";
    }

    @RequestMapping(value= "/profile" ,params = {"save"},method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileFormVo profileFormVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        System.out.println("save ok" + profileFormVo);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/profile",params = {"addTaste"})
    public String addRow(ProfileFormVo profileFormVo, BindingResult bindingResult){
        profileFormVo.getTastes().add(null);
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile",params = {"removeTaste"})
    public String removeRow(ProfileFormVo profileFormVo, BindingResult bindingResult, HttpServletRequest request){
        Integer rowId = Integer.valueOf(request.getParameter("removeTaste"));
        profileFormVo.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }



}
