package com.pofol.shop.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class ItemCondition {

    private Long color;
    private Long category;
    private Long subcategory;
    private Long size;
    private Boolean highestPrice;
    private Boolean highestSalesRate;
    private Boolean highestReviewGrade;
    private Boolean lowestPrice;

}
