package myoidc.server.web.controller.endpoint;

import myoidc.server.infrastructure.oidc.OIDCUseScene;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "registration_pre";
    }


    /**
     * 开始注册 form
     *
     * @param useScene 使用场景, 默认为 WEB
     * @param model    Model
     * @return view
     * @throws Exception e
     */
    @GetMapping("registration_form")
    public String registration(@RequestParam("scene") OIDCUseScene useScene, Model model) throws Exception {

        return "registration_form";
    }


}
