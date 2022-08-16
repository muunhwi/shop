package com.pofol.shop.controller;

import com.pofol.shop.domain.dto.order.CartDTO;
import com.pofol.shop.domain.dto.order.OrderCartDTO;
import com.pofol.shop.domain.dto.order.checkoutDTO;
import com.pofol.shop.domain.dto.member.LoginForm;
import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.dto.member.JoinMemberForm;
import com.pofol.shop.service.ItemService;
import com.pofol.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/member/login")
    public String getLoginPage(Model model, @AuthenticationPrincipal Member member) {
        model.addAttribute("join", new JoinMemberForm());
        model.addAttribute("login", new LoginForm());
        if(member != null) {
            model.addAttribute("member", member.getEmail());
        }
        model.addAttribute("top3", itemService.findTop3BySalesRate());
        model.addAttribute("list", itemService.getSliceItemList());
        return "/shop/index";
    }

    @GetMapping("/member/join")
    public String getJoinPage(Model model) {

        if(model.getAttribute("join") == null) {
            model.addAttribute("join", new JoinMemberForm());
        }

        model.addAttribute("login", new LoginForm());
        model.addAttribute("top3", itemService.findTop3BySalesRate());
        model.addAttribute("list", itemService.getSliceItemList());
        return "/shop/index";
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

        memberService.formToMember(form);
        return "redirect:/member/login";

    }

    @GetMapping("/member/checkout")
    public String getCheckOut(@AuthenticationPrincipal Member member, Model model) {
        OrderCartDTO orderCart = memberService.findOrderCartByMember(member);
        if(orderCart.getCarts().isEmpty())
            throw new EntityNotFoundException();

        model.addAttribute("orderCart", orderCart);
        return "/member/checkout";
    }

    @PostMapping("/member/checkout")
    public String checkout(@AuthenticationPrincipal Member member,checkoutDTO checkoutDTO) {
        memberService.saveOrderInfo(member, checkoutDTO);
        return "redirect:/member/completion";
    }

    @GetMapping("/member/completion")
    public String getCompletion(@AuthenticationPrincipal Member member, Model model) {
        return "/member/completion";
    }

    @GetMapping("/member/cart")
    public String cartList(@AuthenticationPrincipal Member member, Model model) {
        List<CartDTO>  carts = memberService.findCartsByMemberId(member.getId());
        model.addAttribute("carts", carts);
        return "/member/cart";
    }

    @PostMapping("/member/cart")
    public String cartUpdate(Long id, Integer count) {
        memberService.updateCartCount(id, count);
        return "redirect:/member/cart";
    }

    @GetMapping("/member/cart/remove")
    public String cartDelete(Long id) {
        memberService.deleteCart(id);
        return "redirect:/member/cart";
    }

    private void addGlobalError(BindingResult bindingResult, String errorMessage, RedirectAttributes redirectAttributes,JoinMemberForm form) {
        bindingResult.reject("formError", errorMessage);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "join", bindingResult);
        redirectAttributes.addFlashAttribute("join", form);
    }


}
