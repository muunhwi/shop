package com.pofol.shop.controller;

import com.pofol.shop.domain.Member;
import com.pofol.shop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/login")
    public String getLoginPage() {
        return "/member/login";
    }
    @PostMapping("/member/login")
    public String postLoginPage(Member member) {
        log.info("post call");
        return "/shop/main";
    }
    @GetMapping("/shop/main")
    public String dakjqwdwo() {
        return "shop/main";
    }
    @GetMapping("/admin/setting")
    public String admin() {
        return "/admin/setting";
    }



}
