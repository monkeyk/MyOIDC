package myoidc.client.web.controller;

import myoidc.client.service.MyOIDCClientService;
import myoidc.client.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 2020/4/11
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
public class UserInfoController {


    @Autowired
    private MyOIDCClientService clientService;


    /**
     * user_info API
     */
    @GetMapping("user_info")
    public String unityUserInfo(String access_token, Model model) {
        UserInfoDto userDto = clientService.loadUserInfoDto(access_token);

        if (userDto.error()) {
            //error
            model.addAttribute("message", userDto.getErrorDescription());
            model.addAttribute("error", userDto.getError());
            return "redirect:oauth_error";
        } else {
            model.addAttribute("userDto", userDto);
            return "user_info";
        }

    }

}
