package com.pofol.shop.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long id;
    private String name;
    @NumberFormat(pattern = "###,###")
    private int price;
    private ItemImageDTO mainImage;


}
