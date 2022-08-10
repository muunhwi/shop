package com.pofol.shop.controller;

import com.pofol.shop.domain.Color;
import com.pofol.shop.domain.Size;
import com.pofol.shop.domain.Subcategory;
import com.pofol.shop.repository.ColorRepository;
import com.pofol.shop.repository.SizeRepository;
import com.pofol.shop.repository.SubcategoryRepository;
import com.pofol.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final SubcategoryRepository subcategoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ShopService shopService;

    @GetMapping("/categories")
    public String[] getSubCategories(String category) {
        return subcategoryRepository.findSubCategoriesNameByCategoryName(category)
                .stream().map(Subcategory::getName)
                .toArray(String[]::new);
    }

    @GetMapping("/sizes")
    public String[] getSizes(Long subcategoryId) {
        return shopService.getSize(subcategoryId).stream().map(Size::getName).toArray(String[]::new);
    }

    @GetMapping("/colors")
    public String[] getColors() {
        return colorRepository.findAll()
                .stream()
                .map(Color::getName)
                .toArray(String[]::new);
    }

}
