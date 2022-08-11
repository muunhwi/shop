package com.pofol.shop.controller;

import com.pofol.shop.domain.Color;
import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.Size;
import com.pofol.shop.domain.dto.ItemFormDTO;
import com.pofol.shop.domain.dto.SubcategoryDTO;
import com.pofol.shop.repository.ColorRepository;
import com.pofol.shop.repository.SubcategoryRepository;
import com.pofol.shop.service.AdminService;
import com.pofol.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.stream.Collectors;

import static com.pofol.shop.domain.sub.StaticContainer.CATEGORY;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ItemService itemService;
    private final SubcategoryRepository subcategoryRepository;
    private final ColorRepository colorRepository;

    @GetMapping("/admin/item")
    public String admin(Model model) {

        model.addAttribute("categories", itemService.allCategories());
        model.addAttribute("item", new ItemFormDTO());

        return "/admin/addItem";
    }

    @PostMapping("/admin/item")
    @ResponseBody
    @Transactional
    public HttpEntity<?> fileUploader(ItemFormDTO DTO) {
        Item item = itemService.mapToItemSave(DTO);
        adminService.uploader(DTO, item);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/categories")
    @ResponseBody
    public List<SubcategoryDTO> getSubCategories(String category) {
        return subcategoryRepository.findSubCategoriesNameByCategoryName(category).stream()
                .map(s -> new SubcategoryDTO(s.getName(), s.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/sizes")
    @ResponseBody
    public String[] getSizes(Long subcategoryId) {
        return itemService.getSize(subcategoryId).stream().map(Size::getName).toArray(String[]::new);
    }

    @GetMapping("/colors")
    @ResponseBody
    public String[] getColors() {
        return colorRepository.findAll()
                .stream()
                .map(Color::getName)
                .toArray(String[]::new);
    }

}
