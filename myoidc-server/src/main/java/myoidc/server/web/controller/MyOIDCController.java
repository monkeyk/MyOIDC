package myoidc.server.web.controller;

import myoidc.server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static myoidc.server.domain.shared.Application.host;


/**
 * 2018/2/5
 *
 * @author Shengzhao Li
 */
@Controller
public class MyOIDCController {


    private static final Logger LOG = LoggerFactory.getLogger(MyOIDCController.class);


    /**
     * 首页
     */
    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("host", host());
        return "index";
    }


    //Go login
    @GetMapping(value = {"/login"})
    public String login(Model model) {
        LOG.debug("Go to login, IP: {}", WebUtils.getIp());
        model.addAttribute("host", host());
        return "login";
    }


}
