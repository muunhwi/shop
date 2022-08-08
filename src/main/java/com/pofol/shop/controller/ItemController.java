package com.pofol.shop.controller;

import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.dto.ItemFormDTO;
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


import static com.pofol.shop.domain.sub.StaticContainer.CATEGORY;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final AdminService adminService;
    private final ItemService itemService;

    @GetMapping("/admin/item")
    public String admin(Model model) {

        model.addAttribute("categories", CATEGORY);
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

}
