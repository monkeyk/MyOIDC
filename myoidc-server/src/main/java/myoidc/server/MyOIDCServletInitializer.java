package myoidc.server;

import myoidc.server.domain.shared.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * Servlet Context init
 */
public class MyOIDCServletInitializer extends SpringBootServletInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //主版本号
        servletContext.setAttribute("mainVersion", Application.VERSION);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MyOIDCServerApplication.class);
    }

}
