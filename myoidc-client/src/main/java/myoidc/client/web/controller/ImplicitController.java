package myoidc.client.web.controller;

import myoidc.client.domain.DiscoveryEndpointInfo;
import myoidc.client.domain.RPHolder;
import myoidc.client.domain.shared.Application;
import myoidc.client.service.MyOIDCClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handle 'implicit'  type actions
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
public class ImplicitController {


    private static final Logger LOG = LoggerFactory.getLogger(ImplicitController.class);


    @Autowired
    private MyOIDCClientService clientService;


    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "implicit")
    public String password(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'implicit' page: {}",endpointInfo);
        model.addAttribute("rpHoldDto", rpHoldDto);
        model.addAttribute("endpointInfo", endpointInfo);
//        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
//        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("host", Application.host());
        return "implicit";
    }


}