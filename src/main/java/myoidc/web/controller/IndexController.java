package myoidc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2016/12/25
 *
 * @author Shengzhao Li
 */
@Controller
public class IndexController {


    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "index";
    }

}
