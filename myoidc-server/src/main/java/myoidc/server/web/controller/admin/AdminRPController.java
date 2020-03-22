package myoidc.server.web.controller.admin;

import myoidc.server.service.OauthService;
import myoidc.server.service.dto.OauthClientDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static myoidc.server.domain.shared.Application.host;

/**
 * 2020/3/18
 * <p>
 * 客户端[RP] 管理
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
@RequestMapping("/admin/rp/")
public class AdminRPController {


    @Autowired
    private OauthService oauthService;


    // RP 列表
    @GetMapping("list")
    public String rpList(String clientId, Model model) {
        List<OauthClientDetailsDto> clientDetailsDtoList = oauthService.loadOauthClientDetailsDtos(clientId);
        model.addAttribute("dtoList", clientDetailsDtoList);
        model.addAttribute("clientId", clientId);
        return "admin/rp_list";
    }


    /**
     * Archive ,逻辑删除
     */
    @GetMapping("archive_client/{clientId}")
    public String archiveClient(@PathVariable("clientId") String clientId) {
        oauthService.archiveOauthClientDetails(clientId);
        return "redirect:../list";
    }


    /**
     * 添加客户端
     */
    @GetMapping("form/plus")
    public String addClient(Model model) {
        model.addAttribute("formDto", new OauthClientDetailsDto().initialized());
        return "admin/rp_form";
    }


    /**
     * 添加客户端
     */
    @PostMapping("form/plus")
    public String submitClient(@ModelAttribute("formDto") @Valid OauthClientDetailsDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/rp_form";
        }
        String clientId = oauthService.saveOAuthClientDetails(formDto);
        model.addAttribute("newClientId", clientId);
        return "redirect:../list";
    }


    /**
     * Test client
     */
    @RequestMapping("test_client/{clientId}")
    public String testClient(@PathVariable("clientId") String clientId, Model model) {
        OauthClientDetailsDto clientDetailsDto = oauthService.loadOauthClientDetailsDto(clientId);
        model.addAttribute("clientDetailsDto", clientDetailsDto);
        model.addAttribute("host", host());
        return "admin/rp_client_test";
    }

}
