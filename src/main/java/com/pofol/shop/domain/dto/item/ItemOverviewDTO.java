package com.pofol.shop.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOverviewDTO {

    private Long id;
    private String name;
    @NumberFormat(pattern = "###,###")
    private int price;
    private List<SizeDTO> size;
    private List<ColorDTO> color;
    private int salesRate;
    private ItemImageDTO mainImage;
    private List<ItemImageDTO> Images;

}
