package myoidc.client.web.controller;


import myoidc.client.domain.shared.Application;
import myoidc.client.service.MyOIDCClientService;
import myoidc.client.domain.RPHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


/**
 * 2018/2/5
 * <p>
 * 引导 入口
 *
 * @author Shengzhao Li
 */
@Controller
public class StartupController {


    private static final Logger LOG = LoggerFactory.getLogger(StartupController.class);


    @Autowired
    private MyOIDCClientService clientService;

    /**
     * 首页
     */
    @GetMapping("/")
    public String index(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        model.addAttribute("host", Application.host());
        model.addAttribute("formDto", rpHoldDto);
        return "index";
    }

    /**
     * submit
     * 提交RP信息
     *
     * @since 1.1.0
     */
    @PostMapping("/submit")
    public String submit(@ModelAttribute("formDto") @Valid RPHolder formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        boolean ok = clientService.saveRPHolder(formDto);
        model.addAttribute("saveResult", ok);
        return "redirect:/";
    }


    /**
     * Common handle oauth error ,
     * show the error message.
     *
     * @since 1..1.0
     */
    @RequestMapping("oauth_error")
    public String oauthError(String error, String message, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("message", message);

        LOG.debug("Go to oauth_error, error={},message={}", error, message);
        return "oauth_error";
    }


    /**
     * verify id_token
     *
     * @param token token
     * @return map
     * @since 1.1.0
     */
    @GetMapping("verify_token")
    @ResponseBody
    public Map<String, Object> verifyIdToken(@RequestParam String token) {
        return clientService.verifyToken(token);
    }

}
