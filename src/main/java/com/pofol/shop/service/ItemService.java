package com.pofol.shop.service;

import com.pofol.shop.domain.*;
import com.pofol.shop.domain.dto.ItemDTO;
import com.pofol.shop.domain.dto.ItemFormDTO;
import com.pofol.shop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    public Item mapToItemSave(ItemFormDTO itemFormDTO) {
        Subcategory subcategory = subcategoryRepository.findSubCategoryBySubCategoryName(itemFormDTO.getSubcategory());
        Color color = colorRepository.findByName(itemFormDTO.getColor());
        Size size = sizeRepository.findByName(itemFormDTO.getSize());

        Item item = Item.builder()
                .name(itemFormDTO.getName())
                .size(size)
                .quantity(itemFormDTO.getQuantity())
                .salesRate(0)
                .reviewGrade(0D)
                .subCategory(subcategory)
                .price(itemFormDTO.getPrice())
                .color(color)
                .build();
        Item save = itemRepository.save(item);
        return save;
    }

}
