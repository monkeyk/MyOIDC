package myoidc.server.configuration;


import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 2018/1/30
 * <p>
 *
 * @author Shengzhao Li
 */
@Configuration
public class WebConfiguration implements EmbeddedServletContainerCustomizer {


//    /**
//     * 字符编码配置 UTF-8
//     */
//    @Bean
//    public FilterRegistrationBean encodingFilter() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new CharacterEncodingFilter());
//        registrationBean.addUrlPatterns("/*");
//        //值越小越靠前
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }


    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {

        container.setDisplayName("MyOIDC");
        // Session 有效时间: 120分钟
        container.setSessionTimeout(120, TimeUnit.MINUTES);

//        container.addErrorPages(
//                //404 错误码的配置
//                new ErrorPage(HttpStatus.NOT_FOUND, "/static/error/404.html")
//        );


    }


}
