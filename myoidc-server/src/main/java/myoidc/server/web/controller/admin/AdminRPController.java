package myoidc.server.web.controller.admin;

import myoidc.server.service.OauthService;
import myoidc.server.service.dto.OauthClientDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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


}
