package springdemo.profile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * 自定义上传图片 属性
 * @author Sunnysen
 */
@ConfigurationProperties(prefix = "upload.pictures")
public class PicturesUploadProperties {

    /**
     * 资源描述 图片上传的目录
     */
    private Resource uploadPath;
    /**
     * 资源描述 默认图片
     */
    private Resource anonymousPicture;

    public Resource getAnonymousPicture(){
        return anonymousPicture;
    }

    /**
     * 通过文本设置 默认图片位置
     * @param anonymousPicture
     */
    public void setAnonymousPicture(String anonymousPicture) {
        this.anonymousPicture = new DefaultResourceLoader().getResource(anonymousPicture);
    }

    public Resource getUploadPath(){
        return uploadPath;
    }

    /**
     * 通过文本设置 图片上传的目录
     * @param uploadPath
     */
    public void setUploadPath(String uploadPath){
        this.uploadPath = new DefaultResourceLoader().getResource(uploadPath);
    }
}
