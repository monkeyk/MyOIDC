package myoidc.client.web.controller;


import myoidc.client.domain.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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


    /**
     * 首页
     */
    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("host", Application.host());
        return "index";
    }


}
