package com.pofol.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShopController {

    @GetMapping("/")
    public String index() {
        return "/shop/index";
    }

    @GetMapping("/admin/setting")
    public String admin() {
        return "/admin/setting";
    }

}
