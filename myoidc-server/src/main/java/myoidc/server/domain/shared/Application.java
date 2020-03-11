package myoidc.server.domain.shared;


import myoidc.server.Constants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Shengzhao Li
 */
public class Application implements InitializingBean, Constants {


    //application host
    private static String host;


    /*
    * default
    * */
    public Application() {
    }


    public static String host() {
        return host;
    }

    public void setHost(String host) {
        Application.host = host.endsWith("/") ? host : host + "/";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(host, "host is null");
    }
}