package myoidc.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2018/2/5
 *
 * @author Shengzhao Li
 */
@Controller
public class MyOIDCController {


    /**
     * 首页
     */
    @RequestMapping(value = "/")
    public String index(Model model) {

        return "index";
    }


}
