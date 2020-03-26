package myoidc.server.web.controller.admin;

import myoidc.server.service.UserService;
import myoidc.server.service.dto.UserFormDto;
import myoidc.server.service.dto.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 2020/3/18
 * <p>
 * 用户[EU] 管理
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Controller
@RequestMapping("/admin/user/")
public class AdminUserController {


    @Autowired
    private UserService userService;


    /**
     * 用户列表
     */
    @GetMapping("list")
    public String list(UserListDto listDto, Model model) {
        listDto = userService.loadUserListDto(listDto);
        model.addAttribute("listDto", listDto);
        return "admin/user_list";
    }


    /**
     * 用户表单
     */
    @GetMapping(value = "form/plus")
    public String showForm(Model model) {
        model.addAttribute("formDto", new UserFormDto());
        return "admin/user_form";
    }


    /**
     * 用户表单
     */
    @PostMapping(value = "form/plus")
    public String submitRegisterClient(@ModelAttribute("formDto") @Valid UserFormDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/user_form";
        }
        String uuid = userService.saveUserForm(formDto);
        model.addAttribute("uuid", uuid).addAttribute("alert", "SaveOK");
        return "redirect:../list";
    }


    /**
     * archive
     */
    @GetMapping("archive/{uuid}")
    public String archive(@PathVariable String uuid, Model model) {
        boolean ok = userService.archiveUserByUuid(uuid);
        model.addAttribute("alert", ok ? "ArchivedOK" : "ArchivedFailed");
        return "redirect:../list";
    }


}
