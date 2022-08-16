package com.pofol.shop.domain.dto.order;

import com.pofol.shop.domain.sub.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class checkoutDTO {

    private String email;
    private String phoneNumber;
    private String zoneCode;
    private String baseAddress;
    private String detailAddress;
    private String extraAddress;

}
