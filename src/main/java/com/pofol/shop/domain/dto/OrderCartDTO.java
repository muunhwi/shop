package com.pofol.shop.domain.dto;

import com.pofol.shop.domain.dto.item.ItemImageDTO;
import com.pofol.shop.domain.sub.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCartDTO {

    private Long id;
    private String email;
    private String phoneNumber;
    private List<CartDTO> carts;
    private String zoneCode;
    private String baseAddress;
    private String detailAddress;
    private String extraAddress;

}
