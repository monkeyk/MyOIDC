package myoidc.server.web.controller.resource;


import myoidc.server.service.SecurityService;
import myoidc.server.service.dto.OAuthResourceDto;
import myoidc.server.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Mobile Resource
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/m/")
public class MobileController {


    @Autowired
    private SecurityService securityService;


    @GetMapping("dashboard")
    @ResponseBody
    public OAuthResourceDto dashboard() {
        return new OAuthResourceDto("mobile-resource", "Just mobile OAuth2-Resource");
    }

    @GetMapping("user_info")
    @ResponseBody
    public UserJsonDto userInfo() {
        return securityService.loadCurrentUserJsonDto();
    }

}