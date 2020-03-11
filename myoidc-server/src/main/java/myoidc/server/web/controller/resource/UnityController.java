package myoidc.server.web.controller.resource;


import myoidc.server.service.SecurityService;
import myoidc.server.service.dto.OAuthResourceDto;
import myoidc.server.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static myoidc.server.Constants.RESOURCE_ID;

/**
 * Unity Resource API
 *
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/unity/")
public class UnityController {


    @Autowired
    private SecurityService securityService;


    @GetMapping("dashboard")
    @ResponseBody
    public OAuthResourceDto dashboard() {
        return new OAuthResourceDto(RESOURCE_ID, "Just unity-Resource");
    }

    @GetMapping("user_info")
    @ResponseBody
    public UserJsonDto userInfo() {
        return securityService.loadCurrentUserJsonDto();
    }

}