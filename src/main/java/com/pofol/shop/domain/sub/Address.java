package com.pofol.shop.domain.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

   private String zoneCode;
   private String baseAddress;
   private String detailAddress;
   private String extraAddress;

}
