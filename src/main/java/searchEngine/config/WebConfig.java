package searchEngine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import searchEngine.interceptpr.MyInterceptor;

/**
 * @Author: yumo
 * @Description: 拦截器配置类(样板)
 * @DateTime: 2022/5/10 12:56
 **/
@Configuration
@ConditionalOnMissingBean(MyInterceptor.class)
public class WebConfig implements WebMvcConfigurer {

    /*@Autowired
    private MyInterceptor myInterceptor;*/


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/static/**","/user");
    }
}
