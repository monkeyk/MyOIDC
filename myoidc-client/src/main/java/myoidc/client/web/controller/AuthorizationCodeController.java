package myoidc.client.web.controller;


import myoidc.client.domain.DiscoveryEndpointInfo;
import myoidc.client.domain.RPHolder;
import myoidc.client.domain.shared.Application;
import myoidc.client.service.MyOIDCClientService;
import myoidc.client.service.dto.AccessTokenDto;
import myoidc.client.service.dto.AuthAccessTokenDto;
import myoidc.client.service.dto.AuthCallbackDto;
import myoidc.client.service.dto.AuthorizationCodeDto;
import myoidc.client.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Handle 'authorization_code'  type actions
 * <p>
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
public class AuthorizationCodeController {


    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeController.class);


    @Autowired
    private MyOIDCClientService clientService;


    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "authorization_code")
    public String authorizationCode(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        model.addAttribute("rpHoldDto", rpHoldDto);
        model.addAttribute("endpointInfo", endpointInfo);
        model.addAttribute("host", Application.host());
        model.addAttribute("state", UUID.randomUUID().toString());
        return "authorization_code";
    }


    /**
     * Save state firstly
     * Redirect to oauth-server login page:   step-2
     */
    @PostMapping(value = "authorization_code")
    public String submitAuthorizationCode(AuthorizationCodeDto codeDto, HttpServletRequest request) throws Exception {
        //save stats  firstly
        WebUtils.saveState(request, codeDto.getState());

        final String fullUri = codeDto.getFullUri();
        LOG.debug("Redirect to OIDC-Server URL: {}", fullUri);
        return "redirect:" + fullUri;
    }


    /**
     * OAuth callback (redirectUri):   step-3
     * <p>
     * Handle 'code', go to 'access_token' ,validation oauth-server response data
     * <p>
     * authorization_code_callback
     */
    @GetMapping(value = "authorization_callback")
    public String authorizationCallback(AuthCallbackDto callbackDto, HttpServletRequest request, Model model) throws Exception {

        if (callbackDto.error()) {
            //Server response error
            model.addAttribute("message", callbackDto.getError_description());
            model.addAttribute("error", callbackDto.getError());
            return "redirect:oauth_error";
        } else if (correctState(callbackDto, request)) {
            //Go to retrieve access_token form
            AuthAccessTokenDto accessTokenDto = clientService.createAuthAccessTokenDto(callbackDto);
            RPHolder rpHoldDto = clientService.loadRPHolder();
            model.addAttribute("rpHoldDto", rpHoldDto);
            model.addAttribute("accessTokenDto", accessTokenDto);
            model.addAttribute("host", Application.host());
            return "code_access_token";
        } else {
            //illegal state
            model.addAttribute("message", "Illegal \"state\": " + callbackDto.getState());
            model.addAttribute("error", "Invalid state");
            return "redirect:oauth_error";
        }

    }


    /**
     * Use HttpClient to get access_token :   step-4
     * <p/>
     * Then, 'authorization_code' flow is finished,  use 'access_token'  visit resources now
     *
     * @param tokenDto AuthAccessTokenDto
     * @param model    Model
     * @return View
     * @throws Exception e
     */
    @PostMapping("code_access_token")
    public String codeAccessToken(AuthAccessTokenDto tokenDto, Model model) throws Exception {
        final AccessTokenDto accessTokenDto = clientService.retrieveAccessTokenDto(tokenDto);
        if (accessTokenDto.error()) {
            model.addAttribute("message", accessTokenDto.getErrorDescription());
            model.addAttribute("error", accessTokenDto.getError());
            return "oauth_error";
        } else {
            model.addAttribute("accessTokenDto", accessTokenDto);
            RPHolder rpHoldDto = clientService.loadRPHolder();
            model.addAttribute("rpHoldDto", rpHoldDto);
            model.addAttribute("endpointInfo", rpHoldDto.getDiscoveryEndpointInfo());
            return "access_token_result";
        }
    }


    /*
     * Check the state is correct or not after redirect from Oauth Server.
     */
    private boolean correctState(AuthCallbackDto callbackDto, HttpServletRequest request) {
        final String state = callbackDto.getState();
        return WebUtils.validateState(request, state);
    }

}