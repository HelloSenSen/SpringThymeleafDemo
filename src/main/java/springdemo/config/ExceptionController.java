package springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 处理异常 controller
 * 根据配置 一般异常会跳转到本controller
 * @auther Sunnysen
 */
@Controller
@RequestMapping(value = "error")
@EnableConfigurationProperties({ServerProperties.class})
public class ExceptionController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    public ExceptionController(ErrorAttributes errorAttributes){
        Assert.notNull(errorAttributes,"ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    /**
     * 返回404 页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(produces = "text/html",value = "404")
    public ModelAndView errorHtml404(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(getStatus(request).value());
        Map<String,Object> model = getErrorAttributes(request,isIncludeStackTrace(request,MediaType.TEXT_HTML));
        return new ModelAndView("error",model);
    }

    /**
     * 返回404 Json
     * @param request
     * @return
     */
    @RequestMapping(value = "404")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> error404(HttpServletRequest request){
        Map<String,Object> body = getErrorAttributes(request,isIncludeStackTrace(request,MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    /**
     * 返回500 页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(produces = "text/html",value = "500")
    public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(getStatus(request).value());
        Map<String,Object> model = getErrorAttributes(request,isIncludeStackTrace(request,MediaType.TEXT_HTML));
        return new ModelAndView("error",model);
    }

    /**
     * 返回500 Json
     * @param request
     * @return
     */
    @RequestMapping(value = "500")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> error500(HttpServletRequest request){
        Map<String,Object> body = getErrorAttributes(request,isIncludeStackTrace(request,MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    /**
     * 确定是否应包括堆栈跟踪属性
     * @param request the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected  boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces){
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS){
            return true;
        }
        if(include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM){
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * 获取错误的信息
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String,Object> getErrorAttributes(HttpServletRequest request,boolean includeStackTrace){
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(requestAttributes,includeStackTrace);
    }


    /**
     * 是否包含trace
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request){
        String parameter =request.getParameter("trace");
        if(parameter==null){
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    /**
     * 获取错误代码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.errror.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try{
            return HttpStatus.valueOf(statusCode);
        }catch (Exception e){
            return  HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public String getErrorPath() {
        return "";
    }



}
