package myoidc.client.web.controller;


import myoidc.client.domain.DiscoveryEndpointInfo;
import myoidc.client.domain.RPHolder;
import myoidc.client.service.MyOIDCClientService;
import myoidc.client.service.dto.AccessTokenDto;
import myoidc.client.service.dto.AuthAccessTokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


/**
 * Handle 'client_credentials'  type actions
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
public class ClientCredentialsController {


    private static final Logger LOG = LoggerFactory.getLogger(ClientCredentialsController.class);


    @Autowired
    private MyOIDCClientService clientService;

    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "client_credentials")
    public String password(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'client_credentials' page, accessTokenUri = {}", endpointInfo.getToken_endpoint());
        model.addAttribute("rpHoldDto", rpHoldDto);
        model.addAttribute("endpointInfo", endpointInfo);
        return "client_credentials";
    }


    /**
     * Ajax call , get access_token
     */
    @GetMapping(value = "credentials_access_token")
    @ResponseBody
    public AccessTokenDto getAccessToken(AuthAccessTokenDto authAccessTokenDto) {
        return clientService.retrieveCredentialsAccessTokenDto(authAccessTokenDto);
    }

}