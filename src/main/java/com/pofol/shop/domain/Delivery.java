package com.pofol.shop.domain;

import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.domain.sub.DeliveryStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "DELIVERY_PK_GENERATOR",
        sequenceName = "DELIVERY_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_PK_GENERATOR")
    private Long id;

    private String deliveryEmail;

    private String deliveryPhoneNumber;

    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

}
