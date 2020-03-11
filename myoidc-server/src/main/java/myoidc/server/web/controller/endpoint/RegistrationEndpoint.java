package myoidc.server.web.controller.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    // 引导 注册
    @GetMapping("registration")
    public String preRegistration(Model model) throws Exception {

        return "registration_form";
    }
}
