package myoidc.client.web.controller;

import myoidc.client.domain.DiscoveryEndpointInfo;
import myoidc.client.domain.RPHolder;
import myoidc.client.service.MyOIDCClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handle 'password'  type actions
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
public class PasswordController {


    private static final Logger LOG = LoggerFactory.getLogger(PasswordController.class);


    @Autowired
    private MyOIDCClientService clientService;


    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "password")
    public String password(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'password' page, accessTokenUri = {}", endpointInfo.getToken_endpoint());
        model.addAttribute("accessTokenUri", endpointInfo.getToken_endpoint());
        model.addAttribute("rpHoldDto", rpHoldDto);
        return "password";
    }


}