package com.pofol.shop.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
