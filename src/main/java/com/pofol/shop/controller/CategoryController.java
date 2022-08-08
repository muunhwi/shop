package com.pofol.shop.controller;

import com.pofol.shop.domain.Subcategory;
import com.pofol.shop.domain.sub.StaticContainer;
import com.pofol.shop.repository.SubcategoryRepository;
import com.pofol.shop.repository.filter.ItemFilterRepository;
import com.pofol.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.pofol.shop.domain.sub.StaticContainer.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final SubcategoryRepository subcategoryRepository;
    private final ShopService shopService;

    @GetMapping("/categories")
    public String[] getSubCategories(String category) {
        List<Subcategory> list= subcategoryRepository.findCategoryNameByCategory(category);
        return list.stream().map(sub -> sub.getName()).toArray(String[]::new);
    }

    @GetMapping("/sizes")
    public String[] getSizes(String category, String subcategory) {
        return shopService.getSize(category, subcategory); // 수정 해야함
    }

    @GetMapping("/colors")
    public String[] getColors() {
        return COLOR; // 수정해야함
    }



}
