package com.pofol.shop.service;

import com.pofol.shop.domain.Subcategory;
import com.pofol.shop.domain.dto.ItemCondition;
import com.pofol.shop.domain.dto.ItemDTO;
import com.pofol.shop.domain.dto.ItemFilterDTO;
import com.pofol.shop.domain.sub.StaticContainer;
import com.pofol.shop.repository.SubcategoryRepository;
import com.pofol.shop.repository.filter.ItemFilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pofol.shop.domain.sub.StaticContainer.*;
import static com.pofol.shop.domain.sub.StaticContainer.ACCESSORY_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ItemFilterRepository itemFilterRepository;
    private final SubcategoryRepository subcategoryRepository;

    public ItemFilterDTO findSubCategoriesByCategoryName(String categoryName, String subCategoryName) {

        List<Subcategory> subcategories = subcategoryRepository.findCategoryNameByCategory(categoryName);
        String[] colors = getColors();
        String[] size = getSize(categoryName, subCategoryName);
        return new ItemFilterDTO(subcategories, colors, size);

    }

    public Page<ItemDTO> findFilterItemListByItemCondition(ItemCondition condition, Pageable pageable) {
        return itemFilterRepository.getFilterItemList(condition, pageable);
    }

    public String[] getSize(String category, String subCategoryName) {
        if(category.equals("아우터") || category.equals("셔츠") || category.equals("스포츠")) {
            return BASE_SIZE;
        } else if(category.equals("팬츠")) {
            return PANTS_SIZE;
        } else if(category.equals("신발")) {
            return SHOES_SIZE;
        } else {
            if(subCategoryName == null) {
                return ALL_SIZE;
            }
            if(subCategoryName.equals("반지")) {
                return RING_SIZE;
            } else if(subCategoryName.equals("모자")){
                return HAT_SIZE;
            } else {
                return ACCESSORY_SIZE;
            }
        }
    }

    private String[] getColors() {
        return StaticContainer.COLOR;
    }


}
