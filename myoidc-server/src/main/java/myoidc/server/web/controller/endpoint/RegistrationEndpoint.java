package myoidc.server.web.controller.endpoint;

import myoidc.server.domain.shared.Application;
import myoidc.server.infrastructure.oidc.OIDCUseScene;
import myoidc.server.service.OauthService;
import myoidc.server.service.SecurityService;
import myoidc.server.service.dto.ClientRegistrationFormDto;
import myoidc.server.service.dto.OauthClientDetailsDto;
import myoidc.server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 2020/3/11
 * <p>
 * Registration client API
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
@RequestMapping("/public/")
public class RegistrationEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationEndpoint.class);


    @Autowired
    private SecurityService securityService;


    // 引导 注册
    @GetMapping("registration")
    public String preRegistration(Model model) {
        LOG.debug("{}|Pre registration, model: {}", WebUtils.getIp(), model);
        return "registration_pre";
    }


    /**
     * 开始注册 form
     *
     * @param useScene 使用场景, 默认为 WEB
     * @param model    Model
     * @return view
     */
    @GetMapping("registration_form")
    public String registration(@RequestParam("scene") OIDCUseScene useScene, Model model) {
        ClientRegistrationFormDto formDto = new ClientRegistrationFormDto(useScene);
        model.addAttribute("formDto", formDto);
        return "registration_form";
    }

    /**
     * 注册客户端提交
     */
    @PostMapping("registration_form")
    public String submitRegistration(@ModelAttribute("formDto") @Valid ClientRegistrationFormDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration_form";
        }
        OauthClientDetailsDto clientDetailsDto = securityService.saveClientRegistrationForm(formDto);
        model.addAttribute("clientDetailsDto", clientDetailsDto);
        model.addAttribute("host", Application.host());
        return "registration_success";
    }

}
