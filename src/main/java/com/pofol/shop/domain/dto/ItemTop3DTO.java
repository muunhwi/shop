package com.pofol.shop.domain.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class ItemTop3DTO {

    public ItemTop3DTO(Long id,String name, int price, String size, String color) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
    }

    private Long id;

    private String name;

    @NumberFormat(pattern = "###,###")
    private int price;

    private String size;

    private String color;

    private ItemMainImageDTO mainImage;

}
