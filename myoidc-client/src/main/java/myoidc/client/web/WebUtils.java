package myoidc.client.web;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author Shengzhao Li
 */
public abstract class WebUtils {


    private WebUtils() {
    }


    /*
     *  Save state to ServletContext, key = value = state
     */
    public static void saveState(HttpServletRequest request, String state) {
        final ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute(state, state);
    }

    /*
     *  Validate state when callback from Oauth Server.
     *  If validation successful, will remove it from ServletContext.
     */
    public static boolean validateState(HttpServletRequest request, String state) {
        if (StringUtils.isBlank(state)) {
            return false;
        }
        final ServletContext servletContext = request.getSession().getServletContext();
        final Object value = servletContext.getAttribute(state);

        if (value != null) {
            servletContext.removeAttribute(state);
            return true;
        }
        return false;
    }


}