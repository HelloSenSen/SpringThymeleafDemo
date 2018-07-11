package springdemo.profile.web;

import groovy.util.logging.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;
import springdemo.profile.config.PicturesUploadProperties;
import springdemo.profile.session.UserProfileSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Locale;

/**
 * 照片上传
 *
 * @author Sunnysen
 */
@Controller
public class PictureUploadController {

    private final Logger logger = LoggerFactory.getLogger(PictureUploadController.class);

    /**
     * 资源描述 图片上传的目录
     */
    private final Resource picturesDir;
    /**
     * 资源描述 默认图片
     */
    private final Resource anonymousPicture;
    /**
     * 国际化消息解析
     */
    private final MessageSource messageSource;

    private final UserProfileSession userProfileSession;

    @Autowired
    public PictureUploadController(PicturesUploadProperties uploadProperties, MessageSource messageSource, UserProfileSession userProfileSession) {
        picturesDir = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
        this.messageSource = messageSource;
        this.userProfileSession = userProfileSession;
    }

    /**
     * 根据picturePath获取图片 将图片写入响应
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response) throws IOException {
        Resource picturePath = userProfileSession.getPicturePath();
        if (picturePath == null) {
            picturePath = anonymousPicture;
        }
        String type = URLConnection.guessContentTypeFromName(picturePath.getFilename());
        response.setHeader("Content-Type", type);
        IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
    }

    /**
     * 图片上传controller
     * 将上传图片后的Resource 放入session
     *
     * @param file
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/profile", params = {"upload"}, method = RequestMethod.POST)
    public String onUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty() || !isImage(file)) {
            redirectAttributes.addFlashAttribute("error", "Incorrect file.Please upload a picture.");
            return "redirect:/profile";
//            throw new IOException("请选择图片上传");
        }
        Resource picturePath = copyFileToPictures(file);
        userProfileSession.setPicturePath(picturePath);
        return "redirect:profile";
    }

    /**
     * 上传文件 实际流操作
     *
     * @param file
     * @return
     * @throws IOException
     */
    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

    /**
     * 异常处理方法
     * 处理本Controller内方法抛出的异常
     * ExceptionHandler指定可处理的异常类型
     *
     * @param locale
     * @return
     */
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(Locale locale) {
        ModelAndView modelAndView = new ModelAndView("profile/profilePage");
        modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
        modelAndView.addObject("profileFormVo", userProfileSession.toForm());
        return modelAndView;
    }

    /**
     * 已配置全局MultipartException 跳转该方法
     *
     * @param locale
     * @param request
     * @return
     */
    @RequestMapping("uploadError")
    public ModelAndView onUploadError(Locale locale, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("profile/profilePage");
        //调试时 可使用WebUtils 返回 javax.servlet.error.message 信息
        String error = logger.isDebugEnabled() ? request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE).toString() : messageSource.getMessage("upload.file.too.big", null, locale);
        modelAndView.addObject("error", error);
        modelAndView.addObject("profileFormVo", userProfileSession.toForm());
        return modelAndView;
    }


    /**
     * 文件类型判断 图片
     *
     * @param file
     * @return
     */
    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    /**
     * 获取文件后缀名
     *
     * @param name
     * @return
     */
    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }

}
