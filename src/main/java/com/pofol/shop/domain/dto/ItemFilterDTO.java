package com.pofol.shop.domain.dto;

import com.pofol.shop.domain.Subcategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemFilterDTO {

    private List<Subcategory> subcategories;
    private String[] colors;
    private String[] sizes;
    private String[] sorts;

    public ItemFilterDTO(List<Subcategory> subcategories, String[] colors, String[] sizes) {
        this.subcategories = subcategories;
        this.colors = colors;
        this.sizes = sizes;
        this.sorts = new String[]{"highestPrice", "highestSalesRate", "highestReviewGrade", "lowestPrice"};
    }
}
