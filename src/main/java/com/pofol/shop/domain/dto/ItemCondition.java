package com.pofol.shop.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ItemCondition {

    private String color;
    private String category;
    private String subCategory;
    private String size;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean highestPrice;
    private Boolean highestSalesRate;
    private Boolean highestReviewGrade;
    private Boolean lowestPrice;

}
