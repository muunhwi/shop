package com.pofol.shop.domain.dto;

import lombok.Data;

@Data
public class SubcategoryDTO {

    public SubcategoryDTO(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    String name;
    Long id;
}
