package com.pofol.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {

    @QueryProjection
    public ItemDTO(String name, int price, int quantity, String size, String color, Double reviewGrade, int salesRate, String subCategoryName, String categoryName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.reviewGrade = reviewGrade;
        this.salesRate = salesRate;
        this.subCategoryName = subCategoryName;
        this.categoryName = categoryName;
    }

    private String name;
    private int price;
    private int quantity;
    private String size;
    private String color;
    private Double reviewGrade;
    private int salesRate;
    private String subCategoryName;
    private String categoryName;

}
