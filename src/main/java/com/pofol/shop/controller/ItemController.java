package com.pofol.shop.controller;

import com.pofol.shop.domain.dto.*;
import com.pofol.shop.domain.Member;
import com.pofol.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal Member member) {

        if(member != null) {
            model.addAttribute("auth", member.getEmail());
        }

        model.addAttribute("join", new JoinMemberForm());
        model.addAttribute("top3", itemService.findTop3BySalesRate() );
        model.addAttribute("login", new LoginForm());
        return "/shop/index";
    }

    @GetMapping("/shop/list")
    public String list(ItemCondition condition, @PageableDefault(page = 0, size=18) Pageable pageable, Model model) throws UnsupportedEncodingException {

        ItemFilterDTO filter = null;
        Long categoryId = condition.getCategory();
        Long subcategoryId = condition.getSubcategory();

        if(categoryId == null) {
            filter = itemService.findItemFilterByNoParamFilter();
            model.addAttribute("categories", itemService.allCategories());
        } else {
            filter = itemService.findItemFilterDTOssByCategoryName(categoryId, subcategoryId);
            model.addAttribute("category", categoryId);
        }

        Page<ItemDTO> list = itemService.getFilterItemList(condition,pageable);
        List<ItemTop3DTO> top3 = itemService.findTop3BySalesRate();

        model.addAttribute("list", list);
        model.addAttribute("filter", filter);
        model.addAttribute("top3", top3);


        return "/shop/list";
    }

    @GetMapping("/shop/item")
    public String getItemOverview() {
        return "shop/overview";
    }



}
