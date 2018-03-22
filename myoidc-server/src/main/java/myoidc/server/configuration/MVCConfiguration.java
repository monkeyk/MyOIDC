package myoidc.server.configuration;


import myoidc.server.domain.shared.Application;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 2018/1/30
 * <p>
 * Spring MVC 扩展配置
 * <p>
 *
 * @author Shengzhao Li
 */
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {


    /**
     * 扩展拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        WebMvcConfigurer.super.addInterceptors(registry);
    }


    /**
     * 解决乱码问题
     * For UTF-8
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.add(new StringHttpMessageConverter(Charset.forName(Application.ENCODING)));
    }


}
