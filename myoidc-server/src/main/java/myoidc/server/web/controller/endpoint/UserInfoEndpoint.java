package myoidc.server.web.controller.endpoint;

import myoidc.server.service.SecurityService;
import myoidc.server.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2020/3/11
 * <p>
 * UserInfo API
 * <p>
 * https://openid.net/specs/openid-connect-core-1_0.html#UserInfo
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@RestController
@RequestMapping("/api/")
public class UserInfoEndpoint {


    @Autowired
    private SecurityService securityService;


    @GetMapping("userinfo")
    public UserJsonDto userinfo() {
        return securityService.loadCurrentUserJsonDto();
    }


}
