package com.pofol.shop.controller;

import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.dto.*;
import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.sub.StaticContainer;
import com.pofol.shop.repository.filter.ItemFilterRepository;
import com.pofol.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.pofol.shop.domain.sub.StaticContainer.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal Member member) {

        if(member != null) {
            model.addAttribute("auth", member.getEmail());
        }

        model.addAttribute("join", new JoinMemberForm());
        model.addAttribute("login", new LoginForm());
        return "/shop/index";
    }

    @GetMapping("/shop/list")
    public String list(ItemCondition condition, @PageableDefault(page = 0, size=18) Pageable pageable, Model model) throws UnsupportedEncodingException {

        ItemFilterDTO filter = null;
        Long categoryId = condition.getCategory();
        Long subcategoryId = condition.getSubcategory();

        if(categoryId == null) {
            filter = shopService.noParamFilter();
            model.addAttribute("categories", shopService.allCategories());
        } else {
            filter = shopService.findSubCategoriesByCategoryName(categoryId, subcategoryId);
            model.addAttribute("category", categoryId);
        }

        Page<ItemDTO> list = shopService.findFilterItemListByItemCondition(condition, pageable);
        model.addAttribute("list", list);
        model.addAttribute("filter", filter);


        return "/shop/list";
    }



}
