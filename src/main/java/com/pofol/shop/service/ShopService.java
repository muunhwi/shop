package com.pofol.shop.service;

import com.pofol.shop.domain.Category;
import com.pofol.shop.domain.Color;
import com.pofol.shop.domain.Size;
import com.pofol.shop.domain.Subcategory;
import com.pofol.shop.domain.dto.ItemCondition;
import com.pofol.shop.domain.dto.ItemDTO;
import com.pofol.shop.domain.dto.ItemFilterDTO;
import com.pofol.shop.domain.sub.StaticContainer;
import com.pofol.shop.repository.CategoryRepository;
import com.pofol.shop.repository.ColorRepository;
import com.pofol.shop.repository.SizeRepository;
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
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final CategoryRepository categoryRepository;

    public ItemFilterDTO findSubCategoriesByCategoryName(Long categoryId, Long subCategoryId) {

        List<Subcategory> subcategories = subcategoryRepository.findSubCategoriesNameByCategoryId(categoryId);
        List<Color> colors = getColors();
        List<Size> size = getSize(subCategoryId);
        return new ItemFilterDTO(subcategories, colors, size);

    }

    public ItemFilterDTO noParamFilter() {

        List<Subcategory> subcategories = subcategoryRepository.findAll();
        List<Color> colors = getColors();
        List<Size> size = getSize(null);
        return new ItemFilterDTO(subcategories, colors, size);

    }


    public Page<ItemDTO> findFilterItemListByItemCondition(ItemCondition condition, Pageable pageable) {
        return itemFilterRepository.getFilterItemList(condition, pageable);
    }

    public List<Size> getSize(Long subCategoryId) {

        if(subCategoryId == null) {
            return sizeRepository.findAll();
        }

        Subcategory subcategory = subcategoryRepository.findSubCategoryBySubCategoryId(subCategoryId);
        String category = subcategory.getCategory().getName();
        String subCategoryName = subcategory.getName();
        String size = null;

        if(category == null) {
            return sizeRepository.findAll();
        } else if(category.equals("아우터") || category.equals("셔츠") || category.equals("스포츠")) {
            size ="BASE_SIZE";
        } else if(category.equals("팬츠")) {
            size = "PANTS_SIZE";
        } else if(category.equals("신발")) {
            size = "SHOES_SIZE";
        } else {
            if(subCategoryName == null) {
                size ="ACCESSORY_SIZE";
            } else if(subCategoryName.equals("반지")) {
                size = "RING_SIZE";
            } else if(subCategoryName.equals("모자")){
                size = "HAT_SIZE";
            } else {
                size = "ACCESSORY_SIZE";
            }
        }

        return  sizeRepository.findByCategoryName(size);

    }

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }


    private List<Color> getColors() {
        return colorRepository.findAll();
    }


}
