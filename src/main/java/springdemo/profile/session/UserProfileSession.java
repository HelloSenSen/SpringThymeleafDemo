package springdemo.profile.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import springdemo.profile.vo.ProfileFormVo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户档案
 * session对象
 * @author Sunnysen
 */
@Component
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfileSession implements Serializable {
    private String twitterHandle;
    private String email;
    private LocalDate brithDate;
    private List<String> tastes = new ArrayList<>();

    /**
     * 通过档案表单为本实例赋值
     * @param profileFormVo
     */
    public void saveForm(ProfileFormVo profileFormVo){
        this.twitterHandle = profileFormVo.getTwitterHandle();
        this.email = profileFormVo.getEmail();
        this.brithDate = profileFormVo.getBirthDate();
        this.tastes = profileFormVo.getTastes();
    }

    /**
     * 通过本类实例创建档案表单
     * @return
     */
    public ProfileFormVo toForm(){
        ProfileFormVo profileFormVo = new ProfileFormVo();
        profileFormVo.setTwitterHandle(twitterHandle);
        profileFormVo.setEmail(email);
        profileFormVo.setBirthDate(brithDate);
        profileFormVo.setTastes(tastes);
        return profileFormVo;
    }

}
