package springdemo.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.UrlPathHelper;
import springdemo.date.USLocalDateFormatter;

import java.time.LocalDate;

/**
 * @author Sunnysen
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 配置 嵌入式容器 参数
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer = new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                //配置异常拦截 对应的错误链接
                container.addErrorPages(new ErrorPage(MultipartException.class,"/uploadError"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/error/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500"));
                container.addErrorPages(new ErrorPage(java.lang.Throwable.class,"/error/500"));
            }
        };
        return  embeddedServletContainerCustomizer;
    }

    /**
     * 配置 URL
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        //关闭去分号行为 矩阵变量使用;号
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    /**
     * 注册 转化器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //注册日期类型转换
        registry.addFormatterForFieldType(LocalDate.class,new USLocalDateFormatter());
    }

    /**
     * session保存locale 和timeZone
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(){
        return  new SessionLocaleResolver();
    }

    /**
     * 配置本地化 拦截参数
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return  localeChangeInterceptor;
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
}
