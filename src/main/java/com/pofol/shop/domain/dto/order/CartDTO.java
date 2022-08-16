package com.pofol.shop.domain.dto.order;

import com.pofol.shop.domain.dto.item.ItemImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {


    private Long id;
    private String name;
    private String size;
    private String color;
    @NumberFormat(pattern = "###,###")
    private int price;
    private int count;
    private ItemImageDTO mainImage;

}
