package com.pofol.shop.domain.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemFilterDTO {

    private List<Subcategory> subcategories;
    private List<Color> colors;
    private List<Size> sizes;
    private String[] sorts;

    public ItemFilterDTO(List<Subcategory> subcategories, List<Color> colors,List<Size> sizes) {
        this.subcategories = subcategories;
        this.colors = colors;
        this.sizes = sizes;
        this.sorts = new String[]{"highestPrice", "highestSalesRate", "highestReviewGrade", "lowestPrice"};
    }
}
