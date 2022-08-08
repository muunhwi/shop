package com.pofol.shop.service;

import com.pofol.shop.domain.Category;
import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.Subcategory;
import com.pofol.shop.domain.dto.ItemDTO;
import com.pofol.shop.domain.dto.ItemFormDTO;
import com.pofol.shop.repository.CategoryRepository;
import com.pofol.shop.repository.ItemRepository;
import com.pofol.shop.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final SubcategoryRepository subcategoryRepository;

    public Item mapToItemSave(ItemFormDTO itemFormDTO) {
        Subcategory subcategory = subcategoryRepository.findCategoryNameBySubCategory(itemFormDTO.getSubcategory());
        Item item = Item.builder()
                .name(itemFormDTO.getName())
                .size(itemFormDTO.getSize())
                .quantity(itemFormDTO.getQuantity())
                .salesRate(0)
                .reviewGrade(0D)
                .subCategory(subcategory)
                .price(itemFormDTO.getPrice())
                .color(itemFormDTO.getColor())
                .build();
        Item save = itemRepository.save(item);
        return save;
    }

}
