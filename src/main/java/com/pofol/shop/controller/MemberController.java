package com.pofol.shop.controller;

import com.pofol.shop.domain.LoginForm;
import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.dto.JoinMemberForm;
import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Member testMember = Member.builder()
                .nickname("member")
                .introduce("hello")
                .address(new Address("23131","hi","qwdq",""))
                .sex("남")
                .email("member@naver.com")
                .phoneNumber("010-1111-1111")
                .password(encoder.encode("dwc02207"))
                .role("ROLE_USER")
                .build();
        memberService.save(testMember);
    }


    @GetMapping("/member/login")
    public String getLoginPage(Model model) {
        model.addAttribute("join", new JoinMemberForm());
        model.addAttribute("login", new LoginForm());
        return "/shop/index";
    }

    @GetMapping("/member/join")
    public String getJoinPage(Model model) {

        if(model.getAttribute("join") == null) {
            model.addAttribute("join", new JoinMemberForm());
        }

        model.addAttribute("login", new LoginForm());
        return "/shop/index";
    }

    @GetMapping("/test")
    public String get() {
        return "/member/login";
    }

    @PostMapping("/member/join")
    public String getJoinPage(@Validated JoinMemberForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            addGlobalError(bindingResult, "입력 양식이 옳바르지 않습니다 다시 입력해주세요", redirectAttributes, form);
            log.info("입력 양식 에러 발생");
            return "redirect:/member/join";
        }

        Optional<Member> findMember = memberService.findByEmail(form.getEmail());
        if(findMember.isPresent()) {
            addGlobalError(bindingResult, "이메일이 중복됩니다.", redirectAttributes, form);
            log.info("이메일 중복 예외 발생");
            return "redirect:/member/join";
        }

        Member member = formToMember(form);
        memberService.save(member);
        return "redirect:/member/login";

    }

    private void addGlobalError(BindingResult bindingResult, String errorMessage, RedirectAttributes redirectAttributes,JoinMemberForm form) {
        bindingResult.reject("formError", errorMessage);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "join", bindingResult);
        redirectAttributes.addFlashAttribute("join", form);
    }

    private Member formToMember(JoinMemberForm form) {
        Member member = Member.builder()
                .email(form.getEmail())
                .phoneNumber(form.getPhoneNumber())
                .sex(form.getSex())
                .address(new Address(form.getZoneCode(), form.getBaseAddress(), form.getDetailAddress(), form.getExtraAddress()))
                .password(encoder.encode(form.getPassword()))
                .build();
        return member;
    }


}
