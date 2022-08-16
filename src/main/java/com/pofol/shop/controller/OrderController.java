package com.pofol.shop.controller;

import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.dto.order.OrderFormDTO;
import com.pofol.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/cart")
    @ResponseBody
    public String cartAdd(OrderFormDTO cartDTO, @AuthenticationPrincipal Member member) {
        return orderService.cartAdd(cartDTO, member);
    }




}
