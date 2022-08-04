package com.pofol.shop.controller;

import com.pofol.shop.domain.LoginForm;
import com.pofol.shop.domain.dto.JoinMemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShopController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("join", new JoinMemberForm());
        model.addAttribute("login", new LoginForm());
        return "/shop/index";
    }

    @GetMapping("/admin/setting")
    public String admin() {
        return "/admin/setting";
    }

}
