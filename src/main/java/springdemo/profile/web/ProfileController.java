package springdemo.profile.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springdemo.date.USLocalDateFormatter;
import springdemo.profile.session.UserProfileSession;
import springdemo.profile.vo.ProfileFormVo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Sunnysen
 */
@Controller
public class ProfileController {

    private UserProfileSession userProfileSession;

    @Autowired
    public ProfileController(UserProfileSession userProfileSession){
        this.userProfileSession = userProfileSession;
    }

    @ModelAttribute("profileFormVo")
    public ProfileFormVo getProfileFormVo(){
        return userProfileSession.toForm();
    }

    /**
     * 页面日期输入格式
     * 对所有请求赋值
     * @param locale
     * @return
     */
    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

    /**
     * 档案入口
     * @return
     */
    @RequestMapping("/profile")
    public String displayProfile(){
        return "profile/profilePage";
    }

    /**
     * 保存档案
     * @param profileFormVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value= "/profile" ,params = {"save"},method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileFormVo profileFormVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        userProfileSession.saveForm(profileFormVo);
        return "redirect:/search/mixed;keywords="+String.join(",",profileFormVo.getTastes());
    }

    /**
     * 添加喜好条目
     * @param profileFormVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/profile",params = {"addTaste"})
    public String addRow(ProfileFormVo profileFormVo, BindingResult bindingResult){
        profileFormVo.getTastes().add(null);
        return "profile/profilePage";
    }

    /**
     * 移除喜好条目
     * @param profileFormVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/profile",params = {"removeTaste"})
    public String removeRow(ProfileFormVo profileFormVo, HttpServletRequest request){
        Integer rowId = Integer.valueOf(request.getParameter("removeTaste"));
        profileFormVo.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }



}
