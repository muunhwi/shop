package com.pofol.shop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemFormDTO {

    private String category;

    private String subcategory;

    private String size;

    private String name;

    private int price;

    private int quantity;

    private String color;

    private MultipartFile mainImage;

    private List<MultipartFile> images;

}
