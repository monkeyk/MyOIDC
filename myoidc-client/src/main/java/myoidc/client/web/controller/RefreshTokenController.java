package myoidc.client.web.controller;


import myoidc.client.domain.DiscoveryEndpointInfo;
import myoidc.client.domain.RPHolder;
import myoidc.client.service.MyOIDCClientService;
import myoidc.client.service.dto.AccessTokenDto;
import myoidc.client.service.dto.AuthAccessTokenDto;
import myoidc.client.service.dto.RefreshAccessTokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handle 'refresh_token'  type actions
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
public class RefreshTokenController {


    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenController.class);


    @Autowired
    private MyOIDCClientService clientService;

    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "refresh_token")
    public String refresh(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'refresh_token' page, DiscoveryEndpointInfo = {}", endpointInfo);
        model.addAttribute("rpHoldDto", rpHoldDto);
        model.addAttribute("endpointInfo", endpointInfo);
        return "refresh_token";
    }

    /**
     * Ajax call , get access_token
     */
    @GetMapping(value = "password_access_token")
    @ResponseBody
    public AccessTokenDto getAccessToken(AuthAccessTokenDto authAccessTokenDto) {
        return clientService.retrievePasswordAccessTokenDto(authAccessTokenDto);
    }

    /**
     * Ajax call , refresh access_token
     */
    @GetMapping(value = "refresh_access_token")
    @ResponseBody
    public AccessTokenDto refreshAccessToken(RefreshAccessTokenDto refreshAccessTokenDto) {
        return clientService.refreshAccessTokenDto(refreshAccessTokenDto);
    }


}